package com.joshmanisdabomb.aimagg.te;

import net.minecraft.tileentity.TileEntity;

public class AimaggTEBillieTiles extends TileEntity {

	private static final double DOUBLE_MAX_OVER_2 = Double.MAX_VALUE/2;

	@Override
	public double getMaxRenderDistanceSquared() {
		return DOUBLE_MAX_OVER_2;
	}
	
}
