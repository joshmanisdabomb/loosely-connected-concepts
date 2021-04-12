package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.AlarmBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.POWERED
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sign

class AlarmBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<AlarmBlockEntity> {

    val ringerStalk: ModelPart
    val ringerEnd: ModelPart

    init {
        val part = context.getLayerModelPart(LCCModelLayers.alarm)
        ringerStalk = part.getChild("ringer_stalk")
        ringerEnd = part.getChild("ringer_end")
    }

    override fun render(entity: AlarmBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        if (!MinecraftClient.getInstance().isPaused) {
            if (entity.cachedState[POWERED]) {
                entity.ringerValue += entity.ringerVelocity.times(tickDelta)
                if (entity.ringerValue.absoluteValue >= 1f) {
                    entity.ringerValue = entity.ringerValue.sign
                    entity.ringerVelocity = entity.ringerValue.times(-0.001f)
                }
            } else {
                entity.ringerValue *= 0.8f
            }
        }
        matrices.push()
        if (entity.cachedState[HORIZONTAL_FACING].axis == Direction.Axis.Z) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f))
            matrices.translate(-1.0, 0.0, 0.0)
        }
        matrices.push()
        matrices.translate(0.5, 0.25, 0.5)
        matrices.scale(0.6f, 0.6f, 0.6f)
        ringerStalk.pitch = entity.ringerValue.div(2.3f)
        ringerStalk.render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
        matrices.pop()
        matrices.push()
        matrices.translate(0.5, 0.25, 0.5)
        matrices.scale(0.9f, 0.8f, 0.8f)
        ringerEnd.pitch = entity.ringerValue.div(2.3f)
        ringerEnd.render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
        matrices.pop()
        matrices.pop()
    }

    companion object : ClientSpriteRegistryCallback {
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.entity("alarm"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            modelPartData.addChild("ringer_stalk", ModelPartBuilder.create().uv(1, 0).cuboid(-0.5f, -1f, -0.5f, 1f, 7f, 1f), ModelTransform.of(0.0f, 0.0f, 0.0f, PI.toFloat(), 0.0f, 0.0f))
            modelPartData.addChild("ringer_end", ModelPartBuilder.create().uv(0, 9).cuboid(-0.5f, 6.0f.div(0.8f).times(0.6f), -1f, 1f, 1f, 2f), ModelTransform.of(0.0f, 0.0f, 0.0f, PI.toFloat(), 0.0f, 0.0f))
            return TexturedModelData.of(modelData, 16, 16)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}