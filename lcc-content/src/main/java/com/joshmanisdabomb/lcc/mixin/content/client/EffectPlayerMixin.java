package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ClientPlayerEntity.class)
public abstract class EffectPlayerMixin extends PlayerEntity {

    @Shadow
    public Input input;

    public EffectPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/TutorialManager;onMovement(Lnet/minecraft/client/input/Input;)V"))
    public void replaceMovementWhenStun(CallbackInfo info) {
        Collection<StatusEffectInstance> effects = getStatusEffects();
        for (StatusEffectInstance effect : effects) {
            if (effect.getEffectType() instanceof LCCContentEffectTrait leffect) {
                leffect.lcc_content_modifyPlayerInput((ClientPlayerEntity)(Object)this, input);
            }
        }
    }

}