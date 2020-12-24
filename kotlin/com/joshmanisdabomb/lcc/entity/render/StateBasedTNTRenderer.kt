package com.joshmanisdabomb.lcc.entity.render

import net.minecraft.block.BlockState
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.TntEntityRenderer
import net.minecraft.client.render.entity.TntMinecartEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.TntEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

class StateBasedTNTRenderer(val state: BlockState, dispatcher: EntityRendererFactory.Context) : TntEntityRenderer(dispatcher) {

    override fun render(entity: TntEntity, yaw: Float, tickDelta: Float, matrixStack: MatrixStack, vertexConsumerProvider: VertexConsumerProvider, light: Int) {
        matrixStack.push()
        matrixStack.translate(0.0, 0.5, 0.0)
        if (entity.fuse.toFloat() - tickDelta + 1.0f < 10.0f) {
            var h = 1.0f - (entity.fuse.toFloat() - tickDelta + 1.0f) / 10.0f
            h = MathHelper.clamp(h, 0.0f, 1.0f)
            h *= h
            h *= h
            val k = 1.0f + h * 0.3f
            matrixStack.scale(k, k, k)
        }
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f))
        matrixStack.translate(-0.5, -0.5, 0.5)
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f))
        TntMinecartEntityRenderer.renderFlashingBlock(state, matrixStack, vertexConsumerProvider, light, entity.fuse / 5 % 2 == 0)
        matrixStack.pop()
        if (hasLabel(entity)) {
            renderLabelIfPresent(entity, entity.displayName, matrixStack, vertexConsumerProvider, light)
        }
    }

}
