package com.joshmanisdabomb.lcc.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.RefiningScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText


class RefinerScreen(handler: RefiningScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<RefiningScreenHandler>(handler, inventory, title) {

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

        val h = 13.times(handler.powerFill()).toInt()
        this.drawTexture(matrices, x + 18, y + 58 + (13.minus(h)), backgroundWidth, 13.minus(h), 11, h)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        if (mouseX in x + 18 .. x + 29 && mouseY in y + 58 .. y + 71) {
            renderTooltip(matrices, listOf(TranslatableText("container.lcc.refining.power", handler.powerString())), mouseX, mouseY)
        }
    }

    companion object {
        val texture = LCC.id("textures/gui/container/refiner.png")
    }

}