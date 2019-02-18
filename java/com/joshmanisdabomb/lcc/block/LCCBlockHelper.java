package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;

interface LCCBlockHelper {

    IBlockState getDefaultState();

    default IBlockState traitHorizontalPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    default IBlockState traitDirectionalPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    default IBlockState traitPillarPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.AXIS, context.getFace().getAxis());
    }

}
