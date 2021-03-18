package com.joshmanisdabomb.lcc.utils

import com.joshmanisdabomb.lcc.mixin.base.client.ClientPlayerInteractionAccessor
import com.joshmanisdabomb.lcc.mixin.base.client.WorldRendererAccessor
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexFormat
import net.minecraft.util.math.BlockPos

object RenderingUtils {

    fun breakingProgress(pos: BlockPos): Float? {
        val interaction = MinecraftClient.getInstance().interactionManager as ClientPlayerInteractionAccessor
        if (interaction.isBreakingBlock && interaction.currentBreakingPos == pos) return interaction.currentBreakingProgress

        val worldRenderer = MinecraftClient.getInstance().worldRenderer as WorldRendererAccessor
        return worldRenderer.blockBreakingInfos.values.filter { it.pos == pos }.maxOfOrNull { it.stage.toFloat().div(10) }
    }

    fun renderLayer(name: String, vertexFormat: VertexFormat, drawMode: VertexFormat.DrawMode, expectedBuffer: Int, crumbling: Boolean, translucent: Boolean, parameters: RenderLayer.MultiPhaseParameters): RenderLayer = RenderLayer.of(name, vertexFormat, drawMode, expectedBuffer, crumbling, translucent, parameters)

}