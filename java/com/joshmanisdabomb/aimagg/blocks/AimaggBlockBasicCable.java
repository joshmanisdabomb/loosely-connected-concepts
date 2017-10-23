package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AimaggBlockBasicCable extends AimaggBlockBasic {

	public static final PropertyBool N = PropertyBool.create("n");
	public static final PropertyBool E = PropertyBool.create("e");
	public static final PropertyBool S = PropertyBool.create("s");
	public static final PropertyBool W = PropertyBool.create("w");
	public static final PropertyBool U = PropertyBool.create("u");
	public static final PropertyBool D = PropertyBool.create("d");
	
	public static final AxisAlignedBB AABB_CABLE_CENTER = new AxisAlignedBB(6/16D, 6/16D, 6/16D, 10/16D, 10/16D, 10/16D);
	public static final AxisAlignedBB AABB_CABLE_NORTH = new AxisAlignedBB(6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D);
	public static final AxisAlignedBB AABB_CABLE_EAST = new AxisAlignedBB(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D);
	public static final AxisAlignedBB AABB_CABLE_SOUTH = new AxisAlignedBB(6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D);
	public static final AxisAlignedBB AABB_CABLE_WEST = new AxisAlignedBB(0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D);
	public static final AxisAlignedBB AABB_CABLE_UP = new AxisAlignedBB(6/16D, 10/16D, 6/16D, 10/16D, 16/16D, 10/16D);
	public static final AxisAlignedBB AABB_CABLE_DOWN = new AxisAlignedBB(6/16D, 0/16D, 6/16D, 10/16D, 6/16D, 10/16D);
	
	public AimaggBlockBasicCable(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.setLightOpacity(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(N, false).withProperty(E, false).withProperty(S, false).withProperty(W, false).withProperty(U, false).withProperty(D, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{N,E,S,W,U,D});
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
		return state.withProperty(N, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.north()), EnumFacing.NORTH))
		 			.withProperty(E, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.east()), EnumFacing.EAST))
		 			.withProperty(S, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.south()), EnumFacing.SOUTH))
		 			.withProperty(W, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.west()), EnumFacing.WEST))
		 			.withProperty(U, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.up()), EnumFacing.UP))
		 			.withProperty(D, this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.down()), EnumFacing.DOWN));
    }

	public abstract boolean doesConnectTo(IBlockAccess world, BlockPos pos, IBlockState self, IBlockState other, EnumFacing side);
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState other = blockAccess.getBlockState(pos.offset(side));
		return other.getBlock() != this && blockAccess.isSideSolid(pos.offset(side), side.getOpposite(), false);
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
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        AxisAlignedBB aabb = AABB_CABLE_CENTER;
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.north()), EnumFacing.NORTH)) aabb = aabb.union(AABB_CABLE_NORTH);
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.east()), EnumFacing.EAST)) aabb = aabb.union(AABB_CABLE_EAST);
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.south()), EnumFacing.SOUTH)) aabb = aabb.union(AABB_CABLE_SOUTH);
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.west()), EnumFacing.WEST)) aabb = aabb.union(AABB_CABLE_WEST);
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.up()), EnumFacing.UP)) aabb = aabb.union(AABB_CABLE_UP);
        if (this.doesConnectTo(source, pos, state, source.getBlockState(pos.down()), EnumFacing.DOWN)) aabb = aabb.union(AABB_CABLE_DOWN);
        return aabb;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_CENTER);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.north()), EnumFacing.NORTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_NORTH);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.east()), EnumFacing.EAST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_EAST);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.south()), EnumFacing.SOUTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_SOUTH);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.west()), EnumFacing.WEST)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_WEST);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.up()), EnumFacing.UP)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_UP);
        if (this.doesConnectTo(worldIn, pos, state, worldIn.getBlockState(pos.down()), EnumFacing.DOWN)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_CABLE_DOWN);
    }
	
}
