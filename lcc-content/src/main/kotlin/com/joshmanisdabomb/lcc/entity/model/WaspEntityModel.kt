package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.WaspEntity
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import kotlin.math.abs

class WaspEntityModel<T : WaspEntity>(root: ModelPart) : EntityModel<T>(RenderLayer::getEntityTranslucent) {

    private val bone: ModelPart
    private val head: ModelPart
    private val mandibles: ModelPart
    private val mandible_left: ModelPart
    private val mandible_right: ModelPart
    private val antennae: ModelPart
    private val antenna_left: ModelPart
    private val antenna_right: ModelPart
    private val torso: ModelPart
    private val wing_left: ModelPart
    private val wing_right: ModelPart
    private val leg_left_front_pivot: ModelPart
    private val leg_left_middle_pivot: ModelPart
    private val leg_left_back_pivot: ModelPart
    private val leg_right_front_pivot: ModelPart
    private val leg_right_middle_pivot: ModelPart
    private val leg_right_back_pivot: ModelPart
    private val leg_left_front: ModelPart
    private val leg_left_middle: ModelPart
    private val leg_left_back: ModelPart
    private val leg_right_front: ModelPart
    private val leg_right_middle: ModelPart
    private val leg_right_back: ModelPart
    private val rear1: ModelPart
    private val rear2: ModelPart
    private val rear3: ModelPart
    private val rear4: ModelPart
    private val stinger: ModelPart

    private val spine: List<ModelPart>
    private val leftLegs: List<ModelPart>
    private val rightLegs: List<ModelPart>
    private val legs: List<ModelPart>
    private val leftLegPivots: List<ModelPart>
    private val rightLegPivots: List<ModelPart>
    private val legPivots: List<ModelPart>

    init {
        this.bone = root.getChild("bone")
        this.head = this.bone.getChild("head")
        this.mandibles = this.head.getChild("mandibles")
        this.mandible_left = mandibles.getChild("mandible_left")
        this.mandible_right = mandibles.getChild("mandible_right")
        this.antennae = this.head.getChild("antennae")
        this.antenna_left = antennae.getChild("antenna_left")
        this.antenna_right = antennae.getChild("antenna_right")
        this.torso = this.bone.getChild("torso")
        this.wing_left = this.torso.getChild("wing_left")
        this.wing_right = this.torso.getChild("wing_right")
        this.leg_left_front_pivot = this.torso.getChild("leg_pivot_left_front")
        this.leg_left_middle_pivot = this.torso.getChild("leg_pivot_left_middle")
        this.leg_left_back_pivot = this.torso.getChild("leg_pivot_left_back")
        this.leg_right_front_pivot = this.torso.getChild("leg_pivot_right_front")
        this.leg_right_middle_pivot = this.torso.getChild("leg_pivot_right_middle")
        this.leg_right_back_pivot = this.torso.getChild("leg_pivot_right_back")
        this.leg_left_front = this.leg_left_front_pivot.getChild("leg_left_front")
        this.leg_left_middle = this.leg_left_middle_pivot.getChild("leg_left_middle")
        this.leg_left_back = this.leg_left_back_pivot.getChild("leg_left_back")
        this.leg_right_front = this.leg_right_front_pivot.getChild("leg_right_front")
        this.leg_right_middle = this.leg_right_middle_pivot.getChild("leg_right_middle")
        this.leg_right_back = this.leg_right_back_pivot.getChild("leg_right_back")
        this.rear1 = this.torso.getChild("rear_1")
        this.rear2 = this.rear1.getChild("rear_2")
        this.rear3 = this.rear2.getChild("rear_3")
        this.rear4 = this.rear3.getChild("rear_4")
        this.stinger = this.rear4.getChild("stinger")

        this.spine = listOf(torso, rear1, rear2, rear3, rear4, stinger)
        this.leftLegs = listOf(leg_left_front, leg_left_middle, leg_left_back)
        this.rightLegs = listOf(leg_right_front, leg_right_middle, leg_right_back)
        this.leftLegPivots = listOf(leg_left_front_pivot, leg_left_middle_pivot, leg_left_back_pivot)
        this.rightLegPivots = listOf(leg_right_front_pivot, leg_right_middle_pivot, leg_right_back_pivot)
        this.legs = leftLegs + rightLegs
        this.legPivots = leftLegPivots + rightLegPivots
    }

