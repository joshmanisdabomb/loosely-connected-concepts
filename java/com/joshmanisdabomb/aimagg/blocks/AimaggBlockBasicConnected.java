package com.joshmanisdabomb.aimagg.blocks;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.model.AimaggBlockConnectedTextureModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public class AimaggBlockBasicConnected extends AimaggBlockBasic implements AimaggBlockAdvancedRendering {

	//TODO figure out inside corners
	
	public static final IUnlistedProperty<Boolean> N = Properties.toUnlisted(PropertyBool.create("n"));
	public static final IUnlistedProperty<Boolean> E = Properties.toUnlisted(PropertyBool.create("e"));
    public static final IUnlistedProperty<Boolean> S = Properties.toUnlisted(PropertyBool.create("s"));
    public static final IUnlistedProperty<Boolean> W = Properties.toUnlisted(PropertyBool.create("w"));
    public static final IUnlistedProperty<Boolean> U = Properties.toUnlisted(PropertyBool.create("u"));
    public static final IUnlistedProperty<Boolean> D = Properties.toUnlisted(PropertyBool.create("d"));
	public static final IUnlistedProperty<Boolean> NE = Properties.toUnlisted(PropertyBool.create("ne"));
    public static final IUnlistedProperty<Boolean> SE = Properties.toUnlisted(PropertyBool.create("se"));
    public static final IUnlistedProperty<Boolean> NW = Properties.toUnlisted(PropertyBool.create("nw"));
    public static final IUnlistedProperty<Boolean> SW = Properties.toUnlisted(PropertyBool.create("sw"));
	public static final IUnlistedProperty<Boolean> NU = Properties.toUnlisted(PropertyBool.create("nu"));
    public static final IUnlistedProperty<Boolean> EU = Properties.toUnlisted(PropertyBool.create("eu"));
    public static final IUnlistedProperty<Boolean> SU = Properties.toUnlisted(PropertyBool.create("su"));
    public static final IUnlistedProperty<Boolean> WU = Properties.toUnlisted(PropertyBool.create("wu"));
	public static final IUnlistedProperty<Boolean> ND = Properties.toUnlisted(PropertyBool.create("nd"));
    public static final IUnlistedProperty<Boolean> ED = Properties.toUnlisted(PropertyBool.create("ed"));
    public static final IUnlistedProperty<Boolean> SD = Properties.toUnlisted(PropertyBool.create("sd"));
    public static final IUnlistedProperty<Boolean> WD = Properties.toUnlisted(PropertyBool.create("wd"));
    
	protected final String textureFolder;
	protected final ModelResourceLocation model;
	
	private final ConnectionType connectionType;

	public AimaggBlockBasicConnected(ConnectionType ct, String internalName, String textureFolder, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		
		this.model = new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "normal");
		this.connectionType = ct;
		this.textureFolder = textureFolder;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{N,E,S,W,U,D,NE,SE,NW,SW,NU,EU,SU,WU,ND,ED,SD,WD});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state;
    }
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState estate = (IExtendedBlockState)state;
        return estate.withProperty(N, isConnected(state, world, pos, EnumFacing.NORTH.getDirectionVec()))
                     .withProperty(E, isConnected(state, world, pos, EnumFacing.EAST.getDirectionVec()))
                     .withProperty(S, isConnected(state, world, pos, EnumFacing.SOUTH.getDirectionVec()))
                     .withProperty(W, isConnected(state, world, pos, EnumFacing.WEST.getDirectionVec()))
                     .withProperty(U, isConnected(state, world, pos, EnumFacing.UP.getDirectionVec()))
                     .withProperty(D, isConnected(state, world, pos, EnumFacing.DOWN.getDirectionVec()))
                     .withProperty(NE, isConnected(state, world, pos, new BlockPos(EnumFacing.NORTH.getDirectionVec()).add(EnumFacing.EAST.getDirectionVec())))
                     .withProperty(SE, isConnected(state, world, pos, new BlockPos(EnumFacing.SOUTH.getDirectionVec()).add(EnumFacing.EAST.getDirectionVec())))
                     .withProperty(NW, isConnected(state, world, pos, new BlockPos(EnumFacing.NORTH.getDirectionVec()).add(EnumFacing.WEST.getDirectionVec())))
                     .withProperty(SW, isConnected(state, world, pos, new BlockPos(EnumFacing.SOUTH.getDirectionVec()).add(EnumFacing.WEST.getDirectionVec())))
                     .withProperty(NU, isConnected(state, world, pos, new BlockPos(EnumFacing.NORTH.getDirectionVec()).add(EnumFacing.UP.getDirectionVec())))
                     .withProperty(EU, isConnected(state, world, pos, new BlockPos(EnumFacing.EAST.getDirectionVec()).add(EnumFacing.UP.getDirectionVec())))
                     .withProperty(SU, isConnected(state, world, pos, new BlockPos(EnumFacing.SOUTH.getDirectionVec()).add(EnumFacing.UP.getDirectionVec())))
                     .withProperty(WU, isConnected(state, world, pos, new BlockPos(EnumFacing.WEST.getDirectionVec()).add(EnumFacing.UP.getDirectionVec())))
                     .withProperty(ND, isConnected(state, world, pos, new BlockPos(EnumFacing.NORTH.getDirectionVec()).add(EnumFacing.DOWN.getDirectionVec())))
                     .withProperty(ED, isConnected(state, world, pos, new BlockPos(EnumFacing.EAST.getDirectionVec()).add(EnumFacing.DOWN.getDirectionVec())))
                     .withProperty(SD, isConnected(state, world, pos, new BlockPos(EnumFacing.SOUTH.getDirectionVec()).add(EnumFacing.DOWN.getDirectionVec())))
                     .withProperty(WD, isConnected(state, world, pos, new BlockPos(EnumFacing.WEST.getDirectionVec()).add(EnumFacing.DOWN.getDirectionVec())));
	}

	private static boolean isConnected(IBlockState state, IBlockAccess worldIn, BlockPos pos, Vec3i addition) {
		IBlockState other = worldIn.getBlockState(pos.add(addition));
		return state.getBlock() == other.getBlock() && state.getBlock().getMetaFromState(state) == other.getBlock().getMetaFromState(other);
	}

	@Override
	public ModelResourceLocation getCustomModelLocation() {
		return this.model;
	}

	@Override
	public IModel newModel(Block b) {
		return new AimaggBlockConnectedTextureModel(b);
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		if (this.connectionType.hasThreeTextures()) {
			return ImmutableSet.of(new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/top_base"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/top_corners"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/top_lines_h"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/top_lines_v"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/side_base"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/side_corners"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/side_lines_h"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/side_lines_v"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/bottom_base"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/bottom_corners"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/bottom_lines_h"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/bottom_lines_v"));
		} else {
			return ImmutableSet.of(new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/base"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/corners"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/lines_h"),
								   new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/lines_v"));
		}
	}
	
	public static enum ConnectionType {
		STANDARD_ONE_TEXTURE(false, false),
		STANDARD_THREE_TEXTURE(false, true),
		INSIDE_ONE_TEXTURE(true, false),
		INSIDE_THREE_TEXTURE(true, true);
		
		private final boolean insideCorners;
		private final boolean threeTextures;

		ConnectionType(boolean insideCorners, boolean threeTextures) {
			this.insideCorners = insideCorners;
			this.threeTextures = threeTextures;
		}
		
		public boolean includesInsideCorners() {
			return this.insideCorners;
		}
		
		public boolean hasThreeTextures() {
			return this.threeTextures;
		}
	}
	
}
