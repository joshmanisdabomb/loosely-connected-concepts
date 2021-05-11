package com.joshmanisdabomb.lcc.utils

import com.joshmanisdabomb.lcc.mixin.infra.client.ClientPlayerInteractionAccessor
import com.joshmanisdabomb.lcc.mixin.infra.client.WorldRendererAccessor
import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.BlockPos

object RenderingUtils {

    fun breakingProgress(pos: BlockPos): Float? {
        val interaction = MinecraftClient.getInstance().interactionManager as ClientPlayerInteractionAccessor
        if (interaction.isBreakingBlock && interaction.currentBreakingPos == pos) return interaction.currentBreakingProgress

        val worldRenderer = MinecraftClient.getInstance().worldRenderer as WorldRendererAccessor
        return worldRenderer.blockBreakingInfos.values.filter { it.pos == pos }.maxOfOrNull { it.stage.toFloat().div(10) }
    }

}