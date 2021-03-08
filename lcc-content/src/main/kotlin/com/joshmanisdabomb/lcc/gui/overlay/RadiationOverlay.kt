package com.joshmanisdabomb.lcc.gui.overlay

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.component.RadiationComponent
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.to
import com.joshmanisdabomb.lcc.extensions.toInt
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.math.sin

@Environment(EnvType.CLIENT)
object RadiationOverlay : DrawableHelper() {

    private val random = Random()
    private const val pi = Math.PI.toFloat()

    private val icons = LCC.gui("radiation")

    private var lastTick: Int = 0
    private var lastEffectDuration: Int = 0
    private var effectDirection: Float = 0f
    private val effectIncreasing get() = effectDirection > 0

    private val restorationColor = Triple(0.9f, 0.6f, 0.8f)
    private val increasingColor = Triple(1.0f, 0.7f, 0.4f)
    private val receivingColor = Triple(0.6f, 0.9f, 0.4f)

    fun render(matrix: MatrixStack, player: PlayerEntity, armorPosition: Int, ticks: Int) {
        val component = LCCComponents.radiation.maybeGet(player)
        val exposure = component.map {it.exposure}.orElse(0f)
        val effect = player.getStatusEffect(LCCEffects.radiation)
        if (effect == null && exposure <= 0f) return

        MinecraftClient.getInstance().textureManager.bindTexture(icons)
        val ticksf = ticks.plus(if (!MinecraftClient.getInstance().isPaused) MinecraftClient.getInstance().tickDelta else 0f)
        val sw = MinecraftClient.getInstance().window.scaledWidth
        val y = MinecraftClient.getInstance().window.scaledHeight - 46
        val x = sw.div(2).minus(113)

        val d = effect?.duration ?: 0
        if (ticks > lastTick) {
            val sign = d.minus(lastEffectDuration).sign
            effectDirection = effectDirection.times(0.9f) + sign.times(0.1f)
            lastEffectDuration = d
            lastTick = ticks
        }

        val waveSpeed = 30f.div((effect?.amplifier?.plus(1) ?: 1).times(effectIncreasing.to(1.5f, 1f)).coerceAtMost(6f))
        RenderSystem.enableBlend()
        if (effectIncreasing) {
            renderWave(matrix, x, y, effect, ticks, 0, waveSpeed)
            renderWave(matrix, x, y, effect, ticks, 1, waveSpeed)
        }
        renderWave(matrix, x, y, effect, ticks, 2, waveSpeed)
        renderWave(matrix, x, y, effect, ticks, 3, waveSpeed)
        renderWave(matrix, x, y, effect, ticks, 4, waveSpeed)
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        RenderSystem.disableBlend()

        val shakeX: Int
        val shakeY: Int
        if (effect != null) {
            random.setSeed(ticksf.div(3f).times(effect.amplifier + 1).times(effectIncreasing.toInt(2, 1)).toInt() * 93564L)
            shakeX = random.nextInt(2).times(random.nextInt(3).minus(1))
            shakeY = random.nextInt(2).times(random.nextInt(3).minus(1))
        } else {
            shakeX = 0
            shakeY = 0
        }

        component.map(RadiationComponent::exposureLimit).orElse(18f)?.also {
            if (exposure >= it) {
                renderHeart(matrix, x, -shakeX, y, -shakeY, exposure, ticks)
                renderHeart(matrix, x, shakeX, y-2, shakeY, exposure, ticks)
            } else {
                renderHeart(matrix, x, shakeX, y, shakeY, exposure, ticks)
            }
        }
        MinecraftClient.getInstance().textureManager.bindTexture(GUI_ICONS_TEXTURE)
    }

    private fun renderHeart(matrix: MatrixStack, x: Int, shakeX: Int, y: Int, shakeY: Int, exposure: Float, ticks: Int) {
        this.drawTexture(matrix, x + shakeX, y + shakeY, 0, 23, 23, 23)
        val noise = exposure.div(2f).rem(1f)
        if (noise > 0f) {
            RenderSystem.enableBlend()
            random.setSeed(ticks.div(2) * 438125L)
            for (j in 0 until 7) {
                for (i in 0 until 7) {
                    if (j == 0 && i !in 1..2 && i !in 4..5) continue
                    if (j == 1 && (i == 1)) continue
                    if (j == 4 && i !in 1..5) continue
                    if (j == 5 && i !in 2..4) continue
                    if (j == 6 && i != 3) continue
                    val b = random.nextFloat()
                    RenderSystem.color4f(b, b, b, b.times(2).minus(1).absoluteValue.times(noise).times(0.5f).plus(noise.times(0.5f)))
                    this.drawTexture(matrix, x + 8 + i + shakeX, y + 8 + j + shakeY, 31 + i, 31 + j, 1, 1)
                }
            }
            RenderSystem.disableBlend()
        }
        RenderSystem.color4f(1f, 1f, 1f, 1f)
    }

    private fun renderWave(matrix: MatrixStack, x: Int, y: Int, effect: StatusEffectInstance?, ticks: Int, wave: Int, waveSpeed: Float) {
        val f = ticks.div(waveSpeed.absoluteValue).plus(wave.div(5f).times((effect == null).toInt(1, -1)))
        val color = when {
            effect == null -> restorationColor
            effectIncreasing -> increasingColor
            else -> receivingColor
        }
        if (f.rem(2f).toInt() == 0) {
            RenderSystem.color4f(color.first, color.second, color.third, sin(f.times(pi)).let { it*it*it }.absoluteValue)
            this.drawTexture(matrix, x, y, wave.times(23), 0, 23, 23)
        }
    }

}