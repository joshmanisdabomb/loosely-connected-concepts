package com.joshmanisdabomb.lcc.entity.model

import com.google.common.collect.ImmutableList
import com.joshmanisdabomb.lcc.entity.WaspEntity
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.model.AnimalModel

class WaspEntityModel<T : WaspEntity>(root: ModelPart) : AnimalModel<T>(RenderLayer::getEntityTranslucent, false, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f) {

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
        this.leg_left_front = this.torso.getChild("leg_left_front")
        this.leg_left_middle = this.torso.getChild("leg_left_middle")
        this.leg_left_back = this.torso.getChild("leg_left_back")
        this.leg_right_front = this.torso.getChild("leg_right_front")
        this.leg_right_middle = this.torso.getChild("leg_right_middle")
        this.leg_right_back = this.torso.getChild("leg_right_back")
        this.rear1 = this.torso.getChild("rear_1")
        this.rear2 = this.rear1.getChild("rear_2")
        this.rear3 = this.rear2.getChild("rear_3")
        this.rear4 = this.rear3.getChild("rear_4")
        this.stinger = this.rear4.getChild("stinger")
    }

    override fun animateModel(entity: T, limbAngle: Float, limbDistance: Float, tickDelta: Float) {
        //this.head.pitch += tickDelta.times(0.01f)
        //this.head.yaw += tickDelta.times(0.01f)
        /*this.antenna_left.yaw += tickDelta.times(0.01f)
        this.antenna_right.yaw -= tickDelta.times(0.01f)*/

        //this.torso.pitch += tickDelta.times(0.0004f)
        //this.torso.yaw += tickDelta.times(0.001f)
        //this.head.pitch += tickDelta.times(0.001f)

        /*
        this.torso.pitch = -0.9f
        this.rear1.pitch = -0.9f
        this.rear2.pitch = -0.3f
        this.rear3.pitch = -0.3f
        this.rear4.pitch = -0.3f
        this.stinger.pitch = -0.3f*/
    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
        this.head.pitch = headPitch * 0.017453292f
        this.head.yaw = headYaw * 0.017453292f

        this.antennae.pitch = 0.15f
        this.mandibles.pitch = 0.3f

        //this.torso.pitch = -0.9f
        //this.rear1.pitch = -0.9f

        this.leg_left_front.roll = 0.4f
        this.leg_left_middle.roll = 0.4f
        this.leg_left_back.roll = 0.4f
        this.leg_right_front.roll = -0.4f
        this.leg_right_middle.roll = -0.4f
        this.leg_right_back.roll = -0.4f

        //this.leg_left_middle.pitch = -this.torso.pitch.div(2)
    }

    override fun getHeadParts(): Iterable<ModelPart> = ImmutableList.of(head)

    override fun getBodyParts(): Iterable<ModelPart> = ImmutableList.of(torso)

    companion object {
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

            torso.addChild("leg_left_front", ModelPartBuilder.create().uv(0, 36).cuboid(-3.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(0, 38).cuboid(-3.5f, 0.5f, 0f, 1.0f, 6.0f, 1.0f), ModelTransform.pivot(-2.5f, 2f, 1f))
            torso.addChild("leg_left_middle", ModelPartBuilder.create().uv(10, 36).cuboid(-3.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(10, 38).cuboid(-3.5f, 0.5f, 0f, 1.0f, 7.0f, 1.0f), ModelTransform.pivot(-2.5f, 2f, 3f))
            torso.addChild("leg_left_back", ModelPartBuilder.create().uv(20, 36).cuboid(-3.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(20, 38).cuboid(-3.5f, 0.5f, 0f, 1.0f, 8.0f, 1.0f), ModelTransform.pivot(-2.5f, 2f, 5f))
            torso.addChild("leg_right_front", ModelPartBuilder.create().uv(0, 36).cuboid(0.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(0, 38).cuboid(2.5f, 0.5f, 0f, 1.0f, 6.0f, 1.0f), ModelTransform.pivot(2.5f, 2f, 1f))
            torso.addChild("leg_right_middle", ModelPartBuilder.create().uv(10, 36).cuboid(-0.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(10, 38).cuboid(2.5f, 0.5f, 0f, 1.0f, 7.0f, 1.0f), ModelTransform.pivot(2.5f, 2f, 3f))
            torso.addChild("leg_right_back", ModelPartBuilder.create().uv(20, 36).cuboid(-0.5f, -0.5f, 0f, 4.0f, 1.0f, 1.0f).uv(20, 38).cuboid(2.5f, 0.5f, 0f, 1.0f, 8.0f, 1.0f), ModelTransform.pivot(2.5f, 2f, 5f))

            val rear1 = torso.addChild("rear_1", ModelPartBuilder.create().uv(0, 21).cuboid(-3.0f, -0.75f, 0.0f, 6.0f, 6.0f, 6.0f), ModelTransform.pivot(0f, -0.5f, 7f))
            val rear2 = rear1.addChild("rear_2", ModelPartBuilder.create().uv(24, 21).cuboid(-2.5f, -2.5f, 0.0f, 5.0f, 5.0f, 1.0f), ModelTransform.pivot(0f, 2.75f, 6f))
            val rear3 = rear2.addChild("rear_3", ModelPartBuilder.create().uv(36, 21).cuboid(-2.0f, -2f, 0.0f, 4.0f, 4.0f, 1.0f), ModelTransform.pivot(0f, 0.5f, 1f))
            val rear4 = rear3.addChild("rear_4", ModelPartBuilder.create().uv(46, 21).cuboid(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 1.0f), ModelTransform.pivot(0f, 0.5f, 1f))
            rear4.addChild("stinger", ModelPartBuilder.create().uv(0, 33).cuboid(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 2.0f), ModelTransform.pivot(0f, 0.5f, 1f))

            return TexturedModelData.of(modelData, 64, 64)
        }
    }

}