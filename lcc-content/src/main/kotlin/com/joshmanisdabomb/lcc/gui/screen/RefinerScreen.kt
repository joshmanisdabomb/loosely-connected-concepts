package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.RefiningScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class RefinerScreen(handler: RefiningScreenHandler, inventory: PlayerInventory, title: Text) : RefiningScreen(handler, inventory, title) {

    override val texture = LCC.id("textures/gui/container/refiner.png")

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 172
        playerInventoryTitleX = 8
        playerInventoryTitleY = 78
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)

        renderPower(matrices, handler.powerAmount(), handler.block.maxEnergy, field_2776 + 18, field_2800 + 58)

        handler.iconIndex()?.also {
            renderArrow(matrices, handler.progressAmount(), handler.maxProgressAmount(), field_2776 + 77, field_2800 + 34)

            renderAction(matrices, it, handler.efficiencyAmount(), handler.maxEfficiencyAmount(), field_2776 + 81, field_2800 + 18)
        }
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawForeground(matrices, mouseX, mouseY)
        renderRecipe(matrices, 93f, 62f, 76)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        renderPowerTooltip(matrices, handler.powerAmount(), currentRecipe?.getEnergyPerTick(), mouseX, mouseY, field_2776 + 18..field_2776 + 29, field_2800 + 58..field_2800 + 72)

        handler.iconIndex().also {
            renderActionTooltip(matrices, handler.efficiencyAmount(), it?.let { handler.maxEfficiencyAmount() }, mouseX, mouseY, field_2776 + 81..field_2776 + 94, field_2800 + 18..field_2800 + 32)

            if (it != null) renderProgressTooltip(matrices, handler.progressAmount(), handler.maxProgressAmount(), mouseX, mouseY, field_2776 + 76..field_2776 + 100, field_2800 + 34..field_2800 + 51)
        }
    }

}