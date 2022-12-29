package com.joshmanisdabomb.lcc.widens

import com.google.common.collect.ImmutableList
import net.minecraft.client.font.FontStorage
import net.minecraft.client.font.Glyph
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.Shader
import net.minecraft.client.render.VertexFormat
import net.minecraft.util.Identifier
import org.apache.commons.lang3.tuple.Triple

object ClientWidens {

    fun renderLayer(name: String, vertexFormat: VertexFormat, drawMode: VertexFormat.DrawMode, expectedBuffer: Int, crumbling: Boolean, translucent: Boolean, affectsOutline: Boolean, textures: ImmutableList<Triple<Identifier, Boolean, Boolean>> = ImmutableList.of(), cull: Boolean = true, writeColorMask: Boolean = true, writeDepthMask: Boolean = true, shader: (() -> Shader)? = null): RenderLayer = RenderLayer.of(name, vertexFormat, drawMode, expectedBuffer, crumbling, translucent, RenderLayer.MultiPhaseParameters.builder()
        .apply { if (shader != null) shader(RenderPhase.Shader(shader)) }
        .apply { when (textures?.size) {
            1 -> texture(RenderPhase.Texture(textures.get(0).left, textures.get(0).middle, textures.get(0).right))
            0, null -> {}
            else -> texture(RenderPhase.Textures.create().apply { textures.forEach { add(it.left, it.middle, it.right) } }.build())
        } }
        .cull(RenderPhase.Cull(cull))
        .writeMaskState(RenderPhase.WriteMaskState(writeColorMask, writeDepthMask))
        .build(affectsOutline)
    )

    fun glyphPair(glyph: Glyph, advance: Glyph) = FontStorage.GlyphPair(glyph, advance)

}