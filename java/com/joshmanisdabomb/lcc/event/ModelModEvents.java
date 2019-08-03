package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.block.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModelModEvents {

    @SubscribeEvent
    public static void onModelBakeEvent(final ModelBakeEvent e) {
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            ModelResourceLocation mrl = ((AdvancedBlockRender)block).getCustomModelLocation();
            IBakedModel model = e.getModelRegistry().get(mrl);
            if (model != null) {
                e.getModelRegistry().put(mrl, ((AdvancedBlockRender)block).newModel(block, model));
            }
        });
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre e) {
        LCCBlocks.all.stream().filter(block -> block instanceof AdvancedBlockRender).forEach(block -> {
            ((AdvancedBlockRender) block).getTextures().forEach(e::addSprite);
        });
    }

}
