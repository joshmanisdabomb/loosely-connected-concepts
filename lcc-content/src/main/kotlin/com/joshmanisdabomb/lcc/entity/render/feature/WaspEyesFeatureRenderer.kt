package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.WaspEntity
import com.joshmanisdabomb.lcc.entity.model.WaspEntityModel
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.util.math.MatrixStack

class WaspEyesFeatureRenderer(renderer: FeatureRendererContext<WaspEntity, WaspEntityModel<WaspEntity>>) : EyesFeatureRenderer<WaspEntity, WaspEntityModel<WaspEntity>>(renderer) {

    override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: WaspEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        if (!entity.hasAngerTime()) return
        super.render(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch)
    }

    override fun getEyesTexture() = eyes

    companion object {
        val eyes = RenderLayer.getEyes(LCC.id("textures/entity/wasp/eyes.png"))
    }

}