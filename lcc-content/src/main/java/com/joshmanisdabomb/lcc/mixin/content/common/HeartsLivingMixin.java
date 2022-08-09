package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType;
import com.joshmanisdabomb.lcc.directory.LCCEffects;
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class HeartsLivingMixin extends Entity {

    public HeartsLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    abstract boolean hasStatusEffect(StatusEffect effect);

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void heal(CallbackInfo info) {
        //TODO eventify for lcc hooks
        if (hasStatusEffect(LCCEffects.INSTANCE.getBleeding())) {
            info.cancel();
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float setDamageAmount(float amount) {
        //TODO eventify for lcc hooks
        return HeartType.calculateDamageAll((LivingEntity)(Object)this, amount);
    }

    @Redirect(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FFF)F"))
    private float modifyArmorReduction(float original, float armor, float toughness, DamageSource source) {
        float after = DamageUtil.getDamageLeft(original, armor, toughness);
        Entity entity = source.getSource();
        if (entity instanceof LCCContentEntityTrait) {
            return ((LCCContentEntityTrait)entity).lcc_content_applyDamageThroughArmor((LivingEntity)(Object)this, after, armor, toughness, original);
        }
        return after;
    }

    @Redirect(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F"))
    private float modifyProtectionReduction(float original, float protection, DamageSource source) {
        float after = DamageUtil.getInflictedDamage(original, protection);
        Entity entity = source.getSource();
        if (entity instanceof LCCContentEntityTrait) {
            return ((LCCContentEntityTrait)entity).lcc_content_applyDamageThroughProtection((LivingEntity)(Object)this, after, protection, original);
        }
        return after;
    }

}
