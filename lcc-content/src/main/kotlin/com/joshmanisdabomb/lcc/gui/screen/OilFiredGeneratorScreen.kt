package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.FiredGeneratorScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class OilFiredGeneratorScreen(handler: FiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : FiredGeneratorScreen(handler, inventory, title) {

    override val texture = LCC.id("textures/gui/container/oil_generator.png")

    override val action_v = 14

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 151
        playerInventoryTitleX = 8
        playerInventoryTitleY = 57
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)

        renderBurn(matrices, handler.burnAmount(), handler.maxBurnAmount(), x + 102, y + 30)

        renderAction(matrices, 0, handler.outputAmount(), handler.block.maxOutput, x + 122, y + 30)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        if (handler.burnAmount() > 0 || handler.outputAmount() > 0) {
            renderBurnTooltip(matrices, handler.burnAmount(), handler.maxBurnAmount(), mouseX, mouseY, x + 102..x + 116, y + 30..y + 44)

            renderSteamTooltip(matrices, handler.outputAmount(), handler.outputCeilingAmount(), handler.waterAmount(), mouseX, mouseY, x + 122..x + 136, y + 30..y + 44)
        }
    }

}