package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.gui.screen.CompositeProcessorScreen
import com.joshmanisdabomb.lcc.gui.screen.DungeonTableScreen
import com.joshmanisdabomb.lcc.gui.screen.RefinerScreen
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreens : ThingDirectory<ScreenRegistry.Factory<*, *>, ScreenHandlerType<*>>() {

    val spawner_table by create(LCCScreenHandlers.spawner_table) { ScreenRegistry.Factory(::DungeonTableScreen) }
    val refiner by create(LCCScreenHandlers.refiner) { ScreenRegistry.Factory(::RefinerScreen) }
    val composite_processor by create(LCCScreenHandlers.composite_processor) { ScreenRegistry.Factory(::CompositeProcessorScreen) }

    override fun registerAll(things: Map<String, ScreenRegistry.Factory<*, *>>, properties: Map<String, ScreenHandlerType<*>>) {
        things.forEach { (k, v) -> typedRegister(properties[k]!!, v) }
    }

    private fun <T : ScreenHandler> typedRegister(type: ScreenHandlerType<T>, v: ScreenRegistry.Factory<*, *>) {
        ScreenRegistry.register(type, v as ScreenRegistry.Factory<T, *>)
    }

}