package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.SaltEntity
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3f

class SaltEntityRenderer(context: EntityRendererFactory.Context) : EntityRenderer<SaltEntity>(context) {

    init {
        shadowRadius = 0f
        shadowOpacity = 0f
    }

    override fun render(entity: SaltEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        matrices.push()
        val scale = entity.hashCode().rem(16).div(32f).plus(0.5f)
        val uv = entity.hashCode().div(7).rem(4)
        matrices.scale(scale, scale, scale)
        matrices.multiply(this.dispatcher.rotation)
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F))
        val vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(texture))
        val entry = matrices.peek()
        val matrix4f = entry.model
        val matrix3f = entry.normal
        vertexConsumer.vertex(matrix4f, -0.5f, -0.5f, 0.0F).color(255, 255, 255, 255).texture(uv.div(4f), uv.div(4f)).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).next()
        vertexConsumer.vertex(matrix4f, 0.5f, -0.5f, 0.0F).color(255, 255, 255, 255).texture(uv.div(4f).plus(0.25f), uv.div(4f)).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).next()
        vertexConsumer.vertex(matrix4f, 0.5f, 0.5f, 0.0F).color(255, 255, 255, 255).texture(uv.div(4f).plus(0.25f), uv.div(4f).plus(0.25f)).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).next()
        vertexConsumer.vertex(matrix4f, -0.5f, 0.5f, 0.0F).color(255, 255, 255, 255).texture(uv.div(4f), uv.div(4f).plus(0.25f)).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).next()
        matrices.pop()
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    override fun getTexture(entity: SaltEntity) = Companion.texture

    companion object {
        val texture = LCC.id("textures/entity/salt.png")
    }

}