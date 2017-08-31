package com.joshmanisdabomb.aimagg.util;

import java.util.Random;
import java.util.UUID;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.PillsProvider;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

public enum PillModifier {
	
	//TODO all numbers apart from speed and health need tweaking
	EXTRA_HEALTH(SharedMonsterAttributes.MAX_HEALTH, UUID.fromString("16be03fe-63b0-4074-b1ac-21cdffce67ae"), ModifierOperation.ADD, 0.0F, 20.0F, -18.0F, 2.0F, 2.0F),
	EXTRA_SPEED(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.fromString("ab748df6-e03c-4c40-8b04-850f2c183766"), ModifierOperation.ADDITIVE_MULTIPLY, 0.0F, 0.12F, -0.06F, 0.03F, 0.01F),
	EXTRA_LUCK(SharedMonsterAttributes.LUCK, UUID.fromString("d76a0652-0a0e-4be1-8b62-6aeed529920d"), ModifierOperation.ADD, 0.0F, 25.0F, -25.0F, 1.0F, 1.0F),
	EXTRA_DAMAGE(SharedMonsterAttributes.ATTACK_DAMAGE, UUID.fromString("76718380-34f8-41a4-9c01-875732b300d7"), ModifierOperation.ADDITIVE_MULTIPLY, 0.0F, 2.0F, -0.3F, 0.15F, 0.04F),
	EXTRA_ATTACK_SPEED(SharedMonsterAttributes.ATTACK_SPEED, UUID.fromString("b9c62130-8c99-4b8d-8cf1-8bb6798365c1"), ModifierOperation.ADDITIVE_MULTIPLY, 0.0F, 2.0F, -0.3F, 0.15F, 0.04F);
	
	private final IAttribute attribute;
	private final UUID modifierUUID;
	private final ModifierOperation op;
	private final float initial;
	private final float maximum;
	private final float minimum;
	private final float maxOnUse;
	private final float minOnUse;

	PillModifier(IAttribute a, UUID uuid, ModifierOperation op, float initial, float maximum, float minimum, float maxOnUse, float minOnUse) {
		this.attribute = a;
		this.modifierUUID = uuid;
		this.op = op;
		this.initial = initial;
		this.maximum = maximum;
		this.minimum = minimum;
		this.maxOnUse = maxOnUse;
		this.minOnUse = minOnUse;
	}
	
	private static float getExtraAmountForModifier(PillModifier p, EntityPlayer player) {
		IPills pills = player.getCapability(PillsProvider.PILLS_CAPABILITY, null);
		switch (p) {
			case EXTRA_SPEED:
				return pills.getExtraSpeed();
			case EXTRA_LUCK:
				return pills.getExtraLuck();
			case EXTRA_DAMAGE:
				return pills.getExtraDamage();
			case EXTRA_ATTACK_SPEED:
				return pills.getExtraAttackSpeed();
			default:
				return pills.getExtraHealth();
		}
	}

	public static void applyAllModifiers(EntityPlayer player) {
		IPills pills = player.getCapability(PillsProvider.PILLS_CAPABILITY, null);

		for (PillModifier p : PillModifier.values()) {
			player.getAttributeMap().getAttributeInstance(p.attribute).removeModifier(p.modifierUUID);
			player.getAttributeMap().getAttributeInstance(p.attribute).applyModifier(new AttributeModifier(p.modifierUUID, "Pill " + p.name().substring(6).toLowerCase().replace('_', ' ') + " boost", PillModifier.getExtraAmountForModifier(p, player), 0));
		}
	}
	
	public float getInitialAmount() {
		return this.initial;
	}

	public float getMaximumAmount() {
		return this.maximum;
	}

	public float getMinimumAmount() {
		return this.minimum;
	}
	
	public float getMaximumAmountOnUse() {
		return this.maxOnUse;
	}
	
	public float getMinimumAmountOnUse() {
		return this.minOnUse;
	}
	
	public float getRandomAmountOnUse(Random r) {
		if (this.getMaximumAmountOnUse() == this.getMinimumAmountOnUse()) {
			return this.getMaximumAmountOnUse();
		} else {
			return (r.nextFloat() * (this.getMaximumAmountOnUse() - this.getMinimumAmountOnUse())) + this.getMinimumAmountOnUse();
		}
	}
	
}
