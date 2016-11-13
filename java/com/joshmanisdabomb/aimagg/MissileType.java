package com.joshmanisdabomb.aimagg;

import net.minecraft.util.ResourceLocation;

public enum MissileType {
	EXPLOSIVE(0, false, new ResourceLocation("aimagg:textures/entity/missile/small/explosive.png"), 1.3F, 0.01F, 1.04F, true),
	FIRE(1, false, new ResourceLocation("aimagg:textures/entity/missile/small/fire.png"), 1.4F, 0.04F, 1.01F, true),
	NUCLEAR(2, true, new ResourceLocation("aimagg:textures/entity/missile/large/nuclear.png"), 0.8F, 0.004F, 1.02F, false);

	private int metadata;
	public boolean showStrength;
	public boolean largeModel;
	private final ResourceLocation entityTexture;
	
	private float topSpeed;
	private float initialSpeed;
	private float speedModifier;

	MissileType(int metadata, boolean largeModel, ResourceLocation texture, float topSpeed, float initialSpeed, float speedModifier, boolean showStrength) {
		this.metadata = metadata;
		this.showStrength = showStrength;
		this.entityTexture = texture;
		this.topSpeed = topSpeed;
		this.initialSpeed = initialSpeed;
		this.speedModifier = speedModifier;
		this.largeModel = largeModel;
	}

	public static MissileType getFromMetadata(int metadata) {
		for (MissileType m : values()) {
			if (metadata == m.metadata) {
				return m;
			}
		}
		return null;
	}

	public int getMetadata() {
		return this.metadata;
	}

	public boolean showStrength() {
		return this.showStrength;
	}

	public ResourceLocation getEntityTexture() {
		return this.entityTexture;
	}
	
	public float getTopSpeed() {
		return topSpeed;
	}

	public float getInitialSpeed() {
		return initialSpeed;
	}

	public float getSpeedModifier() {
		return speedModifier;
	}

	public boolean useLargeModel() {
		return largeModel;
	}
}