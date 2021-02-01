package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.LCCBasePacketsToClient
import net.fabricmc.api.ClientModInitializer

object LCCBaseClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCBasePacketsToClient.register()
    }

}