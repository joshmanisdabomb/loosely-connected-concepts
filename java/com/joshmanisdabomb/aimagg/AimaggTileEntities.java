package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;
import com.joshmanisdabomb.aimagg.te.AimaggTEClassicChest;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;
import com.joshmanisdabomb.aimagg.te.render.AimaggTESRBouncePad;
import com.joshmanisdabomb.aimagg.te.render.AimaggTESRLaunchPad;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggTileEntities {

	//TODO Hopper support.
	
	public static void init() {
		GameRegistry.registerTileEntity(AimaggTELaunchPad.class, "aimagg_launch_pad");
		GameRegistry.registerTileEntity(AimaggTEComputerCase.class, "aimagg_computer_case");
		GameRegistry.registerTileEntity(AimaggTEBouncePad.class, "aimagg_bounce_pad");
		GameRegistry.registerTileEntity(AimaggTEClassicChest.class, "aimagg_classic_chest");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(AimaggTELaunchPad.class, new AimaggTESRLaunchPad());
		ClientRegistry.bindTileEntitySpecialRenderer(AimaggTEBouncePad.class, new AimaggTESRBouncePad());
	}
	
}
