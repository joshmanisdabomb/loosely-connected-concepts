package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.gui.screen.*
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreens : ThingDirectory<ScreenRegistry.Factory<*, *>, ScreenHandlerType<*>>() {

    val spawner_table by create(LCCScreenHandlers.spawner_table) { ScreenRegistry.Factory(::DungeonTableScreen) }

    val refiner by create(LCCScreenHandlers.refiner) { ScreenRegistry.Factory(::RefinerScreen) }
    val composite_processor by create(LCCScreenHandlers.composite_processor) { ScreenRegistry.Factory(::CompositeProcessorScreen) }

    val coal_generator by create(LCCScreenHandlers.coal_generator) { ScreenRegistry.Factory(::CoalFiredGeneratorScreen) }
    val oil_generator by create(LCCScreenHandlers.oil_generator) { ScreenRegistry.Factory(::OilFiredGeneratorScreen) }

    override fun registerAll(things: Map<String, ScreenRegistry.Factory<*, *>>, properties: Map<String, ScreenHandlerType<*>>) {
        things.forEach { (k, v) -> typedRegister(properties[k]!!, v) }
    }

    private fun <T : ScreenHandler> typedRegister(type: ScreenHandlerType<T>, v: ScreenRegistry.Factory<*, *>) {
        ScreenRegistry.register(type, v as ScreenRegistry.Factory<T, *>)
    }

}