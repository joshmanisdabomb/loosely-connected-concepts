package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.*
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreenHandlers : ThingDirectory<ScreenHandlerType<out ScreenHandler>, Unit>() {

    val spawner_table by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::DungeonTableScreenHandler) }

    val refiner by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::RefinerScreenHandler) }
    val composite_processor by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::CompositeProcessorScreenHandler) }

    val coal_generator by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::CoalFiredGeneratorScreenHandler) }
    val oil_generator by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::OilFiredGeneratorScreenHandler) }

    val energy_bank by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::EnergyBankScreenHandler) }

}