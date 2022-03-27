package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
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
import java.util.*
import kotlin.math.pow

@Environment(EnvType.CLIENT)
class PsychoPigEntityModel(private val root: ModelPart) : SinglePartEntityModel<PsychoPigEntity>(), ModelWithArms, ModelWithHead {

    private val head: ModelPart
    private val teeth: Array<ModelPart>
    private val body: ModelPart
    private val rightHindLeg: ModelPart
    private val leftHindLeg: ModelPart
    private val rightFrontLeg: ModelPart
    private val leftFrontLeg: ModelPart

    init {
        head = root.getChild(EntityModelPartNames.HEAD)
        teeth = Array(teethAmount) { head.getChild("teeth_$it") }
        body = root.getChild(EntityModelPartNames.BODY)
        rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG)
        leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG)
        rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG)
        leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG)
    }

    override fun animateModel(entity: PsychoPigEntity, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta)
    }

    override fun setAngles(entity: PsychoPigEntity, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        head.yaw = headYaw * (Math.PI.toFloat() / 180)
        head.pitch = headPitch * (Math.PI.toFloat() / 180)
        rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + Math.PI.toFloat()) * 1.4f * limbDistance
        leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance
        if (!entity.mainHandStack.isEmpty) {
            if (entity.isLeftHanded) {
                leftFrontLeg.pitch = -Math.PI.toFloat().div(2f)
            } else {
                rightFrontLeg.pitch = -Math.PI.toFloat().div(2f)
            }
        }
        rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance
        leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + Math.PI.toFloat()) * 1.4f * limbDistance
    }

    override fun getPart() = root

    override fun setArmAngle(arm: Arm, matrices: MatrixStack) = rightFrontLeg.rotate(matrices)

    override fun getHead() = head

    companion object {
        val teethAmount = 48

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f)
                .uv(16, 16).cuboid(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f)
                .uv(0, 0).cuboid(-3.1f, -0.6f, -8.01f, 0.2f, 0.2f, 0.2f)
                .uv(0, 0).cuboid(2.9f, -0.6f, -8.01f, 0.2f, 0.2f, 0.2f)
            , ModelTransform.pivot(0.0f, 2.0f, -2.5f))

            val rand = Random()
            var toothLength = 0.8f
            for (i in 0 until teethAmount) {
                val dist = i.div(4)
                val column = i.div(2).rem(2)
                val distMod = column.times(2).minus(1)
                val top = i.plus(1).rem(2)

                val smileLength = 1.8f.times(1 - dist.plus(1f).div(teethAmount.div(4).plus(1)).times(0.7f))
                val smileCurve = dist.plus(1f).div(teethAmount.div(4).plus(1)).pow(4f).times(3.0f)
                if (i % 2 == 0) toothLength = rand.nextFloat().times(0.2f).plus(0.4f).times(smileLength)

                head.addChild("teeth_$i", ModelPartBuilder.create()
                    .uv(0, 1).cuboid(0.0f, -top.times(smileLength.minus(toothLength)), -0.15f, 0.3f, (top == 0).transform(toothLength, smileLength.minus(toothLength)), 0.3f)
                , ModelTransform.of(0.15f.unaryMinus().plus(dist.times(0.3f).plus(0.15f).times(distMod)), 1.8f.minus(i.rem(2).times(smileLength)).plus(2.0f).minus(smileCurve), -8.0f, rand.nextFloat().times(0.2f).minus(0.1f), 0.0f, 0.0f))
            }

            val body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(28, 8).cuboid(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f), ModelTransform.of(0.0f, 11.0f, 2.0f, 0.3f, 0.0f, 0.0f))

            val rightHindLeg = root.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f), ModelTransform.pivot(-3.001f, 18.0f, 0.0f))
            val leftHindLeg = root.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f), ModelTransform.pivot(3.001f, 18.0f, 0.0f))
            val rightFrontLeg = root.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f), ModelTransform.pivot(-2.999f, 6.0f, -7.0f))
            val leftFrontLeg = root.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f), ModelTransform.pivot(2.999f, 6.0f, -7.0f))

            return TexturedModelData.of(modelData, 64, 32)
        }
    }

}