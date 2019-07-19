package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.potion.StunEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCEffects {

    public static final ResourceLocation POTION_TEXTURE = new ResourceLocation(LCC.MODID, "textures/gui/potions.png");

    public static final ArrayList<Effect> all = new ArrayList<>();

    public static Effect stun;

    public static void init(RegistryEvent.Register<Effect> e) {
        all.add(stun = new StunEffect(EffectType.HARMFUL, 0x00ffd8)
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", (double)-1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .setRegistryName(LCC.MODID, "stun"));
    }

}
