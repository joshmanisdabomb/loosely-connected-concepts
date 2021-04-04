package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.inventory.container.BatteryScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text


class EnergyBankScreen(handler: BatteryScreenHandler, inventory: PlayerInventory, title: Text) : BatteryScreen(handler, inventory, title) {

    override val texture = LCC.id("textures/gui/container/energy_bank.png")

    override val action_v = 14

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 150
        playerInventoryTitleX = 8
        playerInventoryTitleY = 56
        titleY = 23
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)

        renderPower(matrices, handler.powerAmount(), handler.block.max, field_2776 + 82, field_2800 + 38)

        renderPowerBar(matrices, handler.powerAmount(), handler.block.max, field_2776 + 25, field_2800 + 6, 126)

        if (inputting) renderArrow(matrices, field_2776 + 71, field_2800 + 42)
        if (outputting) renderArrow(matrices, field_2776 + 96, field_2800 + 42)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, field_2776 + 82..field_2776 + 93, field_2800 + 38..field_2800 + 52)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, field_2776 + 25..field_2776 + 151, field_2800 + 6..field_2800 + 16)
    }

}