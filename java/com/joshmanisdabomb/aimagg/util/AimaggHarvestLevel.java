package com.joshmanisdabomb.aimagg.util;

public class AimaggHarvestLevel {

	private final Specialization s;
	private final int level;
	
	public AimaggHarvestLevel(Specialization s, int level) {
		this.s = s;
		this.level = level;
	}
	
	public static enum Specialization {
		RAINBOW;
	}
	
}
