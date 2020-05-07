package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.block.model.DefaultModelAcceptor;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCModels;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ResourceEvents {

    @SubscribeEvent
    public static void onModelRegisterEvent(final ModelRegistryEvent e) {
        LCCModels.register(e);

        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            ((AdvancedBlockRender) block).getSpecialModels().forEach(ModelLoader::addSpecialModel);
        });
    }

    @SubscribeEvent
    public static void onModelBakeEvent(final ModelBakeEvent e) {
        //Advanced block models.
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            IBakedModel model = ((AdvancedBlockRender)block).newModel(block);
            for (ModelResourceLocation mrl : ((AdvancedBlockRender)block).getModelLocations()) {
                IBakedModel old = e.getModelRegistry().get(mrl);
                if (old != null) {
                    if (model instanceof DefaultModelAcceptor) {
                        ((DefaultModelAcceptor)model).acceptModel(mrl, old);
                    }
                    e.getModelRegistry().put(mrl, model);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onTextureStitch(final TextureStitchEvent.Pre e) {
        if (e.getMap().getTextureLocation() != AtlasTexture.LOCATION_BLOCKS_TEXTURE) return;
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            ((AdvancedBlockRender)block).getTextures().forEach(e::addSprite);
        });
    }

}
