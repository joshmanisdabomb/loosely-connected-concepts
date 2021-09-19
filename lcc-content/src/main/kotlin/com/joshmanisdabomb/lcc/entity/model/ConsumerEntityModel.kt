package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.ConsumerEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper

class ConsumerEntityModel<T : ConsumerEntity>(root: ModelPart) : EntityModel<T>(RenderLayer::getEntitySolid) {

    private val bone: ModelPart
    private val head: ModelPart
    private val jaw: ModelPart
    private val body1: ModelPart
    private val body2: ModelPart
    private val rightHindLeg: ModelPart
    private val leftHindLeg: ModelPart
    private val rightFrontLeg: ModelPart
    private val leftFrontLeg: ModelPart

    private val teeth: List<ModelPart>

    init {
        this.bone = root.getChild("bone")
        this.head = this.bone.getChild("head")
        this.jaw = this.head.getChild("jaw")
        this.teeth = List(7) {
            this.jaw.getChild("tooth_${it+1}")
        }
        this.body1 = this.bone.getChild("body_1")
        this.body2 = this.bone.getChild("body_2")
        this.rightHindLeg = this.bone.getChild("right_hind_leg")
        this.leftHindLeg = this.bone.getChild("left_hind_leg")
        this.rightFrontLeg = this.bone.getChild("right_front_leg")
        this.leftFrontLeg = this.bone.getChild("left_front_leg")
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        this.bone.render(matrices, vertices, light, overlay)
    }

    override fun animateModel(entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        if (!MinecraftClient.getInstance().isPaused) {
            var jawTarget = 0f
            var jawSpeed = 0.045f
            if (this.handSwingProgress > 0.0) {
                jawTarget = (0.5f - this.handSwingProgress).times(4f).coerceAtLeast(0f)
                jawSpeed = (1f - this.handSwingProgress)
            } else if (entity.isTongueActive) {
                jawTarget = 1f
            } else if (entity.aggroTarget != null) {
                if (entity.canBiteTarget(entity.aggroTarget)) {
                    jawTarget = 0.5f
                } else {
                    jawTarget = 0.15f
                }
            }
            entity.jawPitch = entity.jawPitch.plus((jawTarget - entity.jawPitch).times(jawSpeed))
        }

        head.pitch = -MathHelper.lerp(tickDelta, entity.lastJawPitch, entity.jawPitch)
        jaw.pitch = -head.pitch.times(2f)
    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        head.yaw = headYaw * 0.017453292f
        head.pitch += headPitch * 0.017453292f

        head.pitch -= MathHelper.sin(animationProgress.times(0.07f)).times(0.07f)
        head.pitch = head.pitch.coerceAtMost(0f)
        jaw.pitch += MathHelper.sin(animationProgress.times(0.07f)).times(0.14f)
        jaw.pitch = jaw.pitch.coerceAtLeast(0f)

        teeth.forEach { it.pitch = -0.2f }

        body1.pitch = 0.6f
        body2.pitch = 0.3f

        leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance
        rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + 3.1415927f) * 1.4f * limbDistance
        leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + 3.1415927f) * 1.4f * limbDistance
        rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance
    }

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root
            val bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 0.0f, 0.0f))
            val head = bone.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5f, -5.0f, -8.0f, 13.0f, 5.0f, 8.0f), ModelTransform.pivot(0f, 12.3f, -0.0f))

            val jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 13).cuboid(-6.5f, 0.0f, -8.0f, 13.0f, 1.0f, 8.0f), ModelTransform.pivot(0f, 0f, 0f))

            val teeth = List(7) {
                jaw.addChild("tooth_${it+1}", ModelPartBuilder.create().uv(it.times(4), 22).cuboid(-0.5f, -4f, 0f, 1.0f, 4.0f, 1.0f), ModelTransform.pivot(-5.7f + it.times(1.9f), 0f, -7.7f))
            }

            val body1 = bone.addChild("body_1", ModelPartBuilder.create().uv(0, 27).cuboid(-4.0f, -3.0f, -3.0f, 8.0f, 6.0f, 6.0f), ModelTransform.pivot(0f, 14f, 2.5f))
            val body2 = bone.addChild("body_2", ModelPartBuilder.create().uv(0, 39).cuboid(-2.5f, -3.0f, -3.0f, 5.0f, 6.0f, 6.0f), ModelTransform.pivot(0f, 17.2f, 5f))

            val leftHindLeg = bone.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 51).cuboid(-1.0f, 0.0f, -1.0f, 2.0f, 5.0f, 2.0f), ModelTransform.pivot(2f, 19f, 8f))
            val rightHindLeg = bone.addChild("right_hind_leg", ModelPartBuilder.create().uv(8, 51).cuboid(-1.0f, 0.0f, -1.0f, 2.0f, 5.0f, 2.0f), ModelTransform.pivot(-2f, 19f, 8f))
            val leftFrontLeg = bone.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 58).cuboid(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f), ModelTransform.pivot(2f, 20f, 3f))
            val rightFrontLeg = bone.addChild("right_front_leg", ModelPartBuilder.create().uv(8, 58).cuboid(-1.0f, 0.0f, -1.0f, 2.0f, 4.0f, 2.0f), ModelTransform.pivot(-2f, 20f, 3f))

            return TexturedModelData.of(modelData, 64, 64)
        }
    }

}