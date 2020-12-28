package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ClientModInitializer

object LCCClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCModelLayers.init()
        LCCBlocks.initClient()
        LCCItems.initClient()
        LCCBlockItems.initClient()
        LCCGroups.init()
        LCCModels.init()
        LCCEntities.initRenderers()
        LCCBlockEntities.initRenderers()
        LCCFluids.initRenderers()
        LCCScreens.init()
        LCCPacketsToClient.init()
        LCCParticlesClient.init()
    }

}