package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.RefiningScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class CompositeProcessorScreen(handler: RefiningScreenHandler, inventory: PlayerInventory, title: Text) : RefiningScreen(handler, inventory, title) {

    override val texture = LCC.id("textures/gui/container/composite_processor.png")

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 190
        playerInventoryTitleX = 8
        playerInventoryTitleY = 96
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)

        renderPower(matrices, handler.powerAmount(), handler.block.maxEnergy, x + 18, y + 76)

        handler.iconIndex()?.also {
            renderArrow(matrices, handler.progressAmount(), handler.maxProgressAmount(), x + 77, y + 43)

            renderAction(matrices, it, handler.efficiencyAmount(), handler.maxEfficiencyAmount(), x + 81, y + 27)
        }
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawForeground(matrices, mouseX, mouseY)
        renderRecipe(matrices, 93f, 80f, 76)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        renderPowerTooltip(matrices, handler.powerAmount(), currentRecipe?.energy, mouseX, mouseY, x + 18..x + 29, y + 76..y + 90)

        handler.iconIndex().also {
            renderActionTooltip(matrices, handler.efficiencyAmount(), it?.let { handler.maxEfficiencyAmount() }, mouseX, mouseY, x + 81..x + 94, y + 27..y + 41)

            if (it != null) renderProgressTooltip(matrices, handler.progressAmount(), handler.maxProgressAmount(), mouseX, mouseY, x + 76..x + 100, y + 43..y + 60)
        }
    }

}