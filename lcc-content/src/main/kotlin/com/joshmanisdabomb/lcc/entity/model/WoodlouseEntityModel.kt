package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.WoodlouseEntity
import com.joshmanisdabomb.lcc.extensions.sqrt
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.AnimalModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import kotlin.math.absoluteValue
import kotlin.math.sign

class WoodlouseEntityModel<T : WoodlouseEntity>(root: ModelPart) : AnimalModel<T>() {

    private val bone: ModelPart
    private val head: ModelPart
    private val body1: ModelPart
    private val body2: ModelPart
    private val body3: ModelPart
    private val body4: ModelPart
    private val body5: ModelPart
    private val body6: ModelPart
    private val leftLeg1: ModelPart
    private val leftLeg2: ModelPart
    private val leftLeg3: ModelPart
    private val leftLeg4: ModelPart
    private val leftLeg5: ModelPart
    private val leftLeg6: ModelPart
    private val rightLeg1: ModelPart
    private val rightLeg2: ModelPart
    private val rightLeg3: ModelPart
    private val rightLeg4: ModelPart
    private val rightLeg5: ModelPart
    private val rightLeg6: ModelPart
    private val leftAntenna: ModelPart
    private val rightAntenna: ModelPart

    init {
        this.bone = root.getChild("bone")
        this.body3 = this.bone.getChild("body_3")
        this.body2 = this.body3.getChild("body_2")
        this.body4 = this.body3.getChild("body_4")
        this.body1 = this.body2.getChild("body_1")
        this.body5 = this.body4.getChild("body_5")
        this.body6 = this.body5.getChild("body_6")
        this.head = this.body1.getChild("head")
        this.leftLeg1 = this.body1.getChild("left_leg_1")
        this.leftLeg2 = this.body2.getChild("left_leg_2")
        this.leftLeg3 = this.body3.getChild("left_leg_3")
        this.leftLeg4 = this.body4.getChild("left_leg_4")
        this.leftLeg5 = this.body5.getChild("left_leg_5")
        this.leftLeg6 = this.body6.getChild("left_leg_6")
        this.rightLeg1 = this.body1.getChild("right_leg_1")
        this.rightLeg2 = this.body2.getChild("right_leg_2")
        this.rightLeg3 = this.body3.getChild("right_leg_3")
        this.rightLeg4 = this.body4.getChild("right_leg_4")
        this.rightLeg5 = this.body5.getChild("right_leg_5")
        this.rightLeg6 = this.body6.getChild("right_leg_6")
        this.leftAntenna = this.head.getChild("left_antenna")
        this.rightAntenna = this.head.getChild("right_antenna")
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }

