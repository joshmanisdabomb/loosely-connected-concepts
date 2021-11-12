package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow
    private RegistryKey<World> spawnPointDimension;

    @Shadow
    private BlockPos spawnPointPosition;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "setSpawnPoint", at = @At("HEAD"))
    public void setSpawn(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean spawnPointSet, boolean bl, CallbackInfo info) {
        if (pos != this.spawnPointPosition || !dimension.equals(this.spawnPointDimension)) {
            MinecraftServer server = this.getServer();
            if (server == null) return;
            ServerWorld world = server.getWorld(this.spawnPointDimension);
            if (world == null) return;
            ServerWorld world2 = server.getWorld(dimension);
            if (world2 == null) return;
            BlockState state = this.spawnPointPosition == null ? null : world.getBlockState(this.spawnPointPosition);
            BlockState state2 = pos == null ? null : world2.getBlockState(pos);
            Block block = state == null ? null : state.getBlock();
            Block block2 = state2 == null ? null : state2.getBlock();
            if (block instanceof LCCBlockTrait) ((LCCBlockTrait)block).lcc_spawnRemoved((ServerPlayerEntity)(Object)this, world, state, this.spawnPointPosition, world2, state2, pos, bodyYaw, spawnPointSet, bl);
            if (block2 instanceof LCCBlockTrait) ((LCCBlockTrait)block2).lcc_spawnSet((ServerPlayerEntity)(Object)this, world2, state2, pos, world, state, this.spawnPointPosition, bodyYaw, spawnPointSet, bl);
        }
    }

}