package com.joshmanisdabomb.lcc.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class FlammableEffect extends HurtResistanceEffect implements LCCEffectHelper {

    public FlammableEffect(EffectType type, int color) {
        super(type, color, (source, amplifier) -> source.isFireDamage() ? Math.pow(0.9, (amplifier + 1)) : 1.0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        int fireTicks = entity.func_223314_ad();
        int fireLength = (amplifier + 5) * 40;
        if (fireTicks < (fireLength - 10) && entity.world.isFlammableWithin(entity.getBoundingBox().shrink(0.001D))) {
            entity.func_223308_g(fireLength);
        }
        if (fireTicks % 20 != 0 && fireTicks % Math.max(20 / (amplifier+2), 1) == 0) {
            entity.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
        }
    }

    @Override
    public boolean isPotionApplicable(LivingEntity entity, EffectInstance effect) {
        return !entity.isImmuneToFire();
    }

}
