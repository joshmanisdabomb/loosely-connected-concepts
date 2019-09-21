package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface MultipartBlock {

    Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos);

    default boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        return false;
    }

    default BlockState updateEmptyState(BlockState check) {
        return check;
    }

    static BlockRayTraceResult rayTrace(PlayerEntity player, IWorld world) {
        double reach = player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue() - (player.isCreative() ? 0 : 0.5F);
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d look = player.getLookVec();
        Vec3d end = start.add(look.x * reach, look.y * reach, look.z * reach);
        return world.rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
    }

    @Nonnull
    default List<VoxelShape> getPartsFromTrace(PlayerEntity player, BlockState state, IWorld world, BlockPos pos) {
        BlockRayTraceResult target = rayTrace(player, world);
        if (target.getType() != RayTraceResult.Type.BLOCK) return Collections.emptyList();
        return this.getPartsFromTrace(target.getHitVec(), state, world, pos);
    }

    @Nonnull
    default List<VoxelShape> getPartsFromTrace(Vec3d hitVec, BlockState state, IWorld world, BlockPos pos) {
        return this.getParts(state, world, pos).stream().filter(s -> s.toBoundingBoxList().stream().anyMatch(box -> box.grow(0.001F).contains(hitVec.subtract(pos.getX(), pos.getY(), pos.getZ())))).collect(Collectors.toList());
    }

    @Nullable
    default VoxelShape getPartFromTrace(PlayerEntity player, BlockState state, IWorld world, BlockPos pos) {
        BlockRayTraceResult target = rayTrace(player, world);
        return this.getPartFromTrace(target.getHitVec(), state, world, pos);
    }

    @Nullable
    default VoxelShape getPartFromTrace(Vec3d hitVec, BlockState state, IWorld world, BlockPos pos) {
        List<VoxelShape> s = this.getPartsFromTrace(hitVec, state, world, pos);
        return s.isEmpty() ? null : s.get(0);
    }

}