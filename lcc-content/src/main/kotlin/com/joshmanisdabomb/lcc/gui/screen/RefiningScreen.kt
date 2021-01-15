package com.joshmanisdabomb.lcc.gui.screen

import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.gui.utils.PowerScreenUtils
import com.joshmanisdabomb.lcc.inventory.container.RefiningScreenHandler
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier

abstract class RefiningScreen(handler: RefiningScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<RefiningScreenHandler>(handler, inventory, title), PowerScreenUtils {

    abstract val texture: Identifier

    val listener = ::onChanged

    private var _ticks = 0
    override val ticks get() = _ticks

    var currentRecipe: RefiningRecipe? = null

    override val offset get() = backgroundWidth
    override val textRenderer get() = textRenderer

    override fun init() {
        super.init()

        handler.inventory.addListener(listener)
        onChanged(handler.inventory)
    }

    override fun onClose() {
        handler.inventory.removeListener(listener)
        super.onClose()
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(texture)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawForeground(matrices, mouseX, mouseY)
    }

    fun onChanged(inventory: Inventory) {
        currentRecipe = handler.currentRecipe?.orElse(null)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    fun renderRecipe(matrices: MatrixStack, x: Float, y: Float, w: Int) {
        currentRecipe?.also {
            val lines = textRenderer.wrapLines(TranslatableText(it.lang), w)
            lines.forEachIndexed { k, v ->
                textRenderer.draw(matrices, v, x, y.minus((lines.size > 1).toInt(6)).plus(k.times(10)), 4210752)
            }
        }
    }

    override fun tick() {
        super.tick()
        _ticks += 1;
    }

}