package com.joshmanisdabomb.lcc.utils

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexFormat

object ClientAccessUtils {

    fun renderLayer(name: String, vertexFormat: VertexFormat, drawMode: VertexFormat.DrawMode, expectedBuffer: Int, crumbling: Boolean, translucent: Boolean, parameters: RenderLayer.MultiPhaseParameters): RenderLayer = RenderLayer.of(name, vertexFormat, drawMode, expectedBuffer, crumbling, translucent, parameters)

}