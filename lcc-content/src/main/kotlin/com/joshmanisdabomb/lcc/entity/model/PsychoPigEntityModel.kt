package com.joshmanisdabomb.lcc.entity.model

import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.*
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.ModelWithArms
import net.minecraft.client.render.entity.model.ModelWithHead
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Arm

@Environment(EnvType.CLIENT)
class PsychoPigEntityModel(private val root: ModelPart) : SinglePartEntityModel<PsychoPigEntity>(), ModelWithArms, ModelWithHead {

    private val head: ModelPart
    private val body: ModelPart
    private val rightHindLeg: ModelPart
    private val leftHindLeg: ModelPart
    private val rightFrontLeg: ModelPart
    private val leftFrontLeg: ModelPart

    init {
        head = root.getChild(EntityModelPartNames.HEAD)
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
    }

    override fun getPart() = root

    override fun setArmAngle(arm: Arm, matrices: MatrixStack) = rightHindLeg.rotate(matrices)

    override fun getHead() = head

    companion object {
        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root

            val head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f).uv(16, 16).cuboid(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f), ModelTransform.pivot(0.0f, 12.0f, -6.0f))

            val body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(28, 8).cuboid(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f), ModelTransform.of(0.0f, 11.0f, 2.0f, 1.5707964f, 0.0f, 0.0f))

            val rightHindLeg = root.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.toFloat(), 4.0f), ModelTransform.pivot(-3.0f, 18.0f, 7.0f))
            val leftHindLeg = root.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.toFloat(), 4.0f), ModelTransform.pivot(3.0f, 18.0f, 7.0f))
            val rightFrontLeg = root.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.toFloat(), 4.0f), ModelTransform.pivot(-3.0f, 18.0f, -5.0f))
            val leftFrontLeg = root.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.toFloat(), 4.0f), ModelTransform.pivot(3.0f, 18.0f, -5.0f))

            return TexturedModelData.of(modelData, 64, 32)
        }
    }

}