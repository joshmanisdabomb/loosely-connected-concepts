package com.joshmanisdabomb.aimagg.event;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockAdvancedRendering;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggModelHandler {

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event) {
		/*for (Block b : AimaggBlocks.advancedRenderRegistry) {
			ModelResourceLocation mrl = ((AimaggBlockAdvancedRendering)b).getCustomModelLocation();
			Object o = event.getModelRegistry().getObject(mrl);
			if (o instanceof IBakedModel) {
			    event.getModelRegistry().putObject(mrl, ((AimaggBlockAdvancedRendering)b).getBakedModel());
			}
		}*/
	}
	
}
