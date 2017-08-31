package com.joshmanisdabomb.aimagg;

import net.minecraft.util.DamageSource;

public class AimaggDamage extends DamageSource {
	
    public static final DamageSource PILL = (new DamageSource("pill")).setDamageBypassesArmor().setDamageIsAbsolute();

	public AimaggDamage(String internalName) {
		super(internalName);
	}

}
