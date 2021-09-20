package com.joshmanisdabomb.lcc.directory

import com.google.common.collect.ImmutableList
import com.joshmanisdabomb.lcc.widens.ClientWidens
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.Identifier
import org.apache.commons.lang3.tuple.Triple

object LCCRenderLayers : BasicDirectory<(Identifier) -> RenderLayer, Unit>() {

    val bright by entry(::initialiser) { {
        ClientWidens.renderLayer("entity_lcc_bright",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            shader = { GameRenderer.getRenderTypeEyesShader()!! },
            textures = ImmutableList.of(Triple.of(it, false, false)),
            cull = true,
            affectsOutline = true
        )
    } }

    fun initialiser(input: (Identifier) -> RenderLayer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

}