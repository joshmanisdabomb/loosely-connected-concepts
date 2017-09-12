package com.joshmanisdabomb.aimagg.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggBlockBasicAxis extends AimaggBlockBasic {

    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class);

	//TODO Interact with wrenches that rotate blocks.

	public AimaggBlockBasicAxis(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.alwaysDropWithDamage(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
	}

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    	return this.getStateFromMeta(meta).withProperty(AXIS, facing.getAxis());
    }
    
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AXIS).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta]);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS);
	}
	
}
