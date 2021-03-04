package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.OxygenExtractorScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text

class OxygenExtractorScreen(handler: OxygenExtractorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<OxygenExtractorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged

    private var _ticks = 0
    override val ticks get() = _ticks

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override val translationKey = "container.lcc.oxygen_extractor"

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
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(texture)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderPower(matrices, handler.powerAmount(), LCCBlocks.oxygen_extractor.max, x + 100, y + 39)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        renderPowerTooltip(matrices, handler.powerAmount(), null, mouseX, mouseY, x + 100..x + 111, y + 39..y + 53)
    }

    override fun tick() {
        super.tick()
        _ticks += 1;
    }

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.id("textures/gui/container/oxygen_extractor.png")
    }

}