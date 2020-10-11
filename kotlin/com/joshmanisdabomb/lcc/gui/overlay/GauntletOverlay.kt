package com.joshmanisdabomb.lcc.gui.overlay

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.toInt
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper.lerp
import kotlin.math.roundToInt

@Environment(EnvType.CLIENT)
object GauntletOverlay : DrawableHelper() {

    private val icons = LCC.gui("gauntlet")

    private val actionLastUnlocked = mutableMapOf<GauntletAction, Int>()

    const val size = 35
    const val spacing = 5

    fun render(matrix: MatrixStack, camera: PlayerEntity, ticks: Int, delta: Float) {
        MinecraftClient.getInstance().textureManager.bindTexture(icons)

        val sw = MinecraftClient.getInstance().window.scaledWidth.div(2)
        val sh = MinecraftClient.getInstance().window.scaledHeight.div(2)

        for (g in GauntletAction.values()) {
            if (!actionLastUnlocked.containsKey(g) && !g.isActing(camera)) {
                actionLastUnlocked[g] = ticks
            } else if (actionLastUnlocked.containsKey(g) && g.isActing(camera)) {
                actionLastUnlocked.remove(g)
            }
        }

        val gauntlet = camera.mainHandStack.item == LCCItems.gauntlet
        RenderSystem.enableBlend()
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, if (gauntlet) 0.82F else 0.5F)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, sw - size - spacing, sh - size - spacing, ticks, delta, false, false)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, sw + spacing, sh - size - spacing, ticks, delta, true, false)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, sw - size - spacing, sh + spacing, ticks, delta, false, true)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, sw + spacing, sh + spacing, ticks, delta, true, true)
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
        RenderSystem.disableBlend()
        MinecraftClient.getInstance().textureManager.bindTexture(GUI_ICONS_TEXTURE)
    }

    fun renderAttack(matrix: MatrixStack, camera: PlayerEntity, action: GauntletAction, x: Int, y: Int, ticks: Int, delta: Float, flipX: Boolean = false, flipY: Boolean = false) {
        val shine = ticks.minus(actionLastUnlocked[action] ?: ticks)
        val baseU = action.isActing(camera).toInt(5, if (shine <= 3) shine.plus(1) else 0)
        val baseV = 0
        this.drawTexture(matrix, x, y, if (flipY) 221 - baseU.times(size) else baseU.times(size), baseV.times(flipY.toInt(-1, 1)).plus(flipY.toInt(2)).times(size).plus(flipX.toInt(105)), size, size)
        if (action.isCasting(camera)) {
            val fill = lerp(delta.toDouble(), 1 - (action.castPercentage(camera, -1) ?: 1.0), 1 - (action.castPercentage(camera) ?: 1.0))
            this.drawTexture(matrix, x, y + (1.minus(fill).times(size).roundToInt()).times(flipY.toInt(0, 1)), if (flipY) 221 else 0, flipY.toInt(-1, 1).plus(flipY.toInt(2)).times(size).plus(flipX.toInt(105)).plus((1.minus(fill).times(size).roundToInt()).times(flipY.toInt(0, 1))), size, size.times(fill).roundToInt())
        } else if (action.isCooldown(camera)) {
            val fill = lerp(delta.toDouble(), action.cooldownPercentage(camera, -1) ?: 1.0, action.cooldownPercentage(camera) ?: 1.0)
            this.drawTexture(matrix, x, y + (1.minus(fill).times(size).roundToInt()).times(flipY.toInt(0, 1)), if (flipY) 11 else 210, 0.plus(flipY.toInt(2)).times(size).plus(flipX.toInt(105)).plus((1.minus(fill).times(size).roundToInt()).times(flipY.toInt(0, 1))), size, size.times(fill).roundToInt())
        }
    }

}