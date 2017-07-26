package com.joshmanisdabomb.aimagg.data;

import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public enum MissileType {
	EXPLOSIVE(0, false, "explosive", 1.3F, 0.01F, 1.04F, false),
	FIRE(1, false, "fire", 1.3F, 0.01F, 1.04F, false),
	NUCLEAR(2, true, "nuclear", 0.8F, 0.004F, 1.02F, true);

	private int metadata;
	public boolean useKilotons;
	
	private final ModelResourceLocation mrl;
	public boolean largeModel;
	private final ResourceLocation entityTexture;
	
	private float topSpeed;
	private float initialSpeed;
	private float speedModifier;

	MissileType(int metadata, boolean largeModel, String name, float topSpeed, float initialSpeed, float speedModifier, boolean useKilotons) {
		this.metadata = metadata;
		this.useKilotons = useKilotons;
		this.entityTexture = new ResourceLocation(Constants.MOD_ID + ":textures/entity/missile/" + (largeModel ? "large/" : "small/") + name + ".png");
		this.topSpeed = topSpeed;
		this.initialSpeed = initialSpeed;
		this.speedModifier = speedModifier;
		this.largeModel = largeModel;
		this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":missile/" + name, "inventory");
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
		return mrl;
	}
}