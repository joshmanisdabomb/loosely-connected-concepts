package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import java.util.Collection;

public interface AdvancedBlockRender {

    IModelData DATA = new ModelDataMap.Builder().build();

    default ModelResourceLocation getCustomModelLocation() {
        return new ModelResourceLocation(new ResourceLocation(LCC.MODID, this.getRegistryName().getPath()), "");
    }

    IBakedModel newModel(Block block, IBakedModel defaultModel);

    ResourceLocation getRegistryName();

    Collection<ResourceLocation> getTextures();

}
