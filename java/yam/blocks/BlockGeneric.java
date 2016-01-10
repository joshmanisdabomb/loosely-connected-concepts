package yam.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGeneric extends Block {
	
	private String sTop, sBottom, sFront, sSide;
	private IIcon iTop, iBottom, iFront, iSide;
	
	private Item blockdrop = null;
	private int dropquantity = 1;
	private int dropquantitymodifier = 1;
	private int rendertype = 0;
	private boolean normalblock = true;
	private boolean passthrough = false;
	private boolean gravity = false;
	private boolean dropGravityBlock = true;
	private ArrayList<String> extraInfo = new ArrayList<String>();
	private DamageSource source;
	private float hurtsFor;
	private boolean transparent;
	private Potion potionEffect;
	private int potionDuration;
	private int potionLevel;
	private int aura = 0;
	
	public BlockGeneric(Material material, String texture) {
		this(material, texture, texture, texture, texture);
	}

	public BlockGeneric(Material material, String textureFront, String textureSide, String textureTop, String textureBottom) {
		super(material);

		sTop = textureTop;
		sBottom = textureBottom;
		sFront = textureFront;
		sSide = textureSide;
		
		this.setBlockTextureName(YetAnotherMod.MODID + ":" + textureFront);
		this.setCreativeTab(YetAnotherMod.global);
		this.setLightOpacity(255);
	}
	
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
		if (gravity) {p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, this.tickRate(p_149726_1_));}
		super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
    	if (gravity) {p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.tickRate(p_149695_1_));}
    	super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
    
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (!p_149674_1_.isRemote) {
        	if (gravity) {
        		this.func_149830_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
                if (aura != 0 && p_149674_1_.rand.nextInt(100) == 0) {
                	this.spread(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, aura);
                }
        	} else {
        		this.spread(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, aura);
        	}
        }
		YetAnotherMod.proxy.updateSpreadRenderAt(p_149674_2_, p_149674_3_, p_149674_4_);
    }
    
    public void setAura(int aura) {
    	this.aura = aura;
    	this.setTickRandomly(true);
    }
    
    public int getAura() {
    	return aura;
    }

    private void spread(World world, int x, int y, int z, int auraID) {
    	BiomeGenBase biome = (auraID == -1 ? YetAnotherMod.biomeDarkAura : YetAnotherMod.biomeLightAura);
    	
    	Chunk c = world.getChunkFromBlockCoords(x, z);
		byte[] biomes = c.getBiomeArray();
		if (biomes != null && biomes.length > 1 && world.getBiomeGenForCoords(x, z) != YetAnotherMod.biomeLightAura) {
			biomes[((((z % 16) + 16) % 16) << 4) | (((x % 16) + 16) % 16)] = (byte)biome.biomeID;
			c.setBiomeArray(biomes);
			c.setChunkModified();
		}
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -2; j <= 2; j++) {
				for (int k = -1; k <= 1; k++) {
					if (world.rand.nextInt(12) == 0) {
						//set block
						Block before = world.getBlock(x+i, y+j, z+k);
						int beforeData = world.getBlockMetadata(x+i, y+j, z+k);
						if (!(before instanceof BlockGeneric && ((BlockGeneric)(before)).getAura() == auraID) && before.getBlockHardness(world, x+i, y+j, z+k) >= 0.0F) {
							if (!(before instanceof BlockLiquid)) {
								//world.func_147480_a(x+i, y+j, z+k, false);
								world.setBlockToAir(x+i, y+j, z+k);
							}
							
							if (before == Blocks.grass || before == Blocks.mycelium) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightGrass);
							} else if (before == Blocks.dirt || before == YetAnotherMod.mud) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightDirt);
							} else if (before == Blocks.stone) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightStone);
							} else if (before == Blocks.cobblestone || before == Blocks.sandstone) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightCobblestone);
							} else if (before == YetAnotherMod.crackedMud) {
								if (world.canBlockSeeTheSky(x+i, y+j, z+k)) {
									world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightGrass);
								} else {
									world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightDirt);
								}
							} else if (before == Blocks.sand || before == YetAnotherMod.quicksand) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightSand);
							} else if (before == Blocks.gravel) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightGravel);
							} else if (before == Blocks.mossy_cobblestone) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightMossyCobblestone);
							} else if (before == Blocks.obsidian) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightPolishedStone);
							} else if (before == Blocks.log || before == Blocks.log2) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightWood);
							} else if (before == Blocks.planks) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightPlanks);
							} else if (before == Blocks.leaves || before == Blocks.leaves2) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightLeaves);
							} else if (before == Blocks.red_mushroom_block || before == Blocks.brown_mushroom_block) {
								if (beforeData == 10 || beforeData == 15) {
									world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightWood);
								} else {
									world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightLeaves);
								}
							} else if (before == Blocks.sapling) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightSapling);
							} else if (before == Blocks.brick_block) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.lightBricks);
							} else if (before == Blocks.stonebrick || (before instanceof BlockGeneric && before.getUnlocalizedName().toLowerCase().contains("brick"))) {
								Block after = world.rand.nextInt(3) == 0 ? YetAnotherMod.bricksWishstone : (world.rand.nextInt(3) == 0 ? YetAnotherMod.bricksHopestone : YetAnotherMod.bricksDreamstone);
								world.setBlock(x+i, y+j, z+k, after);
							} else if (before == Blocks.coal_ore || before == Blocks.iron_ore || before == YetAnotherMod.saltOre) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.wishstoneOre);
							} else if (before == Blocks.gold_ore || before == Blocks.emerald_ore || before == Blocks.redstone_ore || before == Blocks.lapis_ore || before == YetAnotherMod.bigxOre) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.hopestoneOre);
							} else if (before == Blocks.diamond_ore || before == YetAnotherMod.rubyOre || before == YetAnotherMod.rustOre || before == YetAnotherMod.uraniumOre) {
								world.setBlock(x+i, y+j, z+k, YetAnotherMod.dreamstoneOre);
							}
						}
						
						//set biome
						Chunk c2 = world.getChunkFromBlockCoords(x+i, z+k);
						byte[] biomes2 = c2.getBiomeArray();
						if (biomes2 != null && biomes2.length > 1 && world.getBiomeGenForCoords(x+i, z+k) != YetAnotherMod.biomeLightAura) {
							biomes2[(((((z+k) % 16) + 16) % 16) << 4) | ((((x+i) % 16) + 16) % 16)] = (byte)biome.biomeID;
							c2.setBiomeArray(biomes2);
							c2.setChunkModified();
						}
					}
				}
			}
		}
	}

	private void func_149830_m(World p_149830_1_, int p_149830_2_, int p_149830_3_, int p_149830_4_)
    {
        if (gravity && func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_) && p_149830_3_ >= 0)
        {
            byte b0 = 32;

            if (p_149830_1_.checkChunksExist(p_149830_2_ - b0, p_149830_3_ - b0, p_149830_4_ - b0, p_149830_2_ + b0, p_149830_3_ + b0, p_149830_4_ + b0))
            {
                if (!p_149830_1_.isRemote)
                {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(p_149830_1_, (double)((float)p_149830_2_ + 0.5F), (double)((float)p_149830_3_ + 0.5F), (double)((float)p_149830_4_ + 0.5F), this, p_149830_1_.getBlockMetadata(p_149830_2_, p_149830_3_, p_149830_4_));
                    entityfallingblock.field_145813_c = dropGravityBlock;
                    p_149830_1_.spawnEntityInWorld(entityfallingblock);
                }
            }
            else
            {
                p_149830_1_.setBlockToAir(p_149830_2_, p_149830_3_, p_149830_4_);

                while (func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_) && p_149830_3_ > 0)
                {
                    --p_149830_3_;
                }

                if (p_149830_3_ > 0)
                {
                    p_149830_1_.setBlock(p_149830_2_, p_149830_3_, p_149830_4_, this);
                }
            }
        }
    }
    
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (source != null) {
			par5Entity.attackEntityFrom(source, hurtsFor);
		}
		if (potionEffect != null && par5Entity instanceof EntityLivingBase) {
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(potionEffect.id, potionDuration, potionLevel));
		}
	}
    
    public int tickRate(World p_149738_1_)
    {
        return 2;
    }

    public static boolean func_149831_e(World p_149831_0_, int p_149831_1_, int p_149831_2_, int p_149831_3_)
    {
        Block block = p_149831_0_.getBlock(p_149831_1_, p_149831_2_, p_149831_3_);

        if (block.isAir(p_149831_0_, p_149831_1_, p_149831_2_, p_149831_3_))
        {
            return true;
        }
        else if (block == Blocks.fire)
        {
            return true;
        }
        else
        {
            //TODO: King, take a look here when doing liquids!
            Material material = block.getMaterial();
            return material == Material.water ? true : material == Material.lava;
        }
    }
	
	public int getDirection(int par0) {
        return par0 & 3;
    }
	
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.iTop = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + this.sTop);
		this.iBottom = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + this.sBottom);
		this.iFront = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + this.sFront);
		this.iSide = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + this.sSide);
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
        return par1 == 1 ? this.iTop : (par1 == 0 ? this.iBottom : (par2 == 2 && par1 == 2 ? this.iFront : (par2 == 3 && par1 == 5 ? this.iFront : (par2 == 0 && par1 == 3 ? this.iFront : (par2 == 1 && par1 == 4 ? this.iFront : this.iSide)))));
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        return transparent && block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
	
	//Custom Methods
	
	public void setSlipperiness(float par1Float) {
		this.slipperiness = par1Float;
	}
	
	public void setDrops(Item par1Item, int par2, int par3) {
		this.blockdrop = par1Item;
		this.dropquantity = par2;
		this.dropquantitymodifier = par3;
	}
	
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return this.blockdrop != null ? this.blockdrop : Item.getItemFromBlock(this);
    }
	
	public int quantityDropped(Random par1Random) {
		return this.dropquantity + par1Random.nextInt(this.dropquantitymodifier);
	}
	
	public void setRenderType(int par1, boolean par2) {
		this.rendertype = par1;
		this.normalblock = par2;	
	}
	
	public boolean renderAsNormalBlock() {
		return this.normalblock;
    }
	
	public boolean isOpaqueCube() {
		return this.normalblock;
	}
	
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
		return this.normalblock ? 0 : 1;
    }
	
	public int getRenderType() {
		return this.rendertype;
    }
	
	public void canPassThrough() {
		this.passthrough = true;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        if (passthrough) {return null;}
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
	
	public void setExtraInformation(String... lines) {
		for (int i = 0; i < lines.length; i++) {
			this.extraInfo.add(lines[i]);
		}
	}
	
	//For itemblocks. :D
	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (extraInfo.isEmpty()) {return;}
		list.addAll(this.extraInfo);
	}
	
	public void setGravity(boolean b) {
		gravity = true;
		dropGravityBlock = b;
	}
	
	public void setDamaging(DamageSource dmgsrc, float damage) {
		this.source = dmgsrc;
		this.hurtsFor = damage;
	}
	
	public void setTransparency() {
		transparent = true;
	}

	public void setEffect(Potion effect, int ticks, int level) {
		this.potionEffect = effect;
		this.potionDuration = ticks;
		this.potionLevel = level;
	}
	
}
