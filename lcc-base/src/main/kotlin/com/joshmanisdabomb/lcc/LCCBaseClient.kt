package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.LCCBasePacketsForClient
import net.fabricmc.api.ClientModInitializer

object LCCBaseClient : ClientModInitializer {

    override fun onInitializeClient() {
        LCCBasePacketsForClient.init()
    }

}