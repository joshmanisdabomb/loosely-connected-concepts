package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ClientModInitializer

object LCCClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCGroups.init()
        LCCModels.init()
        LCCFluids.initRenderers()
        LCCPacketsToClient.init()
        LCCParticlesClient.init()
    }

}