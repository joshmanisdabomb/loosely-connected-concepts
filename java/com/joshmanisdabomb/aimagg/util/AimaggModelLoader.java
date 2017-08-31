package com.joshmanisdabomb.aimagg.util;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockAdvancedRendering;

import net.minecraft.block.Block;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class AimaggModelLoader implements ICustomModelLoader {

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		for (Block b : AimaggBlocks.advancedRenderRegistry) {
			if (modelLocation.getResourceDomain().equals(Constants.MOD_ID) && !modelLocation.getResourcePath().contains("models/item") && !modelLocation.getResourcePath().contains("models/block") && modelLocation.getResourcePath().contains(((AimaggBlockAdvancedRendering)b).getInternalName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {		
		int indexOfSlash = modelLocation.getResourcePath().toString().lastIndexOf("/");
		Block b = indexOfSlash > -1 ? Block.getBlockFromName(modelLocation.getResourceDomain() + ":" + modelLocation.getResourcePath().toString().substring(indexOfSlash)) : Block.getBlockFromName(modelLocation.getResourceDomain() + ":" + modelLocation.getResourcePath().toString());
		if (b != null) {
			return ((AimaggBlockAdvancedRendering)b).newModel(b);
		}
		return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

}
