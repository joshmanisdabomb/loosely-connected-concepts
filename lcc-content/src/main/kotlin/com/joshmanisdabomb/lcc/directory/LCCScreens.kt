package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.gui.screen.*
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text

object LCCScreens : AdvancedDirectory<Any, ScreenRegistry.Factory<out ScreenHandler, *>, ScreenHandlerType<out ScreenHandler>, Unit>() {

    val spawner_table by entry(::initialiser) { ::DungeonTableScreen }

    val refiner by entry(::initialiser) { ::RefinerScreen }
    val composite_processor by entry(::initialiser) {::CompositeProcessorScreen }

    val coal_generator by entry(::initialiser) { ::CoalFiredGeneratorScreen }
    val oil_generator by entry(::initialiser) { ::OilFiredGeneratorScreen }

    val energy_bank by entry(::initialiser) { ::EnergyBankScreen }

    val atomic_bomb by entry(::initialiser) { ::AtomicBombScreen }

    val oxygen_extractor by entry(::initialiser) { ::OxygenExtractorScreen }

    val kiln by entry(::initialiser) { ::KilnScreen }
    val nuclear_generator by entry(::initialiser) { ::NuclearFiredGeneratorScreen }

    val computing by entry(::initialiser) { { h: ComputingScreenHandler, pi: PlayerInventory, text: Text -> (MinecraftClient.getInstance().world?.getBlockEntity(h.pos) as? ComputingBlockEntity)?.getHalf(h.top)?.module?.createScreen(h, pi, text)!! } }
    val terminal by entry(::initialiser) { ::TerminalScreen }

    val imbuing by entry(::initialiser) { ::ImbuingScreen }

    fun <S, H : ScreenHandler> initialiser(input: (H, PlayerInventory, Text) -> S, context: DirectoryContext<ScreenHandlerType<out ScreenHandler>>, parameters: Unit) where S : Screen, S : ScreenHandlerProvider<H> = ScreenRegistry.Factory(input).also { ScreenRegistry.register(context.properties as ScreenHandlerType<H>, input) }

    override fun defaultProperties(name: String) = LCCScreenHandlers[name]
    override fun defaultContext() = Unit

}