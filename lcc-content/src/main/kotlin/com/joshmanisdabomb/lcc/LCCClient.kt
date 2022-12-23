package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.item.render.ConsumerMawItemRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.texture.SpriteAtlasTexture

object LCCClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCRenderLayers.init()
        LCCModelLayers.init()
        LCCBlocks.initClient()
        LCCItems.initClient()
        LCCBlockItems.initClient()
        LCCModels.init()
        LCCEntities.initRenderers()
        LCCBlockEntities.initRenderers()
        LCCFluids.initRenderers()
        LCCScreens.init()
        LCCPacketsToClient.init()
        LCCParticlesClient.init()
        LCCEventsClient.init()

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE).register(LCCSignTypes)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(ConsumerMawItemRenderer)
    }

}