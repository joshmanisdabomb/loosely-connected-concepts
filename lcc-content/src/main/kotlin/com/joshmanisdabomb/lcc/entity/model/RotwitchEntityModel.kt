package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.RotwitchEntity
import com.joshmanisdabomb.lcc.extensions.transform
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.*
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.ModelWithArms
import net.minecraft.client.render.entity.model.ModelWithHead
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Arm
import net.minecraft.util.math.MathHelper

@Environment(EnvType.CLIENT)
class RotwitchEntityModel(private val root: ModelPart) : SinglePartEntityModel<RotwitchEntity>(), ModelWithArms, ModelWithHead {

    private val bone: ModelPart
    private val head: ModelPart
    private val hat: ModelPart
    private val body: ModelPart
    private val leftLeg: ModelPart
    private val rightLeg: ModelPart
    private val rightArm: ModelPart
    private val leftArm: ModelPart

    init {
        this.bone = root.getChild("bone")
        this.head = bone.getChild(EntityModelPartNames.HEAD)
        this.hat = head.getChild(EntityModelPartNames.HAT)
        this.body = bone.getChild(EntityModelPartNames.BODY)
        this.leftLeg = bone.getChild(EntityModelPartNames.LEFT_LEG)
        this.rightLeg = bone.getChild(EntityModelPartNames.RIGHT_LEG)
        this.leftArm = bone.getChild(EntityModelPartNames.LEFT_ARM)
        this.rightArm = bone.getChild(EntityModelPartNames.RIGHT_ARM)
    }

