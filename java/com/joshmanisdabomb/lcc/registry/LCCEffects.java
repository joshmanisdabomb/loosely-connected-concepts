package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.potion.FlammableEffect;
import com.joshmanisdabomb.lcc.potion.HurtResistanceEffect;
import com.joshmanisdabomb.lcc.potion.RadiationEffect;
import com.joshmanisdabomb.lcc.potion.StunEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCEffects {

    public static final ArrayList<Effect> all = new ArrayList<>();

    public static StunEffect stun;
    public static HurtResistanceEffect vulnerable;
    public static FlammableEffect flammable;
    public static RadiationEffect radiation;

    public static void init(RegistryEvent.Register<Effect> e) {
        all.add((stun = new StunEffect(EffectType.HARMFUL, 0x00ffd8))
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", -1, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .setRegistryName(LCC.MODID, "stun")
        );
        all.add((vulnerable = new HurtResistanceEffect(EffectType.HARMFUL, 0x282457, (source, amplifier) -> Math.pow(0.9, (amplifier + 1)))).setRegistryName(LCC.MODID, "vulnerable"));
        all.add((flammable = new FlammableEffect(EffectType.HARMFUL, 0x825933)).setRegistryName(LCC.MODID, "flammable"));
        all.add((radiation = new RadiationEffect(EffectType.HARMFUL, 0xc3db9a)).setRegistryName(LCC.MODID, "radiation"));
    }

}