    override fun animateModel(entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float) {

    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        body2.yaw = headYaw.times((Math.PI.toFloat().div(180f))).absoluteValue.sqrt().times(0.2f).times(headYaw.sign)
        body4.yaw = -body2.yaw
        body1.yaw = body2.yaw
        body5.yaw = -body2.yaw
        head.yaw = body2.yaw
        body6.yaw = -body2.yaw

        leftLeg1.pitch = MathHelper.cos(limbAngle * 1.9f) * 1.4f * limbDistance
        rightLeg1.pitch = MathHelper.cos(limbAngle * 1.9f + 3.1415927f) * 1.4f * limbDistance
        leftLeg2.pitch = -leftLeg1.pitch
        rightLeg2.pitch = -rightLeg1.pitch
        leftLeg3.pitch = leftLeg1.pitch
        rightLeg3.pitch = rightLeg1.pitch
        leftLeg4.pitch = -leftLeg1.pitch
        rightLeg4.pitch = -rightLeg1.pitch
        leftLeg5.pitch = leftLeg1.pitch
        rightLeg5.pitch = rightLeg1.pitch
        leftLeg6.pitch = -leftLeg1.pitch
        rightLeg6.pitch = -rightLeg1.pitch

        body2.pitch = entity.hurtTime.minus(MinecraftClient.getInstance().tickDelta).coerceAtLeast(0.0f).times(-0.04f)
        body4.pitch = -body2.pitch
        body1.pitch = body2.pitch
        body5.pitch = body4.pitch
        head.pitch = body2.pitch
        body6.pitch = body4.pitch

        leftAntenna.pitch = MathHelper.cos(animationProgress.times(0.3f)).times(0.03f).plus(MathHelper.cos(limbAngle.times(0.4f)).absoluteValue.times(0.2f).times(limbDistance)).coerceAtLeast(0.0f).plus(0.1f)
        rightAntenna.pitch = leftAntenna.pitch
    }

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root
            val bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 0.0f, 0.0f))
            val body_3 = bone.addChild("body_3", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 4.0F), ModelTransform.pivot(0.0f, 22.0f, 0.0f))
            val body_2 = body_3.addChild("body_2", ModelPartBuilder.create().uv(24, 0).cuboid(-3.5F, -7.0F, -2.0F, 7.0F, 7.0F, 4.0F), ModelTransform.pivot(0.0f, 0.0f, -4.0f))
            val body_4 = body_3.addChild("body_4", ModelPartBuilder.create().uv(24, 11).cuboid(-3.5F, -7.0F, -2.0F, 7.0F, 7.0F, 4.0F), ModelTransform.pivot(0.0f, 0.0f, 4.0f))
            val body_1 = body_2.addChild("body_1", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, -5.0F, -1.5F, 6.0F, 5.0F, 3.0F), ModelTransform.pivot(0.0f, 0.0f, -3.5f))
            val body_5 = body_4.addChild("body_5", ModelPartBuilder.create().uv(0, 20).cuboid(-3.0F, -5.0F, -1.5F, 6.0F, 5.0F, 3.0F), ModelTransform.pivot(0.0f, 0.0f, 3.5f))
            val body_6 = body_5.addChild("body_6", ModelPartBuilder.create().uv(46, 5).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0f, 0.0f, 2.50f))
            val head = body_1.addChild("head", ModelPartBuilder.create().uv(46, 0).cuboid(-2.0F, -3.0F, -0.5F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0f, -0.7f, -2.5f))

            val rightLeg1 = body_1.addChild("right_leg_1", ModelPartBuilder.create().uv(0, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-2.75F, 0.0F, 0.5F))
            val leftLeg1 = body_1.addChild("left_leg_1", ModelPartBuilder.create().uv(4, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(2.75F, 0.0F, 0.5F))
            val rightLeg2 = body_2.addChild("right_leg_2", ModelPartBuilder.create().uv(8, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-3.25F, 0.0F, 0.0F))
            val leftLeg2 = body_2.addChild("left_leg_2", ModelPartBuilder.create().uv(12, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(3.25F, 0.0F, 0.0F))
            val rightLeg3 = body_3.addChild("right_leg_3", ModelPartBuilder.create().uv(16, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-3.75F, 0.0F, 0.0F))
            val leftLeg3 = body_3.addChild("left_leg_3", ModelPartBuilder.create().uv(20, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(3.75F, 0.0F, 0.0F))
            val rightLeg4 = body_4.addChild("right_leg_4", ModelPartBuilder.create().uv(24, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-3.25F, 0.0F, 0.0F))
            val leftLeg4 = body_4.addChild("left_leg_4", ModelPartBuilder.create().uv(28, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(3.25F, 0.0F, 0.0F))
            val rightLeg5 = body_5.addChild("right_leg_5", ModelPartBuilder.create().uv(32, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-2.75F, 0.0F, 0.5F))
            val leftLeg5 = body_5.addChild("left_leg_5", ModelPartBuilder.create().uv(36, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(2.75F, 0.0F, 0.5F))
            val rightLeg6 = body_6.addChild("right_leg_6", ModelPartBuilder.create().uv(40, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(-1.75F, 0.0F, 0.0F))
            val leftLeg6 = body_6.addChild("left_leg_6", ModelPartBuilder.create().uv(44, 28).cuboid(-0.5f, 0.0f, -0.5f, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(1.75F, 0.0F, 0.0F))

            val leftAntenna = head.addChild("left_antenna", ModelPartBuilder.create().uv(58, 4).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F).uv(54, 13).cuboid(-0.5f, -3.25F, -5.0F, 1.0F, 1.0F, 4.0F), ModelTransform.pivot(-1.25f, -3f, 0.75f))
            val rightAntenna = head.addChild("right_antenna", ModelPartBuilder.create().uv(58, 0).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F).uv(54, 8).cuboid(-0.5f, -3.25F, -5.0F, 1.0F, 1.0F, 4.0F), ModelTransform.pivot(1.25f, -3f, 0.75f))

            return TexturedModelData.of(modelData, 64, 32)
        }
    }

    override fun getHeadParts() = emptyList<ModelPart>()

    override fun getBodyParts() = listOf(body3)

}