    override fun animateModel(entity: RotwitchEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta)
    }

    override fun setAngles(entity: RotwitchEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        head.yaw = MathHelper.sin(MathHelper.clampAngle(headYaw, 0f, 180f).div(180f).times(Math.PI.toFloat()))

        head.pitch = 0.3f
        rightArm.pitch = 3.6f + MathHelper.cos(limbAngle.times(0.4f)).times(0.45f)
        rightArm.yaw = -1.75f + MathHelper.sin(limbAngle.minus(0.4f).times(0.7f)).times(0.15f)
        rightArm.roll = -2.35f + MathHelper.cos(limbAngle.minus(0.7f).times(0.2f)).times(0.45f)
        leftArm.pitch = -1.4f + MathHelper.sin(limbAngle.times(limbDistance.coerceAtMost(1f)).times(0.3f)).times(0.15f) + MathHelper.sin(animationProgress.times(0.1f)).times(0.04f)
        leftArm.yaw = -0.2f + MathHelper.cos(limbAngle.minus(0.4f)).times(0.15f) + MathHelper.cos(animationProgress.times(0.1f)).times(0.04f)
        leftArm.roll = -0.1f + MathHelper.sin(limbAngle.minus(0.7f)).times(0.25f)
        rightLeg.pitch = -1.12f + MathHelper.sin(limbAngle.times(0.4f)).times(0.45f) - MathHelper.cos(limbAngle.times(limbDistance.coerceAtMost(2f)).times(0.15f)).times(0.4f)
        rightLeg.yaw = -0.2f + MathHelper.sin(limbAngle.minus(0.1f).times(0.5f)).times(0.15f) + MathHelper.cos(animationProgress.times(0.1f)).times(0.04f)
        rightLeg.roll = 0.2f + MathHelper.cos(limbAngle.minus(0.4f).times(0.5f)).times(0.45f) - MathHelper.cos(animationProgress.times(0.2f)).times(0.05f)
        leftLeg.pitch = 0.0f + MathHelper.sin(limbAngle.times(limbDistance.coerceAtMost(1f)).times(0.3f)).times(0.05f)
        leftLeg.yaw = 0.1f + MathHelper.sin(limbAngle.minus(0.4f).times(0.7f)).times(0.15f)
        leftLeg.roll = -0.1f + MathHelper.sin(limbAngle.minus(0.7f).times(0.2f)).times(0.45f)
    }

    override fun getPart() = root

    override fun setArmAngle(arm: Arm, matrices: MatrixStack) = (arm == Arm.LEFT).transform(leftArm, rightArm).rotate(matrices)

    override fun getHead() = head

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val bone = root.addChild("bone", ModelPartBuilder.create(), ModelTransform.of(0.0f, 24.0f, 0.0f, Math.PI.toFloat().div(-2f), 0.0f, 0.0f))

            val head = bone.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f), ModelTransform.of(0.0f, -8.0f, -2.0f, 0.0f, 0.0f, 0.0f))
            val hat = head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, Dilation(0.5f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val body = bone.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()
                .uv(46, 16).cuboid(3.0F, -5.0F, -4.0F, 1.0F, 6.0F, 2.0F)
                .uv(52, 26).cuboid(-3.0F, -5.0F, -4.0F, 1.0F, 1.0F, 2.0F)
                .uv(52, 23).cuboid(0.0F, -5.0F, -4.0F, 3.0F, 1.0F, 2.0F)
                .uv(58, 20).cuboid(2.0F, 0.0F, -4.0F, 1.0F, 1.0F, 2.0F)
                .uv(52, 20).cuboid(-1.0F, 0.0F, -4.0F, 1.0F, 1.0F, 2.0F)
                .uv(52, 16).cuboid(-3.0F, -1.0F, -4.0F, 2.0F, 2.0F, 2.0F)
                .uv(48, 30).cuboid(-2.0F, -2.0F, -3.0F, 1.0F, 1.0F, 1.0F)
                .uv(44, 30).cuboid(-1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F)
                .uv(44, 28).cuboid(0.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F)
                .uv(40, 28).cuboid(2.0F, -3.0F, -3.0F, 1.0F, 3.0F, 1.0F)
                .uv(44, 26).cuboid(0.0F, -4.0F, -3.0F, 3.0F, 1.0F, 1.0F)
                .uv(44, 24).cuboid(-2.0F, -5.0F, -3.0F, 2.0F, 1.0F, 1.0F)
                .uv(40, 24).cuboid(-3.0F, -4.0F, -3.0F, 1.0F, 3.0F, 1.0F)
                .uv(40, 16).cuboid(-4.0F, -5.0F, -4.0F, 1.0F, 6.0F, 2.0F)
                .uv(20, 21).cuboid(-4.0F, 1.0F, -4.0F, 8.0F, 3.0F, 2.0F)
                .uv(20, 16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 3.0F, 2.0F)
                .uv(0, 16).cuboid(-4.0F, -8.0F, -2.0F, 8.0F, 12.0F, 2.0F)
            , ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val rightLeg = bone.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create()
                .uv(32, 47).cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F)
                .uv(32, 41).cuboid(-2.0F, 5.0F, 0.0F, 4.0F, 1.0F, 2.0F)
                .uv(32, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F)
                .uv(32, 44).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 1.0F, 2.0F)
                .uv(16, 48).cuboid(-1.0F, 1.0F, -1.0F, 2.0F, 10.0F, 2.0F)
            , ModelTransform.pivot(-2.0f, 4.0f, -2.0f))
            val leftLeg = bone.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create()
                .uv(48, 46).cuboid(-2.0F, 6.0F, 0.0F, 2.0F, 1.0F, 2.0F)
                .uv(48, 41).cuboid(-2.0F, 5.0F, -2.0F, 2.0F, 1.0F, 4.0F)
                .uv(48, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F)
                .uv(24, 48).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F)
            , ModelTransform.pivot(2.0f, 4.0f, -2.0f))
            val rightArm = bone.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create()
                .uv(8, 48).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 11.0F, 2.0F)
                .uv(0, 32).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 3.0F, 4.0F)
            , ModelTransform.pivot(-6.0f, -6.0f, -2.0f))
            val leftArm = bone.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create()
                .uv(0, 48).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 11.0F, 2.0F)
                .uv(16, 42).cuboid(0.0F, 4.0F, -2.0F, 2.0F, 3.0F, 2.0F)
                .uv(16, 32).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F)
            , ModelTransform.pivot(6.0f, -6.0f, -2.0f))

            return TexturedModelData.of(modelData, 64, 64)
        }
    }

}