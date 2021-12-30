package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.TerminalBlockEntity
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
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Vec3f
import java.util.*

class TerminalBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<TerminalBlockEntity> {

    val power_light: ModelPart

    init {
        val part = context.getLayerModelPart(LCCModelLayers.terminal)
        power_light = part.getChild("power_light")
    }

    override fun render(entity: TerminalBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val vertexConsumer = texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityAlpha)
        val direction = entity.cachedState[Properties.HORIZONTAL_FACING]

        matrices.push()
        matrices.translate(0.5, 0.5, 0.5)
        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(direction.horizontal.plus(2).rem(4).times(90f)))
        matrices.translate(-0.5, -0.625, -0.5005)

        if (entity.session != null) {
            power_light.render(matrices, vertexConsumer, light, overlay, 1f, 1f, 1f, 1f)
        }

        matrices.pop()
    }

    companion object : ClientSpriteRegistryCallback {
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/computer_lighting"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root

            modelPartData.addChild("power_light", ModelPartBuilder.create().uv(0, 0).cuboid(0f, 0f, 0f, 2f, 3f, 1f), ModelTransform.of(2.0f, 2.0f, -1.0f, 0.0f, 0.0f, 0.0f))

            return TexturedModelData.of(modelData, 16, 16)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}
