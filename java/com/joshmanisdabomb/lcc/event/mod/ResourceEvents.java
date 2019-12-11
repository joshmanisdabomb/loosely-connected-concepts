package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ResourceEvents {

    @SubscribeEvent
    public static void onModelBakeEvent(final ModelBakeEvent e) {
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            IBakedModel model = ((AdvancedBlockRender)block).newModel(block);
            for (ModelResourceLocation mrl : ((AdvancedBlockRender)block).getModelLocations()) {
                if (e.getModelRegistry().get(mrl) != null) {
                    e.getModelRegistry().put(mrl, model);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onTextureStitch(final TextureStitchEvent.Pre e) {
        if (!e.getMap().getBasePath().equals("textures")) return;
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            ((AdvancedBlockRender) block).getTextures().forEach(e::addSprite);
        });
    }

}