    override fun render(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
        matrices.push()
        if (this.child) {
            matrices.translate(0.0, 0.2, 0.25)
        }
        this.head.render(matrices, vertices, light, overlay)
        if (this.child) {
            matrices.translate(0.0, 0.55, -0.15)
            matrices.scale(0.5f, 0.5f, 0.5f)
        }

        this.wing_left.visible = !this.child
        this.wing_right.visible = !this.child

        this.torso.render(matrices, vertices, light, overlay)
        matrices.pop()
    }

    override fun animateModel(entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        if (entity.isOnGround) {
            this.torso.pitch = -0.1f
            this.rear1.pitch = -0.1f
            this.rear2.pitch = 0f
            this.rear3.pitch = 0f
            this.rear4.pitch = 0f
            this.stinger.pitch = 0f
            groundedLegRoll.forEachIndexed { k, v ->
                leftLegPivots[k].roll = v
                leftLegPivots[k].pitch = -this.torso.pitch.div(3-k)
                rightLegPivots[k].roll = -v
                rightLegPivots[k].pitch = -this.torso.pitch.div(3-k)
            }
            leg_left_front.pitch = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 0.0f) * 0.5f) * limbDistance * 2f
            leg_left_front.roll = abs(MathHelper.sin(limbAngle * 0.6662f + 0.0f) * 0.5f) * limbDistance * 2f
            leg_left_middle.pitch = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 3.1415927f.times(0.66f)) * 0.5f) * limbDistance * 2f
            leg_left_middle.roll = abs(MathHelper.sin(limbAngle * 0.6662f + 3.1415927f.times(0.66f)) * 0.5f) * limbDistance * 2f
            leg_left_back.pitch = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 3.1415927f.times(1.33f)) * 0.5f) * limbDistance * 2f
            leg_left_back.roll = abs(MathHelper.sin(limbAngle * 0.6662f + 3.1415927f.times(1.33f)) * 0.5f) * limbDistance * 2f
            leg_right_front.pitch = -leg_left_front.pitch
            leg_right_front.roll = -leg_left_front.roll
            leg_right_middle.pitch = -leg_left_middle.pitch
            leg_right_middle.roll = -leg_left_middle.roll
            leg_right_back.pitch = -leg_left_back.pitch
            leg_right_back.roll = -leg_left_back.roll
        } else {
            this.legs.forEach { it.roll = 0f; it.pitch = limbDistance.times(0.5f) }
            this.leftLegPivots.forEachIndexed { k, v -> v.roll = 0.4f; v.pitch = -this.torso.pitch.div(3-k) }
            this.rightLegPivots.forEachIndexed { k, v -> v.roll = -0.4f; v.pitch = -this.torso.pitch.div(3-k) }

            val sting = MathHelper.lerp(tickDelta, entity.lastStingAnimation, entity.stingAnimation)
            this.torso.pitch = -0.3f - 0.6f.times(sting)
            this.rear1.pitch = -0.3f - 0.6f.times(sting)
            this.rear2.pitch = (-0.3f).times(sting)
            this.rear3.pitch = (-0.3f).times(sting)
            this.rear4.pitch = (-0.3f).times(sting)
            this.stinger.pitch = (-0.3f).times(sting)
        }
    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        this.head.pitch = headPitch.times(0.017453292f).times(0.5f)
        this.head.yaw = headYaw.times(0.017453292f).times(0.5f)

        this.antennae.pitch = 0.05f + limbDistance.times(0.2f)
        this.mandibles.pitch = 0.3f
        this.mandibles.yaw = 0f
        this.mandible_left.yaw = 0.2f + MathHelper.sin(animationProgress).times(0.6f)
        this.mandible_right.yaw = -this.mandible_left.yaw

        leftLegPivots.forEachIndexed { k, v -> v.yaw = -0.6f + k.times(0.6f) }
        rightLegPivots.forEachIndexed { k, v -> v.yaw = 0.6f - k.times(0.6f) }

        if (entity.isOnGround) {
            this.wing_left.pitch = 0f
            this.wing_left.roll = -1.5f
        } else {
            this.wing_left.pitch = (MathHelper.sin(animationProgress * 74.48451f * 0.017453292f) * 3.1415927f * 0.15f)
            this.wing_left.roll = (MathHelper.cos(animationProgress * 74.48451f * 0.017453292f) * 3.1415927f * 0.15f) - 0.9f
        }
        this.wing_right.pitch = this.wing_left.pitch
        this.wing_right.roll = -this.wing_left.roll
    }

    companion object {
        private val groundedLegRoll = listOf(0.15f, 0.4f, 0.68f)

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root
            val bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 19.0f, 0.0f))
            val head = bone.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0f, -2.0f, -5.0f, 4.0f, 4.0f, 5.0f), ModelTransform.pivot(0f, 17f, -4f))

            val mandibles = head.addChild("mandibles", ModelPartBuilder.create(), ModelTransform.pivot(0f, 2f, -4.5f))
            mandibles.addChild("mandible_left", ModelPartBuilder.create().uv(6, 33).cuboid(-0.5f, 0f, -2f, 1.0f, 1.0f, 2.0f), ModelTransform.pivot(-1.5f, -0.25f, 0f))
            mandibles.addChild("mandible_right", ModelPartBuilder.create().uv(6, 33).cuboid(-0.5f, 0f, -2f, 1.0f, 1.0f, 2.0f), ModelTransform.pivot(1.5f, -0.25f, 0f))

            val antennae = head.addChild("antennae", ModelPartBuilder.create(), ModelTransform.pivot(0f, -2f, -3.5f))
            antennae.addChild("antenna_left", ModelPartBuilder.create().uv(60, 0).cuboid(-0.5f, -3f, -0.5f, 1.0f, 3.0f, 1.0f).uv(56, 4).cuboid(-0.5f, -3f, -3.5f, 1.0f, 1.0f, 3.0f), ModelTransform.pivot(1.2f, 0f, -0.5f))
            antennae.addChild("antenna_right", ModelPartBuilder.create().uv(60, 0).cuboid(-0.5f, -3f, -0.5f, 1.0f, 3.0f, 1.0f).uv(56, 4).cuboid(-0.5f, -3f, -3.5f, 1.0f, 1.0f, 3.0f), ModelTransform.pivot(-1.2f, 0f, -0.5f))

            val torso = bone.addChild("torso", ModelPartBuilder.create().uv(0, 9).cuboid(-2.5f, -0.75f, 0f, 5.0f, 5.0f, 7.0f), ModelTransform.pivot(0f, 15.25f, -4f))
            val wingDilation = Dilation(0.003f)
            torso.addChild("wing_left", ModelPartBuilder.create().uv(12, 47).cuboid(0f, -11.0f, -3.0f, 0.0f, 11.0f, 6.0f, wingDilation), ModelTransform.pivot(-2f, 0f, 3.5f))
            torso.addChild("wing_right", ModelPartBuilder.create().uv(0, 47).cuboid(0f, -11.0f, -3.0f, 0.0f, 11.0f, 6.0f, wingDilation), ModelTransform.pivot(2f, 0f, 3.5f))

            val leg_pivot_left_front = torso.addChild("leg_pivot_left_front", ModelPartBuilder.create(), ModelTransform.pivot(-2.5f, 3f, 1.5f))
            val leg_pivot_left_middle = torso.addChild("leg_pivot_left_middle", ModelPartBuilder.create(), ModelTransform.pivot(-2.5f, 3f, 3.5f))
            val leg_pivot_left_back = torso.addChild("leg_pivot_left_back", ModelPartBuilder.create(), ModelTransform.pivot(-2.5f, 3f, 5.5f))
            val leg_pivot_right_front = torso.addChild("leg_pivot_right_front", ModelPartBuilder.create(), ModelTransform.pivot(2.5f, 3f, 1.5f))
            val leg_pivot_right_middle = torso.addChild("leg_pivot_right_middle", ModelPartBuilder.create(), ModelTransform.pivot(2.5f, 3f, 3.5f))
            val leg_pivot_right_back = torso.addChild("leg_pivot_right_back", ModelPartBuilder.create(), ModelTransform.pivot(2.5f, 3f, 5.5f))

            leg_pivot_left_front.addChild("leg_left_front", ModelPartBuilder.create().uv(0, 36).cuboid(-3.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(0, 38).cuboid(-3.5f, 0.5f, -0.5f, 1.0f, 6.0f, 1.0f), ModelTransform.NONE)
            leg_pivot_left_middle.addChild("leg_left_middle", ModelPartBuilder.create().uv(10, 36).cuboid(-3.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(10, 38).cuboid(-3.5f, 0.5f, -0.5f, 1.0f, 7.0f, 1.0f), ModelTransform.NONE)
            leg_pivot_left_back.addChild("leg_left_back", ModelPartBuilder.create().uv(20, 36).cuboid(-3.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(20, 38).cuboid(-3.5f, 0.5f, -0.5f, 1.0f, 8.0f, 1.0f), ModelTransform.NONE)
            leg_pivot_right_front.addChild("leg_right_front", ModelPartBuilder.create().uv(0, 36).cuboid(-0.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(0, 38).cuboid(2.5f, 0.5f, -0.5f, 1.0f, 6.0f, 1.0f), ModelTransform.NONE)
            leg_pivot_right_middle.addChild("leg_right_middle", ModelPartBuilder.create().uv(10, 36).cuboid(-0.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(10, 38).cuboid(2.5f, 0.5f, -0.5f, 1.0f, 7.0f, 1.0f), ModelTransform.NONE)
            leg_pivot_right_back.addChild("leg_right_back", ModelPartBuilder.create().uv(20, 36).cuboid(-0.5f, -0.5f, -0.5f, 4.0f, 1.0f, 1.0f).uv(20, 38).cuboid(2.5f, 0.5f, -0.5f, 1.0f, 8.0f, 1.0f), ModelTransform.NONE)

            val rear1 = torso.addChild("rear_1", ModelPartBuilder.create().uv(0, 21).cuboid(-3.0f, -0.75f, 0.0f, 6.0f, 6.0f, 6.0f), ModelTransform.pivot(0f, -0.5f, 7f))
            val rear2 = rear1.addChild("rear_2", ModelPartBuilder.create().uv(24, 21).cuboid(-2.5f, -2.5f, 0.0f, 5.0f, 5.0f, 1.0f), ModelTransform.pivot(0f, 2.75f, 6f))
            val rear3 = rear2.addChild("rear_3", ModelPartBuilder.create().uv(36, 21).cuboid(-2.0f, -2f, 0.0f, 4.0f, 4.0f, 1.0f), ModelTransform.pivot(0f, 0.5f, 1f))
            val rear4 = rear3.addChild("rear_4", ModelPartBuilder.create().uv(46, 21).cuboid(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 1.0f), ModelTransform.pivot(0f, 0.5f, 1f))
            rear4.addChild("stinger", ModelPartBuilder.create().uv(0, 33).cuboid(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 2.0f), ModelTransform.pivot(0f, 0.5f, 1f))

            return TexturedModelData.of(modelData, 64, 64)
        }
    }

}