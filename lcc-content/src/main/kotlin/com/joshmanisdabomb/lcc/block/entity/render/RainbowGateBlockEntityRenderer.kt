package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.RainbowGateBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3f

class RainbowGateBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<RainbowGateBlockEntity> {

    val parts: Array<Array<ModelPart>>

    init {
        val part = context.getLayerModelPart(LCCModelLayers.rainbow_gate)
        parts = Array(6) { i -> Array(4*9*4) { part.getChild("${i}_$it") } }
    }

    override fun render(entity: RainbowGateBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val ticks = entity.sequence ?: return
        val offset = entity.offset ?: return
        val direction = entity.direction ?: return

        matrices.push()
        matrices.translate(direction.offsetX.times(2.0), offset.unaryMinus().plus(1.0), direction.offsetZ.times(2.0))
        matrices.translate(0.5, 0.5, 0.5)
        parts.forEachIndexed { k, v ->
            v.forEachIndexed { k2, v2 ->
                matrices.push()
                val position = entity.shardLastOffsets[k][k2].lerp(entity.shardOffsets[k][k2], tickDelta.toDouble())
                val rotation = entity.shardLastRotations[k][k2].copy()
                rotation.lerp(entity.shardRotations[k][k2], tickDelta)
                matrices.translate(position.x, position.y, position.z)
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(rotation.x))
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation.y))
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rotation.z))
                v2.render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f)
                matrices.pop()
            }
        }
        matrices.pop()
    }

    companion object : ClientSpriteRegistryCallback {
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.entity("rainbow_gate"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            for (i in 0 until 6) {
                for (j in 0 until 4*4*9) {
                    val x = j.rem(29)
                    val y = j.div(29).rem(15)
                    modelPartData.addChild("${i}_$j", ModelPartBuilder.create().uv(x + i.div(4).times(32), y + i.rem(4).times(16)).cuboid(0f, 0f, 0f, 1f, 1f, 1f), ModelTransform.of(0f, 0f, 0f, 0f, 0f, 0f))
                }
            }
            return TexturedModelData.of(modelData, 64, 64)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}