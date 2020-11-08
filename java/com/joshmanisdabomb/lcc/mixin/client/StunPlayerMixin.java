package com.joshmanisdabomb.lcc.mixin.client;

import com.joshmanisdabomb.lcc.directory.LCCEffects;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class StunPlayerMixin extends PlayerEntity {

    @Shadow
    public Input input;

    public StunPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/TutorialManager;onMovement(Lnet/minecraft/client/input/Input;)V"))
    public void replaceMovement(CallbackInfo info) {
        if (this.hasStatusEffect(LCCEffects.INSTANCE.getStun())) {
            input.movementForward = input.movementSideways = 0.0F;
            input.pressingForward = input.pressingBack = input.pressingLeft = input.pressingRight = input.jumping = input.sneaking = false;
        }
    }

}