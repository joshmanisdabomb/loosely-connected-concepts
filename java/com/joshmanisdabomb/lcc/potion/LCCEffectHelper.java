package com.joshmanisdabomb.lcc.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

public interface LCCEffectHelper {

    boolean isPotionApplicable(LivingEntity entity, EffectInstance effect);

}