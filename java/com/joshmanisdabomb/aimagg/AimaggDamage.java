package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpikes.SpikesType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class AimaggDamage extends DamageSource {
	
    public static final DamageSource PILL = (new AimaggDamage("aimagg:pill")).setDamageBypassesArmor().setDamageIsAbsolute();

    private SpikesType spikes = null;
    
	public AimaggDamage(String internalName) {
		super(internalName);
	}
	
	public static DamageSource causeSpikesDamage(SpikesType s) {
        return new AimaggDamage("aimagg:spikes").setSpikesType(s);
    }

	private DamageSource setSpikesType(SpikesType s) {
		this.spikes = s;
		return this;
	}

	public SpikesType getSpikesType() {
		return this.spikes;
	}

}
