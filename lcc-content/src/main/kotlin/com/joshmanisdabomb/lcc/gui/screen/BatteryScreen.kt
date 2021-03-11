package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.BatteryScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import kotlin.math.roundToInt
import kotlin.math.sin

abstract class BatteryScreen(handler: BatteryScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<BatteryScreenHandler>(handler, inventory, title), PowerScreenUtils {

    abstract val texture: Identifier

    val listener = ::onChanged

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val translationKey = "container.lcc.battery"

    private var input = false
    val inputting get() = input
    private var output = false
    val outputting get() = output

    init {
        handler.inventory.addListener(listener)
        onChanged(handler.inventory)
    }

    override fun onClose() {
        handler.inventory.removeListener(listener)
        super.onClose()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::method_34542)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    override fun tick() {
        super.tick()
        _ticks += 1;
    }

    fun renderArrow(matrices: MatrixStack, x: Int, y: Int) {
        drawTexture(matrices, x + sin(ticks.div(2.0)).times(1.4).roundToInt(), y, backgroundWidth, 14, 8, 8)
    }

    fun onChanged(inventory: Inventory) {
        input = handler.inventory.slotsIn("input")?.any { (it.item as? StackEnergyStorage)?.getEnergy(LooseEnergy, StackEnergyContext(it)) ?: 0f > 0f } ?: false
        output = handler.inventory.slotsIn("output")?.any { (it.item as? StackEnergyStorage)?.run { (getEnergy(LooseEnergy, StackEnergyContext(it)) ?: 0f) < (getMaximumEnergy(LooseEnergy, StackEnergyContext(it)) ?: return@any false) } ?: false } ?: false
    }

}