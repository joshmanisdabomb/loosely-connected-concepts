package com.joshmanisdabomb.lcc.potion;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

public class RadiationEffect extends Effect {

    public RadiationEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        IAttributeInstance attribute = entity.getAttributes().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);

        UUID uuid = UUID.fromString("e322834e-028f-481c-bc3e-53f0065bb8ec");
        if (attribute != null) {
            AttributeModifier current = attribute.getModifier(uuid);
            AttributeModifier modifier = new AttributeModifier(uuid, this::getName, MathHelper.clamp((current != null ? current.getAmount() : 0) - (0.005F * (amplifier + 1)), Math.min(4 - entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue(), -entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() / 2), 0), AttributeModifier.Operation.ADDITION);
            attribute.removeModifier(uuid);
            attribute.applyModifier(modifier);
            if (entity.getHealth() > entity.getMaxHealth()) {
                entity.setHealth(entity.getMaxHealth());
            }
        }
    }

}
