package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType;
import com.joshmanisdabomb.lcc.component.HeartsComponent;
import com.joshmanisdabomb.lcc.directory.component.LCCComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class HeartsPlayerMixin extends LivingEntity {

    protected HeartsPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float setDamageAmount(float amount) {
        //TODO eventify
        return HeartType.calculateDamageAll(this, amount);
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void heartHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> info) {
        HeartType damageLayer = (LCCComponents.INSTANCE.getHearts().maybeGet(this).map(HeartsComponent::getDamageLayer).orElse(HeartType.RED));
        SoundEvent sound = damageLayer.getHurtSound();
        if (sound != null) {
            info.setReturnValue(sound);
            info.cancel();
        }
    }

}
