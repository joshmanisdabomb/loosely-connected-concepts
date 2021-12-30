package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
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
import java.util.*

class ComputingBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<ComputingBlockEntity> {

    val rand = Random()

    val power_light: ModelPart
    val error_light: ModelPart
    val read_light: ModelPart

    init {
        val part = context.getLayerModelPart(LCCModelLayers.computing)
        power_light = part.getChild("power_light")
        error_light = part.getChild("error_light")
        read_light = part.getChild("read_light")
    }

    override fun render(entity: ComputingBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        for (half in entity.getHalves()) {
            val computer = half.module as? ComputerComputerModule ?: continue
            val code = computer.getCurrentErrorCode(half) ?: continue

            val vertexConsumer = texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityAlpha)

            matrices.push()
            if (half.top) matrices.translate(0.0, 0.5625, 0.0)
            matrices.translate(0.5, 0.5, 0.5)
            matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(half.direction.horizontal.plus(2).rem(4).times(90f)))
            matrices.translate(-0.5, -0.5, -0.5005)

            if (code > 0) {
                if (System.currentTimeMillis().rem(2000) > 1000) error_light.render(matrices, vertexConsumer, light, overlay, 1f, 1f, 1f, 1f)
            } else if (computer.isReading(half) && rand.nextBoolean()) {
                read_light.render(matrices, vertexConsumer, light, overlay, 1f, 1f, 1f, 1f)
            } else {
                power_light.render(matrices, vertexConsumer, light, overlay, 1f, 1f, 1f, 1f)
            }

            matrices.pop()
        }
    }

    companion object : ClientSpriteRegistryCallback {
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/computer_lighting"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root

            modelPartData.addChild("power_light", ModelPartBuilder.create().uv(0, 0).cuboid(0f, 0f, 0f, 2f, 3f, 1f), ModelTransform.of(2.0f, 2.0f, -1.0f, 0.0f, 0.0f, 0.0f))
            modelPartData.addChild("error_light", ModelPartBuilder.create().uv(0, 4).cuboid(0f, 0f, 0f, 2f, 3f, 1f), ModelTransform.of(2.0f, 2.0f, -1.0f, 0.0f, 0.0f, 0.0f))
            modelPartData.addChild("read_light", ModelPartBuilder.create().uv(0, 8).cuboid(0f, 0f, 0f, 2f, 3f, 1f), ModelTransform.of(2.0f, 2.0f, -1.0f, 0.0f, 0.0f, 0.0f))

            return TexturedModelData.of(modelData, 16, 16)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}
