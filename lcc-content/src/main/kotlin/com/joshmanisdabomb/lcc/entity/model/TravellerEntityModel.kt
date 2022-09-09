package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.TravellerEntity
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack

class TravellerEntityModel<T : TravellerEntity>(root: ModelPart) : SinglePartEntityModel<T>() {

    private val main: ModelPart

    init {
        this.main = root.getChild("main")
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }

    override fun animateModel(entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float) {

    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        main.pitch = -0.6f
        main.yaw = 0.65f
    }

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root
            val main = root.addChild("main",
                ModelPartBuilder.create()
                    .uv(0, 0).cuboid(-0.5F, -24.0F, -0.5F, 1.0F, 24.0F, 1.0F)
                    .uv(4, 8).cuboid(-1.5F, -25.5F, -1.5F, 3.0F, 3.0F, 1.0F)
                    .uv(11, 11).cuboid(-1.0F, -25.0F, -0.5F, 2.0F, 2.0F, 1.0F)
                    .uv(4, 0).cuboid(-2.0F, -26.0F, 0.5F, 4.0F, 4.0F, 4.0F)
                , ModelTransform.pivot(3.75f, 3.25f, -3.75f))

            return TexturedModelData.of(modelData, 32, 32)
        }
    }

    override fun getPart() = main

}