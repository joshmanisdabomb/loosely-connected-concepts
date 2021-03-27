package com.joshmanisdabomb.lcc.gui.overlay

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor
import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import kotlin.math.ceil

@Environment(EnvType.CLIENT)
object ContainedOverlay : DrawableHelper() {

    private val icons = LCC.gui("contained")

    fun render(matrix: MatrixStack, player: PlayerEntity, pieces: Iterable<ItemStack>, ticks: Int): Boolean {
        val sw = MinecraftClient.getInstance().window.scaledWidth
        val sh = MinecraftClient.getInstance().window.scaledHeight

        var flag = false
        for (piece in pieces) {
            if ((piece.item as? ContainedArmor)?.hideAirMeter(player, piece, pieces) == true) {
                flag = true
                break
            }
        }
        if (!flag) return false

        val maxOxygen = ContainedArmor.getTotalMaxOxygen<OxygenStorage>(pieces)
        if (maxOxygen <= 0f) return false
        val oxygen = ContainedArmor.getTotalOxygen<OxygenStorage>(pieces)
        if (oxygen <= 0f) return false
        val value = oxygen.div(maxOxygen).times(10f)

        RenderSystem.setShaderTexture(0, icons)
        val current = ceil(value).toInt()
        for (i in 0 until current) {
            this.drawTexture(matrix, sw.div(2) + 82 - i.times(8), sh - 49, if (i == current - 1 && value.rem(1f) <= 0.01f) 9 else 0, 0, 9, 9)
        }
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE)
        return true
    }

}