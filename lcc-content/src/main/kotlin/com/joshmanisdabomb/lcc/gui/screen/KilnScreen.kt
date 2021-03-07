package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.inventory.container.KilnScreenHandler
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class KilnScreen(container: KilnScreenHandler, inventory: PlayerInventory, title: Text) : AbstractFurnaceScreen<KilnScreenHandler>(container, FurnaceRecipeBookScreen(), inventory, title, Identifier("textures/gui/container/furnace.png"))