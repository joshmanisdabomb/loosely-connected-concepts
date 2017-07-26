package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggTEs {

	public static void init() {
		GameRegistry.registerTileEntity(AimaggTELaunchPad.class, "aimagg_launch_pad");
	}
	
}
