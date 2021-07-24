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
        return hooked.pos.add(0.0, hooked.height.div(2.0), 0.0).subtract(entity.pos)
    }

    override fun render(entity: ConsumerTongueEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        val owner = entity.owner ?: return
        val hooked = entity.hooked

        matrices.push()

        val d = owner.x - (hooked ?: entity).x
        val e = entity.targetY!! - (hooked?.y?.plus(hooked.height.div(2f)) ?: entity.y)
        val f = owner.z - (hooked ?: entity).z
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