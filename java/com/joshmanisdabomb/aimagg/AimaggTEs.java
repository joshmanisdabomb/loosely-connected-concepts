package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.te.AimaggTESpreaderConstructor;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggTEs {

	public static void init() {
		GameRegistry.registerTileEntity(AimaggTESpreaderConstructor.class, "aimagg_spreader_controller");
	}
	
}
