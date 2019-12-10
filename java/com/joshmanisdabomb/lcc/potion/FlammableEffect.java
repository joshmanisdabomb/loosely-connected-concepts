package com.joshmanisdabomb.lcc.potion;

import net.minecraft.potion.EffectType;

public class FlammableEffect extends HurtResistanceEffect {

    public FlammableEffect(EffectType type, int color) {
        super(type, color, (source, amplifier) -> source.isFireDamage() ? Math.pow(0.9, (amplifier + 1)) : 1.0);
    }

}
