package com.joshmanisdabomb.lcc.screen

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.inventory.container.RefiningScreenHandler
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import kotlin.math.ceil


class RefinerScreen(handler: RefiningScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<RefiningScreenHandler>(handler, inventory, title) {

    val listener = ::onChanged

    private var ticks = 0

    var currentRecipe: RefiningRecipe? = null

    override fun init() {
        backgroundWidth = 176
        backgroundHeight = 172
        playerInventoryTitleX = 8
        playerInventoryTitleY = 78
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

        val h = 13.times(handler.powerAmount().div(handler.block.maxEnergy)).toInt()
        this.drawTexture(matrices, x + 18, y + 58 + (13.minus(h)), backgroundWidth, 13.minus(h), 11, h)

        handler.iconIndex()?.also {
            val w = ceil(23.times(handler.progressAmount().toFloat().div(handler.maxProgressAmount()))).toInt()
            this.drawTexture(matrices, x + 77, y + 34, backgroundWidth.plus(1), 14, w, 17)

            this.drawTexture(matrices, x + 81, y + 18, backgroundWidth, 31 + it.times(14), 14, 14)

            val eff = handler.efficiencyAmount().div(handler.maxEfficiencyAmount())
            if (eff >= 1f && ticks % 8 >= 4) {
                this.drawTexture(matrices, x + 81, y + 18, backgroundWidth.plus(28), 31 + it.times(14), 14, 14)
            } else {
                val h2 = ceil(13.times(eff)).toInt()
                this.drawTexture(matrices, x + 81, y + 18 + (13.minus(h2)), backgroundWidth.plus(14), 31 + it.times(14) + 13.minus(h2), 14, h2)
            }
        }
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        super.drawForeground(matrices, mouseX, mouseY)
        currentRecipe?.also {
            val lines = textRenderer.wrapLines(TranslatableText(it.lang), 76)
            lines.forEachIndexed { k, v ->
                textRenderer.draw(matrices, v, 93f, 62f.minus((lines.size > 1).toInt(6)).plus(k.times(10)), 4210752)
            }
        }
    }

    fun onChanged(inventory: Inventory) {
        currentRecipe = handler.currentRecipe?.orElse(null)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)

        currentRecipe.also {
            val prefix = if (it != null) ".recipe" else ""
            if (mouseX in x + 18..x + 29 && mouseY in y + 58..y + 72) {
                renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.power".plus(prefix), LooseEnergy.displayWithUnits(LooseEnergy.fromStandard(handler.powerAmount())), LooseEnergy.displayWithUnits(it?.energy ?: 0f)), Int.MAX_VALUE), mouseX, mouseY)
            }
        }

        handler.iconIndex().also {
            val prefix = if (it != null) ".recipe" else ""
            if (mouseX in x + 81..x + 94 && mouseY in y + 18..y + 32) {
                renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.efficiency".plus(prefix), handler.efficiencyAmount().plus(100).decimalFormat(force = true), handler.maxEfficiencyAmount().plus(100).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
            }

            if (mouseX in x + 76..x + 100 && mouseY in y + 34..y + 51 && it != null) {
                renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.time", handler.progressAmount().div(20), handler.maxProgressAmount().div(20)), Int.MAX_VALUE), mouseX, mouseY)
            }
        }
    }

    override fun tick() {
        super.tick()
        ticks += 1;
    }

    companion object {
        val texture = LCC.id("textures/gui/container/refiner.png")
    }

}