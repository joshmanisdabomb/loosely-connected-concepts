package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.dimension.rainbow.AimaggWorldProviderRainbow;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.common.DimensionManager;

public enum AimaggDimension {
	
	RAINBOW(AimaggWorldProviderRainbow.class);

	private final DimensionType dimType;
	private final int dimID;

	AimaggDimension(Class<? extends WorldProvider> provider) {
		this.dimType = DimensionType.register(this.name().toLowerCase(), this.getSuffix(), this.dimID = DimensionManager.getNextFreeDimId(), provider, false);
	}

	public int getDimensionID() {
		return this.dimID;
	}
	
	public String getSuffix() {
		return "_" + this.name().toLowerCase();
	}

	public DimensionType getDimensionType() {
		return this.dimType;
	}

	public static void init() {
		for (AimaggDimension d : AimaggDimension.values()) {
			DimensionManager.registerDimension(d.getDimensionID(), d.getDimensionType());
		}
	}

	public static AimaggDimension getDimensionFromID(int id) {
		for (AimaggDimension d : AimaggDimension.values()) {
			if (id == d.getDimensionID()) {
				return d;
			}
		}
		return null;
	}
	
}
