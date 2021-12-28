package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.TerminalBlockEntity
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.AtomicBombScreenHandler
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.inventory.container.TerminalScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
class TerminalScreen(handler: TerminalScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<TerminalScreenHandler>(handler, inventory, title), PowerScreenUtils {

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer
    override val textureWidth = 512
    override val textureHeight = 512

    override val translationKey = "container.lcc.terminal"

    val be get() = MinecraftClient.getInstance().world?.getBlockEntity(handler.pos) as? TerminalBlockEntity

    override fun init() {
        backgroundWidth = 256
        backgroundHeight = 231
        playerInventoryTitleX = 8
        playerInventoryTitleY = 137
        titleY = 7
        super.init()
    }

    override fun handledScreenTick() {
        super.handledScreenTick()
        _ticks += 1
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        val session = be?.getSession()

        fill(matrices, x + 8, y + 18, x + 248, y + 134, session?.getBackgroundColor() ?: 0xFF101010.toInt())

        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0f, 0f, backgroundWidth, backgroundHeight, 512, 512)

        session?.render(matrices, delta, x + 8, y + 18)

        RenderSystem.enableBlend()
        drawTexture(matrices, x + 8, y + 18, 256f, 14f, 240, 116, 512, 512)
        RenderSystem.disableBlend()

        renderPower(matrices, handler.powerAmount(), LooseEnergy.toStandard(100f), x + 237, y + 139)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null/* TODO */, mouseX, mouseY, x + 237..x + 248, y + 139..y + 153)
    }

    companion object {
        val texture = LCC.id("textures/gui/container/terminal.png")
    }

}