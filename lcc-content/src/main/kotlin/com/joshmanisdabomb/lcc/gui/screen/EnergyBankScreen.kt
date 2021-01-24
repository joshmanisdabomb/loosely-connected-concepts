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

        renderPower(matrices, handler.powerAmount(), handler.block.max, x + 82, y + 38)

        renderPowerBar(matrices, handler.powerAmount(), handler.block.max, x + 25, y + 6, 126)

        if (inputting) renderArrow(matrices, x + 71, y + 42)
        if (outputting) renderArrow(matrices, x + 96, y + 42)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(matrices, mouseX, mouseY, delta)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 82..x + 93, y + 38..y + 52)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 25..x + 151, y + 6..y + 16)
    }

}