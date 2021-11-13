package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.entity.model.DiscipleEntityModel
import com.joshmanisdabomb.lcc.extensions.exp
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

class DiscipleBrainFeatureRenderer(context: FeatureRendererContext<DiscipleEntity, DiscipleEntityModel>) : FeatureRenderer<DiscipleEntity, DiscipleEntityModel>(context) {

    override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: DiscipleEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        val hyaw = MathHelper.lerp(tickDelta, entity.prevHeadYaw, entity.headYaw)
        val byaw = -MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.bodyYaw)
        val pitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch)
        matrices.push()

        val sin = MathHelper.sin(animationProgress.div(2.5f) + limbDistance).exp(5)
        val cos = MathHelper.cos(animationProgress.div(2.5f) + limbDistance).exp(5)
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(byaw + hyaw))
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(pitch))
        matrices.translate(0.0, 13.div(16.0).unaryMinus(), 0.0)

        matrices.push()

        matrices.scale(1.0f + sin.times(0.05f).coerceAtLeast(0f) + sin.times(0.05f), 1f, 1.0f + cos.times(0.05f).coerceAtLeast(0f) + cos.times(0.05f))
        val renderLayer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(getTexture(entity)))
        contextModel.renderBrainBase(matrices, renderLayer, light, MobEntityRenderer.getOverlay(entity, 0.0f))

        matrices.pop()
        matrices.push()

        matrices.scale(1.0f + cos.times(0.06f).coerceAtLeast(0f) + cos.times(0.06f), 1f, 1.0f + sin.times(0.06f).coerceAtLeast(0f) + sin.times(0.06f))
        contextModel.renderBrainTop(matrices, renderLayer, light, MobEntityRenderer.getOverlay(entity, 0.0f))

        matrices.pop()
        matrices.pop()
    }

}
