package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.ConsumerTongueEntity
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack

class ConsumerTongueEntityModel(root: ModelPart) : EntityModel<ConsumerTongueEntity>(RenderLayer::getEntitySolid) {

    private val tongue: ModelPart

    init {
        this.tongue = root.getChild("tongue")
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        this.tongue.render(matrices, vertices, light, overlay)
    }

    override fun animateModel(entity: ConsumerTongueEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float) {

    }

    override fun setAngles(entity: ConsumerTongueEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        tongue.pitch = 0f
        tongue.roll = 0f
        tongue.yaw = 0f
    }

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val tongue = root.addChild("tongue", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5f, -63.0f, -0.5f, 7.0f, 63.0f, 1.0f), ModelTransform.pivot(0f, 0f, 0f))

            return TexturedModelData.of(modelData, 32, 64)
        }
    }

}