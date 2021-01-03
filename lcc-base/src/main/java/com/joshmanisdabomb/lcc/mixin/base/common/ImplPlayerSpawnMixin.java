package com.joshmanisdabomb.lcc.mixin.base.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(PlayerManager.class)
public abstract class ImplPlayerSpawnMixin {

    private ServerPlayerEntity spawningPlayer = null;

    @Inject(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;findRespawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;"))
    private void cachePlayer(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> info) {
        spawningPlayer = player;
    }

    @Redirect(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;findRespawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;"))
    private Optional<Vec3d> findRespawn(ServerWorld world, BlockPos pos, float yaw, boolean spawnPointSet, boolean alive) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof LCCExtendedBlock) {
            Optional<Vec3d> ret = ((LCCExtendedBlock)block).lcc_spawnOn(spawningPlayer, world, state, pos, yaw, spawnPointSet, alive);
            if (ret != null) return ret;
        }
        return PlayerEntity.findRespawnPosition(world, pos, yaw, spawnPointSet, alive);
    }

    @Inject(method = "respawnPlayer", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void newPlayerConsume(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> info, BlockPos pos, float yaw, boolean spawnPointSet, ServerWorld world, Optional<Vec3d> optional, ServerWorld spawn, ServerPlayerEntity clone) {
        if (player == null || clone == null || world == null || spawn == null || pos == null) return;
        BlockState state = spawn.getBlockState(pos);
        if (state == null) return;
        Block block = state.getBlock();
        if (block == null) return;
        if (block instanceof LCCExtendedBlock) {
            ((LCCExtendedBlock)block).lcc_spawnAfter(clone, spawn, state, pos, yaw, spawnPointSet, alive);
        }
    }

}
