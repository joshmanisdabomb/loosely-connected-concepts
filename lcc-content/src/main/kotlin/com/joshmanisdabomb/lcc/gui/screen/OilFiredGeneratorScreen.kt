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

        renderBurn(matrices, handler.burnAmount(), handler.maxBurnAmount(), field_2776 + 102, field_2800 + 30)

        renderAction(matrices, 0, handler.outputAmount(), handler.block.maxOutput, field_2776 + 122, field_2800 + 30)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        if (handler.burnAmount() > 0 || handler.outputAmount() > 0) {
            renderBurnTooltip(matrices, handler.burnAmount(), handler.maxBurnAmount(), mouseX, mouseY, field_2776 + 102..field_2776 + 116, field_2800 + 30..field_2800 + 44)

            renderSteamTooltip(matrices, handler.outputAmount(), handler.outputCeilingAmount(), handler.waterAmount(), mouseX, mouseY, field_2776 + 122..field_2776 + 136, field_2800 + 30..field_2800 + 44)
        }
    }

}