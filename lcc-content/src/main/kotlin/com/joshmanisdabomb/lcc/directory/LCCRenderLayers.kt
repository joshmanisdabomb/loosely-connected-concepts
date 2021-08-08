package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.widens.ClientWidens
import net.minecraft.client.render.*
import net.minecraft.util.Identifier

object LCCRenderLayers : BasicDirectory<(Identifier) -> RenderLayer, Unit>() {

    private val eyes_shader by lazy { RenderPhase.Shader(GameRenderer::getRenderTypeEyesShader) }

    private val cull_enabled by lazy { RenderPhase.Cull(true) }
    private val cull_disabled by lazy { RenderPhase.Cull(false) }
    private val color_mask by lazy { RenderPhase.WriteMaskState(true, false) }

    val bright by entry(::initialiser) { {
        val multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
            .shader(eyes_shader)
            .texture(RenderPhase.Texture(it, false, false))
            .cull(cull_enabled)
            .build(true)
        ClientWidens.renderLayer("entity_lcc_bright",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            multiPhaseParameters
        )
    } }

    fun initialiser(input: (Identifier) -> RenderLayer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

}