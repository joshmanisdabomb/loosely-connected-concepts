package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.screen.DungeonTableScreen
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreens : ThingDirectory<ScreenRegistry.Factory<*, *>, ScreenHandlerType<*>>() {

    val screen by create(LCCScreenHandlers.spawner_table) { ScreenRegistry.Factory(::DungeonTableScreen) }

    override fun registerAll(things: Map<String, ScreenRegistry.Factory<*, *>>, properties: Map<String, ScreenHandlerType<*>>) {
        things.forEach { (k, v) -> typedRegister(properties[k]!!, v) }
    }

    private fun <T : ScreenHandler> typedRegister(type: ScreenHandlerType<T>, v: ScreenRegistry.Factory<*, *>) {
        ScreenRegistry.register(type, v as ScreenRegistry.Factory<T, *>)
    }

}