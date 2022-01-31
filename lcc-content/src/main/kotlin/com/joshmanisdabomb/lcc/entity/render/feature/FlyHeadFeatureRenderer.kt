package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.entity.FlyEntity
import com.joshmanisdabomb.lcc.entity.model.FlyEntityModel
import com.joshmanisdabomb.lcc.entity.render.FlyEntityRenderer.Companion.getFlyColor
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.util.math.MatrixStack

class FlyHeadFeatureRenderer(context: FeatureRendererContext<FlyEntity, FlyEntityModel>) : FeatureRenderer<FlyEntity, FlyEntityModel>(context) {

    override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: FlyEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        val renderLayer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(getTexture(entity)))
        val owner = entity.owner
        if (owner is ClientPlayerEntity) {
            val flyColor = getFlyColor(owner)
            if (flyColor != null) {
                contextModel.renderHead(matrices, renderLayer, light, MobEntityRenderer.getOverlay(entity, 0.0f), (flyColor shr 16 and 255).div(255f), (flyColor shr 8 and 255).div(255f), (flyColor and 255).div(255f), 1.0f)
            } else {
                contextModel.renderHead(matrices, renderLayer, light, MobEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f)
            }
        } else {
            contextModel.renderHead(matrices, renderLayer, light, MobEntityRenderer.getOverlay(entity, 0.0f), 0.0f, 0.0f, 0.0f, 1.0f)
        }
    }

}
