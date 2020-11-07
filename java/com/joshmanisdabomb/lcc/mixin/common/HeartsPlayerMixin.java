package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.concepts.hearts.HeartType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class HeartsPlayerMixin extends LivingEntity {

    protected HeartsPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float setDamageAmount(float amount) {
        return HeartType.calculateDamageAll(this, amount);
    }

}
