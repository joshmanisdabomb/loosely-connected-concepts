package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.ConsumerTongueEntity
import com.joshmanisdabomb.lcc.entity.model.ConsumerTongueEntityModel
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f

class ConsumerTongueEntityRenderer(context: EntityRendererFactory.Context) : EntityRenderer<ConsumerTongueEntity>(context) {

    val model = ConsumerTongueEntityModel(context.getPart(LCCModelLayers.consumer_tongue))

    init {
        shadowRadius = 0f
        shadowOpacity = 0f
    }

    override fun getPositionOffset(entity: ConsumerTongueEntity, tickDelta: Float): Vec3d {
        val hooked = entity.hooked ?: return super.getPositionOffset(entity, tickDelta)
        return Vec3d(MathHelper.lerp(tickDelta.toDouble(), hooked.lastRenderX, hooked.x), MathHelper.lerp(tickDelta.toDouble(), hooked.lastRenderY, hooked.y) + hooked.height.div(2f), MathHelper.lerp(tickDelta.toDouble(), hooked.lastRenderZ, hooked.z))
            .subtract(MathHelper.lerp(tickDelta.toDouble(), entity.lastRenderX, entity.x), MathHelper.lerp(tickDelta.toDouble(), entity.lastRenderY, entity.y), MathHelper.lerp(tickDelta.toDouble(), entity.lastRenderZ, entity.z))
    }

    override fun render(entity: ConsumerTongueEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        val owner = entity.owner ?: return
        val hooked = entity.hooked
        val origin = hooked ?: entity

        matrices.push()

        val d = MathHelper.lerp(tickDelta.toDouble(), owner.lastRenderX, owner.x) - MathHelper.lerp(tickDelta.toDouble(), origin.lastRenderX, origin.x)
        val e = entity.getTargetY(MathHelper.lerp(tickDelta.toDouble(), owner.lastRenderY, owner.y))!! - MathHelper.lerp(tickDelta.toDouble(), origin.lastRenderY, origin.y).plus(if (origin === hooked) hooked.height.div(2f) else 0f)
        val f = MathHelper.lerp(tickDelta.toDouble(), owner.lastRenderZ, owner.z) - MathHelper.lerp(tickDelta.toDouble(), origin.lastRenderZ, origin.z)
        val g = MathHelper.sqrt(d * d + f * f).toDouble()
        val h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
        val i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
        val j = MathHelper.sqrt(d * d + e * e + f * f)
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f - i))
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90f - h))

        matrices.scale(1f, (16f/63f).times(j), 1f)

        model.setAngles(entity, 0f, 0f, 0f, 0f, 0f)

        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(texture)), light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f)
        matrices.pop()
    }

    override fun getTexture(entity: ConsumerTongueEntity) = Companion.texture

    companion object {
        val texture = LCC.id("textures/entity/consumer/tongue.png")
    }

}