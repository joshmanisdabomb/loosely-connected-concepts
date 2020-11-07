package com.joshmanisdabomb.lcc

import com.google.common.base.CaseFormat
import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object LCC : ModInitializer, ClientModInitializer {

    const val modid = "lcc"

    override fun onInitialize() {
        LCCBlocks.init()
        LCCItems.init()
        LCCBlockItems.init()
        LCCCommands.init()
        LCCEvents.init()
        LCCPackets.init()
        LCCDamage.init()
        LCCBiomes.init()
    }

    override fun onInitializeClient() {
        LCCGroups.init()
    }

    fun id(path: String): Identifier = Identifier(modid, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, path))

    fun gui(name: String): Identifier = id("textures/gui/$name.png")

}