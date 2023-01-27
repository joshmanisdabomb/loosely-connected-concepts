package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.render.TexturedRenderLayers

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
        LCCSkyRenderers.init()
        LCCDimensionEffects.init()

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE).register(LCCSignTypes)
    }

}