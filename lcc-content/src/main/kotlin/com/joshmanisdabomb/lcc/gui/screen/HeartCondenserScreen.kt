package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.HeartCondenserScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class HeartCondenserScreen(handler: HeartCondenserScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<HeartCondenserScreenHandler>(handler, inventory, title) {

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 198
        playerInventoryTitleX = 8
        playerInventoryTitleY = 104
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE)
        for (i in 0..9) {
            val space = 1.0//sin(System.currentTimeMillis().div(1000.0)).plus(1).div(2.0).times(1.7)
            val hx = 83 - i.minus(5).plus(0.5).times(10).times(space).toInt()
            drawTexture(matrices, x + hx, y + 64, 16, 0, 9, 9)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/heart_condenser.png")
        val hearts = LCC.id("textures/gui/container/heart_condenser.png")
    }

}