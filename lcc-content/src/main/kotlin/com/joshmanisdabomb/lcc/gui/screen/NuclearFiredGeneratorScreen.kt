package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.NuclearFiredGeneratorScreenHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text

class NuclearFiredGeneratorScreen(handler: NuclearFiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<NuclearFiredGeneratorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    val listener = ::onChanged

    private var _ticks = 0
    override val ticks get() = _ticks

    override val translationKey = "container.lcc.generator"

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

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
        backgroundHeight = 172
        playerInventoryTitleX = 8
        playerInventoryTitleY = 78
        super.init()
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

    fun onChanged(inventory: Inventory) {

    }

    companion object {
        val texture = LCC.gui("container/nuclear_generator")
    }

}