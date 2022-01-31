package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.FlyEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.ModelWithHead
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper

@Environment(EnvType.CLIENT)
class FlyEntityModel(private val root: ModelPart) : SinglePartEntityModel<FlyEntity>(), ModelWithHead {

    private val bone: ModelPart
    private val head: ModelPart
    private val leftWing: ModelPart
    private val rightWing: ModelPart

    init {
        this.bone = root.getChild("bone")
        this.head = bone.getChild(EntityModelPartNames.HEAD)
        this.leftWing = bone.getChild(EntityModelPartNames.LEFT_WING)
        this.rightWing = bone.getChild(EntityModelPartNames.RIGHT_WING)
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        head.visible = false
        leftWing.visible = true
        rightWing.visible = true
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }

    fun renderHead(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        head.visible = true
        leftWing.visible = false
        rightWing.visible = false
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }

    override fun setAngles(entity: FlyEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        bone.yaw = headYaw * (Math.PI.toFloat() / 180)
        leftWing.roll = MathHelper.cos(limbAngle) * limbDistance
        rightWing.roll = -leftWing.roll
    }

    override fun getPart() = root

    override fun getHead() = head

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 21.5f, 0.0f))
            val head = bone.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 1.0f), ModelTransform.pivot(0.0f, 0.0f, 0.0f))
            val leftWing = bone.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(0, 2).cuboid(0.0f, -0.05f, -0.5f, 2.0f, 0.1f, 1.0f), ModelTransform.pivot(0.5f, -0.45f, 0.0f))
            val rightWing = bone.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(0, 4).cuboid(-2.0f, -0.05f, -0.5f, 2.0f, 0.1f, 1.0f), ModelTransform.pivot(-0.5f, -0.45f, 0.0f))

            return TexturedModelData.of(modelData, 16, 16)
        }
    }

}