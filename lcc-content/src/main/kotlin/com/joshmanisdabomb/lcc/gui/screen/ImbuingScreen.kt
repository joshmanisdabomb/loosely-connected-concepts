package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.ImbuingScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.ForgingScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class ImbuingScreen(handler: ImbuingScreenHandler, inventory: PlayerInventory, title: Text) : ForgingScreen<ImbuingScreenHandler>(handler, inventory, title, texture) {

    init {
        titleX = 60
        titleY = 18
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        RenderSystem.disableBlend()
        super.drawForeground(matrices, mouseX, mouseY)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/imbuing_press.png")
    }

}