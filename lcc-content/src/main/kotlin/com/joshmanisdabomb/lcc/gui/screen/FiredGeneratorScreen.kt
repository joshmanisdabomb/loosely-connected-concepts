package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.FiredGeneratorScreenHandler
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

abstract class FiredGeneratorScreen(handler: FiredGeneratorScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<FiredGeneratorScreenHandler>(handler, inventory, title), PowerScreenUtils {

    abstract val texture: Identifier

    private var _ticks = 0
    override val ticks get() = _ticks

    override val translationKey = "container.lcc.generator"

    var currentRecipe: RefiningRecipe? = null

    override val offsetX get() = backgroundWidth
    override val offsetY get() = backgroundHeight
    override val textRenderer get() = textRenderer

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(texture)
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

}