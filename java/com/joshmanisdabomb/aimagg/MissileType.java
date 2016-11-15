package com.joshmanisdabomb.aimagg;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public enum MissileType {
	EXPLOSIVE(0, false, new ResourceLocation("aimagg:textures/entity/missile/small/explosive.png"), new ModelResourceLocation(Constants.MOD_ID + ":missile/explosive", "inventory"), 1.3F, 0.01F, 1.04F, false),
	FIRE(1, false, new ResourceLocation("aimagg:textures/entity/missile/small/fire.png"), new ModelResourceLocation(Constants.MOD_ID + ":missile/fire", "inventory"), 1.3F, 0.01F, 1.04F, false),
	NUCLEAR(2, true, new ResourceLocation("aimagg:textures/entity/missile/large/nuclear.png"), new ModelResourceLocation(Constants.MOD_ID + ":missile/nuclear", "inventory"), 0.8F, 0.004F, 1.02F, true);

	private int metadata;
	public boolean useKilotons;
	
	private final ModelResourceLocation mrt;
	public boolean largeModel;
	private final ResourceLocation entityTexture;
	
	private float topSpeed;
	private float initialSpeed;
	private float speedModifier;

	MissileType(int metadata, boolean largeModel, ResourceLocation texture, ModelResourceLocation mrt, float topSpeed, float initialSpeed, float speedModifier, boolean useKilotons) {
		this.metadata = metadata;
		this.useKilotons = useKilotons;
		this.entityTexture = texture;
		this.topSpeed = topSpeed;
		this.initialSpeed = initialSpeed;
		this.speedModifier = speedModifier;
		this.largeModel = largeModel;
		this.mrt = mrt;
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

	public boolean usingKilotons() {
		return this.useKilotons;
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
	
	public ModelResourceLocation getItemModel() {
		return mrt;
	}
}