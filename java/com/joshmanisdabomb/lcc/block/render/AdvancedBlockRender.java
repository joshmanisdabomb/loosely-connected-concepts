package com.joshmanisdabomb.lcc.block.render;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import java.util.Collection;

public interface AdvancedBlockRender {

    @OnlyIn(Dist.CLIENT)
    IModelData DATA = new ModelDataMap.Builder().build();

    @OnlyIn(Dist.CLIENT)
    default ModelResourceLocation getCustomModelLocation() {
        return new ModelResourceLocation(new ResourceLocation(LCC.MODID, this.getRegistryName().getPath()), "");
    }

    @OnlyIn(Dist.CLIENT)
    IBakedModel newModel(Block block, IBakedModel defaultModel);

    ResourceLocation getRegistryName();

    @OnlyIn(Dist.CLIENT)
    Collection<ResourceLocation> getTextures();

}
