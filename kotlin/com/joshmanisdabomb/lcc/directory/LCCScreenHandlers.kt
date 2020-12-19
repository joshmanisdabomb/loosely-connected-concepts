package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.DungeonTableScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

object LCCScreenHandlers : ThingDirectory<ScreenHandlerType<out ScreenHandler>, Unit>() {

    val spawner_table by createWithName { ScreenHandlerRegistry.registerSimple(LCC.id(it), ::DungeonTableScreenHandler) }

}