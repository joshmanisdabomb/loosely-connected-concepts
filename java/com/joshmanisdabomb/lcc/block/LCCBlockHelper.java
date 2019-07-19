package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;

interface LCCBlockHelper {

    BlockState getDefaultState();

    default BlockState traitHorizontalPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    default BlockState traitDirectionalPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    default BlockState traitPillarPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.AXIS, context.getFace().getAxis());
    }

}
