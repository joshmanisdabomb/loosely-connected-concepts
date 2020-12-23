package com.joshmanisdabomb.lcc.gui.overlay

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction.Companion.ability
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.gui.screen.GauntletScreen
import com.joshmanisdabomb.lcc.gui.utils.GauntletProgressRenderer
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3f
import net.minecraft.entity.player.PlayerEntity

@Environment(EnvType.CLIENT)
object GauntletOverlay : DrawableHelper(), GauntletProgressRenderer {

    private val icons = LCC.gui("gauntlet")

    override val size = 31
    const val spacing = 2
    override val loc = -size-spacing

    private val sw get() = MinecraftClient.getInstance().window.scaledWidth
    private val sh get() = MinecraftClient.getInstance().window.scaledHeight

    private val actionLastUnlocked = mutableMapOf<GauntletAction, Int>()

    fun render(matrix: MatrixStack, camera: PlayerEntity, ticks: Int, delta: Float) {
        val client = MinecraftClient.getInstance()
        if (client.currentScreen is GauntletScreen) return

        client.textureManager.bindTexture(icons)

        for (g in GauntletAction.values()) {
            if (!actionLastUnlocked.containsKey(g) && !g.isActing(camera)) {
                actionLastUnlocked[g] = ticks
            } else if (actionLastUnlocked.containsKey(g) && g.isActing(camera)) {
                actionLastUnlocked.remove(g)
            }
        }

        val gauntlet = camera.mainHandStack.item == LCCItems.gauntlet || camera.offHandStack.item == LCCItems.gauntlet
        val current = camera.mainHandStack?.orCreateTag?.ability ?: camera.offHandStack?.orCreateTag?.ability
        RenderSystem.enableBlend()
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, if (gauntlet) 0.82F else 0.43F)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 0f)
        if (gauntlet || GauntletAction.PUNCH.isActing(camera)) renderAttack(matrix, camera, GauntletAction.PUNCH, current, ticks, delta, 90f)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 180f)
        if (gauntlet || GauntletAction.UPPERCUT.isActing(camera)) renderAttack(matrix, camera, GauntletAction.UPPERCUT, current, ticks, delta, 270f)
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)
        RenderSystem.disableBlend()
        client.textureManager.bindTexture(GUI_ICONS_TEXTURE)
    }

    fun renderAttack(matrix: MatrixStack, camera: PlayerEntity, action: GauntletAction, current: GauntletAction?, ticks: Int, delta: Float, angle: Float) {
        val shine = ticks.minus(actionLastUnlocked[action] ?: ticks)
        val selected = (current == action).toInt(2)
        val charging = current == action && action.isChargeable() && camera.activeItem.item == LCCItems.gauntlet

        val baseU = charging.toInt(2, action.isActing(camera).toInt(action.isCasting(camera).toInt(0, 5), if (shine <= 3) shine.plus(1) else 0))
        val baseV = (charging || action.isCasting(camera)).toInt(1) + selected

        matrix.push()
        matrix.translate(sw.div(2).toDouble(), 0.0, 0.0)
        matrix.translate(0.0, sh.div(2).toDouble(), 0.0)
        matrix.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(angle))

        this.drawTexture(matrix, loc, loc, baseU.times(size), baseV.times(size), size, size)
        if (action.isCasting(camera)) {
            renderProgress(matrix, camera, action::castPercentage, true, 1, 1.plus(selected), angle, delta)
        } else if (action.isCooldown(camera)) {
            renderProgress(matrix, camera, action::cooldownPercentage, false, 6, selected, angle, delta)
        } else if (charging) {
            renderProgress(matrix, camera, { player, offset -> action.chargePercentage(player, offset = offset) }, false, 3, 1.plus(selected), angle, delta, 1)
            if (camera.itemUseTime > action.chargeBiteTime && ticks % 2 == 0) {
                this.drawTexture(matrix, loc, loc, 4.times(size), 1.plus(selected).times(size), size, size)
            }
        }

        matrix.pop()
    }

}