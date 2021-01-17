package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.FiredGeneratorScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class CoalFiredGeneratorScreen(handler: FiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : FiredGeneratorScreen(handler, inventory, title) {

    override val texture = LCC.id("textures/gui/container/coal_generator.png")

    override val actionU = 14

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 133
        playerInventoryTitleX = 8
        playerInventoryTitleY = 39
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)

        renderBurn(matrices, handler.burnAmount(), handler.maxBurnAmount(), x + 102, y + 21)

        renderAction(matrices, 0, handler.outputAmount(), handler.block.maxOutput, x + 122, y + 21)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        /*renderPowerTooltip(matrices, handler.powerAmount(), currentRecipe?.energy, mouseX, mouseY, x + 18..x + 29, y + 58..y + 72)

        handler.iconIndex().also {
            renderActionTooltip(matrices, handler.efficiencyAmount(), it?.let { handler.maxEfficiencyAmount() }, mouseX, mouseY, x + 81..x + 94, y + 18..y + 32)

            if (it != null) renderProgressTooltip(matrices, handler.progressAmount(), handler.maxProgressAmount(), mouseX, mouseY, x + 76..x + 100, y + 34..y + 51)
        }*/
    }

}