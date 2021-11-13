package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.ModelWithArms
import net.minecraft.client.render.entity.model.ModelWithHead
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

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
    }

    override fun setAngles(entity: DiscipleEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        head.yaw = headYaw * (Math.PI.toFloat() / 180)
        head.pitch = headPitch * (Math.PI.toFloat() / 180)

        if (entity.isOnGround) {
            rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance * 0.5f
            leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + Math.PI.toFloat()) * 1.4f * limbDistance * 0.5f
        } else {
            rightLeg.pitch = MathHelper.lerp(0.01f, rightLeg.pitch, 0.1f)
            leftLeg.pitch = MathHelper.lerp(0.01f, leftLeg.pitch, 0.4f)
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

            val brain1 = head.addChild("brain_1", ModelPartBuilder.create().uv(0, 64).cuboid(-5.0f, 0.0f, -5.0f, 10.0f, 3.0f, 10.0f), ModelTransform.NONE)
            val brain2 = brain1.addChild("brain_2", ModelPartBuilder.create().uv(0, 77).cuboid(0.0f, -2.5f, 0.0f, 6.0f, 3.0f, 8.0f), ModelTransform.of(-0.1f, 1.0f, -4.0f, 0.0f, 0.0f, 0.12f))
            val brain3 = brain1.addChild("brain_3", ModelPartBuilder.create().uv(28, 77).cuboid(-6.0f, -2.5f, 0.0f, 6.0f, 3.0f, 8.0f), ModelTransform.of(0.1f, 1.0f, -4.0f, 0.0f, 0.0f, -0.12f))

            val nose = head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), ModelTransform.pivot(0.0f, -2.0f, 0.0f))

            val body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 20).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).uv(0, 38).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, Dilation(0.5f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val arms = root.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(44, 22).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).uv(40, 38).cuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f))
            arms.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), ModelTransform.NONE)

            val rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-2.0f, 12.0f, 0.0f))
            val leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(2.0f, 12.0f, 0.0f))
            val rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 46).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-5.0f, 2.0f, 0.0f))
            val leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(5.0f, 2.0f, 0.0f))

            return TexturedModelData.of(modelData, 64, 128)
        }
    }

}