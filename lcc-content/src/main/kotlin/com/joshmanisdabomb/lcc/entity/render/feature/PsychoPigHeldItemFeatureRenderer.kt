package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3f

class PsychoPigHeldItemFeatureRenderer(context: FeatureRendererContext<PsychoPigEntity, EntityModel<PsychoPigEntity>>) : FeatureRenderer<PsychoPigEntity, EntityModel<PsychoPigEntity>>(context) {

    override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: PsychoPigEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        if (!entity.isAggro) return
        matrices.push()
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-190.0f))
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f))
        matrices.translate(0.1875.times(entity.isLeftHanded.transformInt(-1,1)), -0.1875, -0.875)
        MinecraftClient.getInstance().heldItemRenderer.renderItem(entity, entity.mainHandStack, if (entity.isLeftHanded) ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND else ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, entity.isLeftHanded, matrices, vertexConsumers, light)
        MinecraftClient.getInstance().heldItemRenderer.renderItem(entity, entity.offHandStack, if (!entity.isLeftHanded) ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND else ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, !entity.isLeftHanded, matrices, vertexConsumers, light)
        matrices.pop()
    }

}