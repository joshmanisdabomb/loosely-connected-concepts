package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.entity.TravellerEntity
import com.joshmanisdabomb.lcc.entity.model.TravellerEntityModel
import com.joshmanisdabomb.lcc.entity.render.TravellerEntityRenderer
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.VillagerResemblingModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3f

class TravellerBindleFeatureRenderer(context: FeatureRendererContext<TravellerEntity, VillagerResemblingModel<TravellerEntity>>, private val model: TravellerEntityModel<TravellerEntity>) : FeatureRenderer<TravellerEntity, VillagerResemblingModel<TravellerEntity>>(context) {

    override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: TravellerEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch)
        matrices.push()
        if (entity.isLeftHanded) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f))
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180f))
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180f))
            matrices.translate(-7.5/16, 0.0, 7.5/16)
        }
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TravellerEntityRenderer.bindle_texture)), light, MobEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f)
        matrices.pop()
    }

}
