package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.extensions.square
import com.joshmanisdabomb.lcc.extensions.transform
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.ModelWithArms
import net.minecraft.client.render.entity.model.ModelWithHead
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper

@Environment(EnvType.CLIENT)
class DiscipleEntityModel(private val root: ModelPart) : SinglePartEntityModel<DiscipleEntity>(), ModelWithArms, ModelWithHead {

    private val head: ModelPart
    private val nose: ModelPart
    private val brain1: ModelPart
    private val brain2: ModelPart
    private val brain3: ModelPart
    private val arms: ModelPart
    private val leftLeg: ModelPart
    private val rightLeg: ModelPart
    private val rightArm: ModelPart
    private val leftArm: ModelPart
    private val rightWing: ModelPart
    private val leftWing: ModelPart
    private val rightWingGrounded: ModelPart
    private val leftWingGrounded: ModelPart

    init {
        this.head = root.getChild(EntityModelPartNames.HEAD)
        this.nose = head.getChild(EntityModelPartNames.NOSE)
        this.brain1 = head.getChild("brain_1")
        brain1.visible = false
        this.brain2 = brain1.getChild("brain_2")
        brain2.visible = false
        this.brain3 = brain1.getChild("brain_3")
        brain3.visible = false
        this.arms = root.getChild(EntityModelPartNames.ARMS)
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG)
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG)
        this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM)
        this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM)
        this.leftWing = root.getChild("left_wing")
        this.rightWing = root.getChild("right_wing")
        this.leftWingGrounded = root.getChild("left_wing_grounded")
        this.rightWingGrounded = root.getChild("right_wing_grounded")
    }

    override fun animateModel(entity: DiscipleEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta)

        if (!MinecraftClient.getInstance().isPaused) {
            if (entity.wingAnimation < 0f) entity.wingAnimation = entity.age.toFloat()

            val vel = entity.velocity
            entity.wingAnimation += tickDelta + ((vel.x.square() + vel.y.coerceAtLeast(0.0) + vel.y.coerceAtMost(0.0).square() + vel.z.square()).toFloat().times(20f) * tickDelta)
        }
    }

    override fun setAngles(entity: DiscipleEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        head.yaw = headYaw * (Math.PI.toFloat() / 180)
        head.pitch = headPitch * (Math.PI.toFloat() / 180)

        if (entity.isOnGround) {
            leftWing.visible = false
            rightWing.visible = false
            leftWingGrounded.visible = true
            rightWingGrounded.visible = true

            rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance * 0.5f
            leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + Math.PI.toFloat()) * 1.4f * limbDistance * 0.5f
        } else {
            leftWing.visible = true
            rightWing.visible = true
            leftWingGrounded.visible = false
            rightWingGrounded.visible = false

            rightLeg.pitch = 0.1f
            leftLeg.pitch = 0.3f
        }

        if (entity.target != null) {
            arms.visible = false
            leftArm.visible = true
            rightArm.visible = true
        } else {
            arms.visible = true
            leftArm.visible = false
            rightArm.visible = false
        }

        this.leftWing.roll = (MathHelper.sin(entity.wingAnimation * 0.017453292f) * 3.1415927f * 0.035f)
        this.leftWing.yaw = (MathHelper.cos(entity.wingAnimation * 0.017453292f) * 3.1415927f * 0.1f) + 0.5f

        this.rightWing.roll = -this.leftWing.roll
        this.rightWing.yaw = -this.leftWing.yaw
    }

    fun renderBrainBase(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int) {
        brain1.visible = true
        brain1.render(matrices, vertices, light, overlay)
        brain1.visible = false
    }

    fun renderBrainTop(matrices: MatrixStack, vertices: VertexConsumer, light: Int, overlay: Int) {
        brain2.visible = true
        brain3.visible = true
        brain2.render(matrices, vertices, light, overlay)
        brain3.render(matrices, vertices, light, overlay)
        brain2.visible = false
        brain3.visible = false
    }

    override fun getPart() = root

    override fun setArmAngle(arm: Arm, matrices: MatrixStack) = (arm == Arm.LEFT).transform(leftArm, rightArm).rotate(matrices)

    override fun getHead() = head

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val brain1 = head.addChild("brain_1", ModelPartBuilder.create().uv(64, 0).cuboid(-5.0f, 0.0f, -5.0f, 10.0f, 3.0f, 10.0f), ModelTransform.NONE)
            val brain2 = brain1.addChild("brain_2", ModelPartBuilder.create().uv(64, 13).cuboid(0.0f, -2.5f, 0.0f, 6.0f, 3.0f, 8.0f), ModelTransform.of(-0.1f, 1.0f, -4.0f, 0.0f, 0.0f, 0.12f))
            val brain3 = brain1.addChild("brain_3", ModelPartBuilder.create().uv(92, 13).cuboid(-6.0f, -2.5f, 0.0f, 6.0f, 3.0f, 8.0f), ModelTransform.of(0.1f, 1.0f, -4.0f, 0.0f, 0.0f, -0.12f))

            val nose = head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), ModelTransform.pivot(0.0f, -2.0f, 0.0f))

            val body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 20).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).uv(0, 38).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, Dilation(0.5f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val arms = root.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(44, 22).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).uv(40, 38).cuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f))
            arms.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), ModelTransform.NONE)

            val rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-2.0f, 12.0f, 0.0f))
            val leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(2.0f, 12.0f, 0.0f))
            val rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 46).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-5.0f, 2.0f, 0.0f))
            val leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(5.0f, 2.0f, 0.0f))

            val rightWingGrounded = root.addChild("right_wing_grounded", ModelPartBuilder.create().uv(0, 64).mirrored().cuboid(0.0f, -10.0f, 0.0f, 11.0f, 25.0f, 0.0f, Dilation(0.001f)), ModelTransform.of(0.0f, 4.0f, 3.5f, 0.0f, -0.2f, 0.0f))
            val leftWingGrounded = root.addChild("left_wing_grounded", ModelPartBuilder.create().uv(0, 64).cuboid(-11.0f, -10.0f, 0.0f, 11.0f, 25.0f, 0.0f, Dilation(0.001f)), ModelTransform.of(0.0f, 4.0f, 3.5f, 0.0f, 0.2f, 0.0f))
            val rightWing = root.addChild("right_wing", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(0.0f, -10.0f, 0.0f, 27.0f, 25.0f, 0.0f, Dilation(0.001f)), ModelTransform.of(0.0f, 4.0f, 3.5f, 0.0f, 0.0f, 0.0f))
            val leftWing = root.addChild("left_wing", ModelPartBuilder.create().uv(0, 89).cuboid(-27.0f, -10.0f, 0.0f, 27.0f, 25.0f, 0.0f, Dilation(0.001f)), ModelTransform.of(0.0f, 4.0f, 3.5f, 0.0f, 0.0f, 0.0f))

            return TexturedModelData.of(modelData, 128, 128)
        }
    }

}