package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.*
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreenHandlers : AdvancedDirectory<Any, ScreenHandlerType<out ScreenHandler>, Unit, Unit>() {

    val spawner_table by entry(::simpleInitialiser) { ::DungeonTableScreenHandler }

    val refiner by entry(::simpleInitialiser) { ::RefinerScreenHandler }
    val composite_processor by entry(::simpleInitialiser) { ::CompositeProcessorScreenHandler }

    val coal_generator by entry(::simpleInitialiser) { ::CoalFiredGeneratorScreenHandler }
    val oil_generator by entry(::simpleInitialiser) { ::OilFiredGeneratorScreenHandler }

    val energy_bank by entry(::simpleInitialiser) { ::EnergyBankScreenHandler }

    val atomic_bomb by entry(::extendedInitialiser) { ::AtomicBombScreenHandler }

    val oxygen_extractor by entry(::simpleInitialiser) { ::OxygenExtractorScreenHandler }

    val kiln by entry(::simpleInitialiser) { ::KilnScreenHandler }
    val nuclear_generator by entry(::extendedInitialiser) { ::NuclearFiredGeneratorScreenHandler }

    val imbuing by entry(::simpleInitialiser) { ::ImbuingScreenHandler }

    override fun id(name: String) = LCC.id(name)

    fun <S : ScreenHandler> simpleInitialiser(input: (Int, PlayerInventory) -> S, context: DirectoryContext<Unit>, parameters: Unit): ScreenHandlerType<S> {
        return ScreenHandlerRegistry.registerSimple(context.id, input)
    }

    fun <S : ScreenHandler> extendedInitialiser(input: (Int, PlayerInventory, PacketByteBuf) -> S, context: DirectoryContext<Unit>, parameters: Unit): ScreenHandlerType<S> {
        return ScreenHandlerRegistry.registerExtended(context.id, input)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}