package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.AimaggTab.AimaggCategory;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketMovementClient;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockSoft extends AimaggBlockBasic {
	
	//TODO Check particle colour when the block is not supported but not updated.
	//TODO Mud may need a better custom sound.
    public static final PropertyEnum<SoftBlockType> TYPE = PropertyEnum.<SoftBlockType>create("type", SoftBlockType.class);

	public AimaggBlockSoft(String internalName, Material material) {
		super(internalName, material, SoftBlockType.MUD.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, SoftBlockType.MUD));
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntitySpider && state.getValue(TYPE) != SoftBlockType.NUCLEAR_WASTE) {
			((EntitySpider)entityIn).setBesideClimbableBlock(false);
		}
		
		if (entityIn instanceof EntityFallingBlock) {
			worldIn.setBlockState(new BlockPos((int)Math.floor(entityIn.posX), (int)Math.floor(entityIn.posY+0.5), (int)Math.floor(entityIn.posZ)), ((EntityFallingBlock)entityIn).getBlock(), 3);
			entityIn.setDead();
		} else if (state.getValue(TYPE).getMaxSpeed() == 0.0D) {
			entityIn.setSprinting(false);
			entityIn.setInWeb();
			if (!worldIn.isRemote) {
				entityIn.motionX *= state.getValue(TYPE).getSpeedModifier();
				entityIn.motionZ *= state.getValue(TYPE).getSpeedModifier();
				if (entityIn instanceof EntityPlayerMP) {
					AimaggPacketMovementClient packet = new AimaggPacketMovementClient();
					packet.setEntityID(entityIn.getEntityId());
					packet.multiplyEntityVelocityX(state.getValue(TYPE).getSpeedModifier());
					packet.multiplyEntityVelocityZ(state.getValue(TYPE).getSpeedModifier());
					AimaggPacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)entityIn);
				}
			}
		} else if (state.getValue(TYPE).getSpeedModifier() != 1.0D && entityIn.getPosition().down().equals(pos)) {
			entityIn.setSprinting(false);
			if (!worldIn.isRemote) {
				AimaggPacketMovementClient packet = new AimaggPacketMovementClient();
				if (entityIn.motionX < state.getValue(TYPE).getMaxSpeed() && entityIn.motionX > -state.getValue(TYPE).getMaxSpeed()) {
					entityIn.motionX *= state.getValue(TYPE).getSpeedModifier();
					packet.multiplyEntityVelocityX(state.getValue(TYPE).getSpeedModifier());
				}
				if (entityIn.motionZ < state.getValue(TYPE).getMaxSpeed() && entityIn.motionZ > -state.getValue(TYPE).getMaxSpeed()) {
					entityIn.motionZ *= state.getValue(TYPE).getSpeedModifier();
					packet.multiplyEntityVelocityZ(state.getValue(TYPE).getSpeedModifier());
				}
				if (entityIn instanceof EntityPlayerMP) {
					packet.setEntityID(entityIn.getEntityId());
					AimaggPacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)entityIn);
				}
			}
		}
	}

    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }
	
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getValue(TYPE).getBoundingBox());
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	if (!worldIn.isRemote && state.getValue(TYPE).canFall()) {
            this.checkFallable(worldIn, pos, state);
        }
    }
    
	private void checkFallable(World worldIn, BlockPos pos, IBlockState state) {
		if ((worldIn.isAirBlock(pos.down()) || BlockFalling.canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
			int i = 32;

			if (!state.getValue(TYPE).canInstafall() && !BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
				if (!worldIn.isRemote) {
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
					worldIn.spawnEntity(entityfallingblock);
				}
			} else {
				worldIn.setBlockToAir(pos);
				BlockPos blockpos;

				for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || BlockFalling.canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0; blockpos = blockpos.down()) {
					;
				}

				if (blockpos.getY() > 0) {
					worldIn.setBlockState(blockpos.up(), state);
				}
			}
		}
	}

	@Override
	public int tickRate(World worldIn) {
        return 2;
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, SoftBlockType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (SoftBlockType sb : SoftBlockType.values()) {
        	items.add(new ItemStack(this, 1, sb.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (SoftBlockType sb : SoftBlockType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), sb.getMetadata(), sb.getModel());
        }
	}
	
	@Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
	
	@Override
	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this) {
			@Override
			public int getMetadata(int metadata) {
				return metadata;
			}

			@Override
			public String getUnlocalizedName(ItemStack stack) {
				return super.getUnlocalizedName() + "." + SoftBlockType.getFromMetadata(stack.getMetadata()).getName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return blockState.getValue(TYPE).getHardness();
    }
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(TYPE).getResistance();
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(TYPE).getSoundType();
    }
	
	@Override
	public boolean isToolEffective(String type, IBlockState state) {
		return (state.getValue(TYPE).getHardness() > -1.0F && type.equals("shovel")) || super.isToolEffective(type, state);
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(TYPE)).getMapColor();
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(16) == 0) {
			BlockPos blockpos = pos.down();

			if (BlockFalling.canFallThrough(worldIn.getBlockState(blockpos))) {
				double d0 = (double) ((float) pos.getX() + rand.nextFloat());
				double d1 = (double) pos.getY() - 0.05D;
				double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
				worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(stateIn));
			}
		}
	}
	
	public static enum SoftBlockType implements IStringSerializable {
		MUD(AimaggCategory.SOFT, 0, MapColor.OBSIDIAN, 0.8F, 0.8F, SoundType.SLIME, FallType.FALL, 0.6D, 1.0D, new AxisAlignedBB(1/32D, 0.0D, 1/32D, 31/32D, 12/16D, 31/32D)),
		QUICKSAND(AimaggCategory.SOFT, 0, MapColor.SAND, 1.3F, 0.7F, SoundType.SAND, FallType.FALL, 0.4D, 0.0D, NULL_AABB),
		RED_QUICKSAND(AimaggCategory.SOFT, 0, MapColor.ADOBE, 1.3F, 0.7F, SoundType.SAND, FallType.FALL, 0.4D, 0.0D, NULL_AABB),
		NUCLEAR_WASTE(AimaggCategory.NUCLEAR, -100, MapColor.SILVER, -1.0F, 6000000.0F, SoundType.STONE, FallType.INSTAFALL, 1.0D, 1.0D, FULL_BLOCK_AABB);

		private final AimaggCategory category;
		private final int upperSortVal;
		
		private final MapColor mapColor;
		private final float hardness;
		private final float resistance;
		private final SoundType st;
		
		private final FallType ft;

		private final double speedMod;
		private final double speedMax;
		private final AxisAlignedBB aabb;
		
		private final ModelResourceLocation mrl;

		SoftBlockType(AimaggCategory cat, int upperSortVal, MapColor mapColor, float hardness, float resistance, SoundType st, FallType ft, double speedModifier, double speedMax, AxisAlignedBB collisionAABB) {
			this.category = cat;
			this.upperSortVal = upperSortVal;
			
			this.mapColor = mapColor;
			this.hardness = hardness;
			this.resistance = resistance;
			this.st = st;
			this.ft = ft;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":soft/" + this.getName());
			this.speedMod = speedModifier;
			this.speedMax = speedMax;
			this.aabb = collisionAABB;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
		public float getHardness() {
			return this.hardness;
		}
		
		public float getResistance() {
			return this.resistance;
		}
		
		public SoundType getSoundType() {
			return this.st;
		}

		public int getMetadata() {
			return this.ordinal();
		}
		
		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
		
		public boolean canFall() {
			return this.ft.canFall;
		}
		
		public boolean canInstafall() {
			return this.ft.instaFall;
		}
		
		public static SoftBlockType getFromMetadata(int meta) {
			return SoftBlockType.values()[meta];
		}

		public ModelResourceLocation getModel() {
			return this.mrl;
		}
		
		public AxisAlignedBB getBoundingBox() {
			return this.aabb;
		}
		
		public double getSpeedModifier() {
			return this.speedMod;
		}
		
		public double getMaxSpeed() {
			return this.speedMax;
		}

		public AimaggCategory getCategoryOverride() {
			return this.category;
		}

		public int getUpperSortValue() {
			return this.upperSortVal;
		}
		
		public static enum FallType {
			NONE(),
			FALL(false),
			INSTAFALL(true);
			
			protected boolean canFall = false;
			protected boolean instaFall = false;
			
			FallType() {}
			
			FallType(boolean instaFall) {
				this.canFall = true;
				this.instaFall = instaFall;
			}
		}
	}

}
