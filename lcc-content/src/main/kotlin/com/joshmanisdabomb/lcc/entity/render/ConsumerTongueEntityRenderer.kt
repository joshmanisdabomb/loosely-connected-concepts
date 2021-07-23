package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.ConsumerTongueEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack

class ConsumerTongueEntityRenderer(context: EntityRendererFactory.Context) : EntityRenderer<ConsumerTongueEntity>(context) {

    init {
        shadowRadius = 0f
        shadowOpacity = 0f
    }

    override fun render(entity: ConsumerTongueEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {

    }

    override fun getTexture(entity: ConsumerTongueEntity) = Companion.texture

    companion object {
        val texture = LCC.id("textures/entity/consumer/tongue.png")
    }

}