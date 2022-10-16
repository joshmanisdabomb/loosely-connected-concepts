package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.gui.overlay.HeartsOverlay
import com.joshmanisdabomb.lcc.inventory.container.HeartCondenserScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text
import kotlin.math.ceil

@Environment(EnvType.CLIENT)
class HeartCondenserScreen(handler: HeartCondenserScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<HeartCondenserScreenHandler>(handler, inventory, title) {

    val listener = ::onChanged

    private var _ticks = 0

    init {
        handler.inventory.addListener(listener)
        onChanged(handler.inventory)
    }

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

        if (handler.burnAmount() > 0) {
            val amount = ceil(handler.burnAmount().div(handler.burnMaxAmount().toFloat()).times(13)).toInt()
            drawTexture(matrices, x + 61, y + 22 + 13.minus(amount), 188, 13.minus(amount), 10, amount)
        }

        if (handler.cookAmount() > 0) {
            val amount = ceil(1.minus(handler.cookAmount().div(handler.cookMaxAmount().toFloat())).times(19)).toInt()
            drawTexture(matrices, x + 82, y + 37, 176, 0, 12, 19.minus(amount))
        }

        val state = MinecraftClient.getInstance().world?.getBlockState(handler.pos)
        val type = (if (state?.isOf(LCCBlocks.heart_condenser) == true) state.get(HeartCondenserBlock.type).type else null) ?: HeartType.RED
        RenderSystem.setShaderTexture(0, if (type.v < 0) DrawableHelper.GUI_ICONS_TEXTURE else HeartsOverlay.icons)
        val health = handler.healthAmount()
        for (i in 0..9) {
            val space = 1.0//sin(System.currentTimeMillis().div(1000.0)).plus(1).div(2.0).times(1.7)
            val hx = 83 + i.minus(5).plus(0.5).times(10).times(space).toInt()
            drawTexture(matrices, x + hx, y + 64, if (type.v < 0) 16 else 0, type.v.coerceAtLeast(0), 9, 9)
            if (health >= i.plus(1).times(2)) {
                drawTexture(matrices, x + hx, y + 64, if (type.v < 0) 52 else 72, type.v.coerceAtLeast(0), 9, 9)
            } else if (health == i.times(2).plus(1)) {
                drawTexture(matrices, x + hx, y + 64, if (type.v < 0) 61 else 81, type.v.coerceAtLeast(0), 9, 9)
            }
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    override fun handledScreenTick() {
        super.handledScreenTick()
        _ticks += 1
    }

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.id("textures/gui/container/heart_condenser.png")
        val hearts = LCC.id("textures/gui/container/heart_condenser.png")
    }

}