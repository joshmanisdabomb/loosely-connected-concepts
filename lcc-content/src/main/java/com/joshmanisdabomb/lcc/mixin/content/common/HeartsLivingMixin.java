package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType;
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class HeartsLivingMixin extends Entity {

    public HeartsLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float setDamageAmount(float amount) {
        return HeartType.calculateDamageAll((LivingEntity)(Object)this, amount);
    }

    @Redirect(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft"))
    private float modifyArmorReduction(float original, float armor, float toughness, DamageSource source) {
        float after = DamageUtil.getDamageLeft(original, armor, toughness);
        Entity entity = source.getSource();
        if (entity instanceof LCCContentEntityTrait) {
            return ((LCCContentEntityTrait)entity).lcc_content_applyDamageThroughArmor((LivingEntity)(Object)this, after, armor, toughness, original);
        }
        return after;
    }

}
