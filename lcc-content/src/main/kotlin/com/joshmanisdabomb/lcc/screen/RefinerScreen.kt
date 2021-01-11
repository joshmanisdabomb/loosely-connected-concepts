package com.joshmanisdabomb.lcc.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.RefinerScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class RefinerScreen(handler: RefinerScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<RefinerScreenHandler>(handler, inventory, title) {

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 172
        playerInventoryTitleX = 8
        playerInventoryTitleY = 78
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(texture)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/refiner.png")
    }

}