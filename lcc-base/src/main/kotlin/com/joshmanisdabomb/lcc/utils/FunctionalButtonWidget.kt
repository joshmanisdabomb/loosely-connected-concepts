package com.joshmanisdabomb.lcc.utils

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

open class FunctionalButtonWidget(x: Int, y: Int, width: Int, height: Int, private val iconWidth: Int, private val iconHeight: Int, message: Text, protected val tooltip: (matrices: MatrixStack, x: Int, y: Int) -> Unit, protected val pressed: () -> Int?) : AbstractPressableButtonWidget(x, y, width, height, message) {

    var texture: Identifier? = null
    var disabled = false

    var ix = 0
    var sx = 0
    var sy = 0

    override fun renderButton(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        texture?.also { MinecraftClient.getInstance().textureManager.bindTexture(it) }
        var j = 0
        if (!active) {
            j += width * 2
        } else if (disabled) {
            j += width * 1
        } else if (isHovered) {
            j += width * 3
        }

        onRenderButton(matrices)
        drawTexture(matrices, x, y, sx + j, sy, width, height)
        RenderSystem.enableBlend()
        onRenderIcon(matrices)
        drawTexture(matrices, x, y, sx + ix, sy, iconWidth, iconHeight)
        RenderSystem.disableBlend()
    }

    override fun onPress() {
        if (disabled) return
        pressed()?.run { ix = this }
    }

    override fun renderToolTip(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        tooltip(matrices, mouseX, mouseY)
    }

    protected open fun onRenderButton(matrices: MatrixStack) = RenderSystem.color4f(1f, 1f, 1f, 1f)
    protected open fun onRenderIcon(matrices: MatrixStack) = RenderSystem.color4f(1f, 1f, 1f, 1f)

}