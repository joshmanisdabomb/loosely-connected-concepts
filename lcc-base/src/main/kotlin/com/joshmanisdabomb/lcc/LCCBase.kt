package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.LCCBasePacketsToClient
import net.fabricmc.api.ModInitializer

object LCCBase : ModInitializer {

    override fun onInitialize() {
        LCCBasePacketsToClient.init()
    }

}