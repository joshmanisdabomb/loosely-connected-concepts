package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.AtomicBombScreenHandler
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class DriveScreen(handler: ComputingScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<ComputingScreenHandler>(handler, inventory, title) {

    val texture = Identifier(handler.half.module.id.namespace, "textures/gui/container/${handler.half.module.id.path}.png")

    override fun init() {
        super.init()
        backgroundWidth = 176
        backgroundHeight = 140
        playerInventoryTitleX = 8
        playerInventoryTitleY = 46
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

}