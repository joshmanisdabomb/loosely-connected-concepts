package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.directory.LCCBlocks;
import com.joshmanisdabomb.lcc.trait.LCCEntityTrait;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(SpawnHelper.class)
public abstract class PitSpawnHelperMixin {

    @ModifyArg(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SpawnHelper;isAcceptableSpawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos$Mutable;D)Z", args = {"log=true"}), index = 3)
    private static double modifySpawnDistance(ServerWorld sworld, Chunk chunk, BlockPos.Mutable pos, double distance) {
        BlockState state = sworld.getBlockState(pos.down());
        if (state.getBlock() == LCCBlocks.INSTANCE.getSpawning_pit()) {
            System.out.println(state);
            System.out.println(distance);
            return 577.0;
        }
        return distance;
    }

}