package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Collection;

public interface MultipartBlock {

    Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos);

    default boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        return false;
    }

}