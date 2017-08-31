package com.joshmanisdabomb.aimagg.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggBlockBasicFacingAny extends AimaggBlockBasic {

	public static final PropertyDirection ANY_FACING = PropertyDirection.create("facing");

	//TODO Interact with wrenches that rotate blocks.

	public AimaggBlockBasicFacingAny(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.alwaysDropWithDamage(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ANY_FACING, EnumFacing.NORTH));
	}

	@Override
	public int getLowerSortValue(ItemStack is) {
		return 0;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(ANY_FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
    	return state.getValue(ANY_FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ANY_FACING, EnumFacing.getFront(meta));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, ANY_FACING);
	}
	
}
