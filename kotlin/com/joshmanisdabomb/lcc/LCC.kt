package com.joshmanisdabomb.lcc

import com.joshmanisdabomb.lcc.directory.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object LCC : ModInitializer, ClientModInitializer {

    val modid = "lcc"

    override fun onInitialize() {
        LCCItems.init()
        LCCCommands.init()
        LCCEvents.init()
    }

    override fun onInitializeClient() {
        LCCGroups.init()
    }

    fun id(path: String): Identifier = Identifier(modid, path)

    fun gui(name: String): Identifier = id("textures/gui/$name.png")

}