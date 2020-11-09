package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.LCCFluids
import com.joshmanisdabomb.lcc.directory.LCCGroups
import com.joshmanisdabomb.lcc.directory.LCCPacketsClient
import net.fabricmc.api.ClientModInitializer

object LCCClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCGroups.init()
        LCCFluids.initRenderers()
        LCCPacketsClient.init()
    }

}