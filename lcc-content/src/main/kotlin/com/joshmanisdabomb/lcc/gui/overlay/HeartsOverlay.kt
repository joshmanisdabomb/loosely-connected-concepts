package com.joshmanisdabomb.lcc.gui.overlay

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.toInt
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Util
import net.minecraft.util.math.MathHelper.ceil
import java.util.*
import kotlin.math.max

@Environment(EnvType.CLIENT)
object HeartsOverlay : DrawableHelper() {

    private val random = Random()

    private val icons = LCC.gui("hearts")
    private val types = HeartType.values().filter { it.drawable }

    fun render(matrix: MatrixStack, player: PlayerEntity, armorPosition: Int, ticks: Int) {
        RenderSystem.setShaderTexture(0, icons)
        var start = armorPosition + 9 + types.filter { it.getMaxHealth(player) > 0 }.count()
        for (type in types.filter { it.getMaxHealth(player) > 0 }.sortedBy { it.sortOrder }) {
            renderHealthBar(matrix, player, type, start, ticks)
            start += getTypeSpace(ceil(type.getMaxHealth(player)))
        }
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE)
    }

    fun getHeartRows(maxHealth: Int) = ceil(maxHealth.div(20.0))

    fun getHeartSpacing(rows: Int) = max(11 - rows, 3)

    fun getHalfHeartsInRow(maxHealth: Int, row: Int) = (maxHealth - (row*20)).coerceIn(0, 20)

    fun getTypeSpace(maxHealth: Int) = getHeartRows(maxHealth).let { it.times(getHeartSpacing(it)) }

    fun getTotalSpace(player: PlayerEntity): Int = types.map { getTypeSpace(ceil(it.getMaxHealth(player))) }.let { it.sum().plus(
        it.filter { it > 0 }.count() - 1
    ) }.coerceAtLeast(0)

    fun renderHealthBar(matrix: MatrixStack, player: PlayerEntity, type: HeartType, top: Int, ticks: Int) {
        val sw = MinecraftClient.getInstance().window.scaledWidth

        val health = ceil(type.getHealth(player))
        val healthMax = ceil(type.getMaxHealth(player))

        val rows = getHeartRows(healthMax)
        val spacing = getHeartSpacing(rows)

        val maxHeartFlash = type.heartJumpEndTick > ticks && (type.heartJumpEndTick - ticks) / 3 % 2 == 1L
        val hardcore = player.world.levelProperties.isHardcore
        val danger = shouldShake(player)

        //flashing hearts
        val currentTime = Util.getMeasuringTimeMs()
        if (health < type.lastHealthValue) {
            type.lastHealthCheckTime = currentTime
            type.heartJumpEndTick = ticks + 20L
        } else if (health > type.lastHealthValue) {
            type.lastHealthCheckTime = currentTime
            type.heartJumpEndTick = ticks + 10L
        }
        if (currentTime - type.lastHealthCheckTime > 1000L) {
            type.renderHealthValue = health
            type.lastHealthCheckTime = currentTime
        }
        type.lastHealthValue = health

        val regen = ceil(type.getHealth(player) + LCCComponents.hearts.maybeGet(player).map { it.crystalRegenAmount }.orElse(0f))

        val xPos = sw.div(2).minus(91)

        val half = health.rem(2) == 1
        val halfFlash = type.renderHealthValue.rem(2) == 1
        val halfMax = healthMax.rem(2) == 1
        val halfRegen = regen.rem(2) == 1

        random.setSeed((ticks * 324587).toLong())
        for (row in rows-1 downTo 0) {
            val yPos = top + (rows-1-row)*spacing

            val inRow = ceil(getHalfHeartsInRow(health, row).div(2f))
            val inRowFlash = ceil(getHalfHeartsInRow(type.renderHealthValue, row).div(2f))
            val inRowMax = ceil(getHalfHeartsInRow(healthMax, row).div(2f))
            val inRowRegen = ceil(getHalfHeartsInRow(regen, row).div(2f))
            val effect = if (player.hasStatusEffect(StatusEffects.POISON)) 36 else player.hasStatusEffect(StatusEffects.WITHER).toInt(72)

            for (heart in 0 until inRowMax) {
                val xPosCurrent = xPos + heart * 8;
                val yPosCurrent = yPos + this.random.nextInt(2).times(danger.toInt())

                val maxHeartHalf = row.times(20) + heart.times(2) >= healthMax - 1 && halfMax
                this.drawTexture(matrix, xPosCurrent, yPosCurrent, maxHeartHalf.toInt(36) + maxHeartFlash.toInt(9), type.v + hardcore.toInt(9), 9, 9)
                if (maxHeartFlash && heart < inRowFlash) {
                    this.drawTexture(matrix, xPosCurrent, yPosCurrent, 90 + (maxHeartHalf || (row.times(20) + heart.times(2) >= type.renderHealthValue - 1 && halfFlash)).toInt(9) + effect,  type.v + hardcore.toInt(9), 9, 9)
                }
                if (type == HeartType.CRYSTAL && heart < inRowRegen) {
                    this.drawTexture(matrix, xPosCurrent, yPosCurrent, 72 + (row.times(20) + heart.times(2) >= regen - 1 && halfRegen).toInt(9) + effect, type.v + 18, 9, 9)
                }
                if (heart < inRow) {
                    this.drawTexture(matrix, xPosCurrent, yPosCurrent, 72 + (row.times(20) + heart.times(2) >= health - 1 && half).toInt(9) + effect, type.v + hardcore.toInt(9), 9, 9)
                }
            }
        }
    }

    fun shouldShake(player: PlayerEntity): Boolean = ceil(HeartType.values().sumOf { it.getHealth(player).toDouble() }) <= 4

}