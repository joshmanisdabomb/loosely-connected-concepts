package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggDamage;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockSpikes extends AimaggBlockBasic {

	public static final PropertyEnum<SpikesType> TYPE = PropertyEnum.<SpikesType>create("type", SpikesType.class);

	public static final AxisAlignedBB SPIKES_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 6/16D, 1.0D);
	
	public AimaggBlockSpikes(String internalName, Material material) {
		super(internalName, material, MapColor.GRAY);
		this.setLightOpacity(0);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, SpikesType.PLAIN));
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SPIKES_AABB;
    }
	
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, SPIKES_AABB);
    }
    
    @Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, SpikesType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (SpikesType s : SpikesType.values()) {
        	items.add(new ItemStack(this, 1, s.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (SpikesType s : SpikesType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), s.getMetadata(), s.getModel());
        }
	}

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
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
				return super.getUnlocalizedName() + "." + SpikesType.getFromMetadata(stack.getMetadata()).getName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(TYPE).getMapColor();
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityItem) {return;}
		entityIn.attackEntityFrom(AimaggDamage.causeSpikesDamage(state.getValue(TYPE)), state.getValue(TYPE).getDamage());
		if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).isCreative())) {
			for (PotionEffect e : state.getValue(TYPE).getEffects()) {
				((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(e.getPotion(), 150, e.getAmplifier()));
			}
		}
	}
	
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getBlockState(pos.down()).getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID) {
    		worldIn.destroyBlock(pos, true);
        }
    }
    
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
	
    public static enum SpikesType implements IStringSerializable {
		PLAIN(MapColor.GRAY, 4.0F, new PotionEffect[]{}),
		BLOODY(MapColor.RED_STAINED_HARDENED_CLAY, 7.0F, new PotionEffect[]{}),
		POISON(MapColor.GREEN_STAINED_HARDENED_CLAY, 4.0F, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 500, 0)}),
		WITHER(MapColor.BLACK_STAINED_HARDENED_CLAY, 4.0F, new PotionEffect[]{new PotionEffect(MobEffects.WITHER, 500, 0)}),
		AMPLIFIED(MapColor.YELLOW_STAINED_HARDENED_CLAY, 4.0F, new PotionEffect[]{new PotionEffect(MobEffects.WEAKNESS, 500, 0)}); //TODO change to amplify damage
    	
    	private final ModelResourceLocation mrl;
    	
		private final MapColor mapColor;
		private final float damage;
		private final PotionEffect[] effects;

		SpikesType(MapColor mcolor, float damage, PotionEffect[] effects) {
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":wasteland/spikes/" + this.getName());
			this.mapColor = mcolor;
			this.damage = damage;
			this.effects = effects;
    	}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public ModelResourceLocation getModel() {
			return this.mrl;
		}

		public int getMetadata() {
			return this.ordinal();
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
		public float getDamage() {
			return this.damage;
		}
		
		public PotionEffect[] getEffects() {
			return this.effects;
		}

		public static SpikesType getFromMetadata(int meta) {
			return SpikesType.values()[meta];
		}

	}

}
