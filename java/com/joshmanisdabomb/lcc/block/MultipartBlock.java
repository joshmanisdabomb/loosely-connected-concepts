package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface MultipartBlock {

    Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos);

    /**
     * Override what happens when a specific part of the multipart block is broken by a player.
     * Return false to default to vanilla breaking behaviour.
     */
    default boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        return false;
    }

    /**
     * Simulates the effects of harvesting of a single part of the multipart block.
     */
    default void harvestPartEffects(BlockState singlePart, IWorld world, BlockPos pos) {
        world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, pos, Block.getStateId(singlePart));
    }

    /**
     * Drops the single part of the multipart block.
     */
    default void spawnPartDrops(BlockState singlePart, World world, BlockPos pos) {
        Block.spawnDrops(singlePart, world, pos, null);
    }

    /**
     * Use this function to show destroy effects for the correct segment or to stop the break sound from playing more than once.
     * @see CogBlock#onShapeHarvested(BlockState, IWorld, BlockPos, PlayerEntity, VoxelShape)
     * @see CogBlock#getSoundType(BlockState)
     */
    default boolean onePart(BlockState state, IWorld world, BlockPos pos) {
        return this.getParts(state, world, pos).size() == 1;
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