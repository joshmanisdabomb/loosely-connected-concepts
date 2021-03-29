package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.block.entity.OxygenExtractorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.OxygenExtractorScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.Direction
import java.util.*

class OxygenExtractorScreen(handler: OxygenExtractorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<OxygenExtractorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val translationKey = "container.lcc.oxygen_extractor"

    val random = Random()
    val oxygenPosition = FloatArray(18) { random.nextFloat().times(38f).minus(10f) }
    val oxygenSpeed = FloatArray(18) { random.nextFloat().times(0.15f).plus(0.85f) }

    init {
        handler.inventory.addListener(listener)
        onChanged(handler.inventory)
    }

    override fun onClose() {
        handler.inventory.removeListener(listener)
        super.onClose()
    }

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 151
        playerInventoryTitleX = 8
        playerInventoryTitleY = 57
        super.init()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
        drawTexture(matrices, x, y + 15, 0, 15, backgroundWidth, 22)

        for ((k, v) in oxygenPosition.withIndex()) {
            val stack = handler.inventory[k / 6]
            drawTexture(matrices, x + 39 + k.times(3), y + 17 + v.toInt(), 176, 14, 2, 5)
            oxygenPosition[k] += oxygenSpeed[k].div(((stack.item as? OxygenStorage)?.isFull(stack)?.not() ?: false).transformInt(4.times(handler.inventory.count { (it.item as? OxygenStorage)?.isFull(it)?.not() ?: false }.coerceAtLeast(1)), 100)).times(handler.oxygenAmount())
            if (oxygenPosition[k] >= 28f) {
                oxygenPosition[k] = -5f
                oxygenSpeed[k] = random.nextFloat().times(0.15f).plus(0.85f)
            }
        }

        drawTexture(matrices, x, y, 0, 0, backgroundWidth, 15)
        drawTexture(matrices, x, y + 37, 0, 37, backgroundWidth, backgroundHeight - 37)

        renderPower(matrices, handler.powerAmount(), LCCBlocks.oxygen_extractor.max, x + 100, y + 39)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 100..x + 111, y + 39..y + 53)

        if (mouseX in x + 39 .. x + 93 && mouseY in y + 17 .. y + 35) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.oxygen_extractor.oxygen".plus(if (Screen.hasShiftDown()) ".advanced" else ""), handler.oxygenAmount().decimalFormat(force = true), *directions.map { handler.oxygenAmount(it).oxygen.times(OxygenExtractorBlockEntity.getDirectionOxygenModifier(it)).decimalFormat(force = true) }.toTypedArray(), 100f.div(handler.oxygenModifier()).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    override fun tick() {
        super.tick()
        _ticks += 1;
    }

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.id("textures/gui/container/oxygen_extractor.png")

        private val directions = arrayOf(Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
    }

}