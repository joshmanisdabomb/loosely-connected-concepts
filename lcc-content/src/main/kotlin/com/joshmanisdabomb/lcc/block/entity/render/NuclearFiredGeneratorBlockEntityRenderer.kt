package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.extensions.blockEntityTransform
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
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
import kotlin.math.PI
import kotlin.math.ceil

class NuclearFiredGeneratorBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<NuclearFiredGeneratorBlockEntity> {

    val fuel_empty: Array<ModelPart>
    val fuel_fill: Array<ModelPart>
    val coolant_empty: Array<ModelPart>
    val coolant_fill: Array<ModelPart>

    init {
        val part = context.getLayerModelPart(LCCModelLayers.nuclear_generator)
        fuel_empty = Array(partAmount) { part.getChild("fuel_empty_$it") }
        fuel_fill = Array(partAmount) { part.getChild("fuel_fill_$it") }
        coolant_empty = Array(partAmount) { part.getChild("coolant_empty_$it") }
        coolant_fill = Array(partAmount) { part.getChild("coolant_fill_$it") }
    }

    override fun render(entity: NuclearFiredGeneratorBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val fuelLevel = ceil(entity.fuel.div(NuclearFiredGeneratorBlockEntity.maxFuel).times(11f)).toInt().coerceIn(0, 10)
        val coolantLevel = ceil(entity.coolant.div(NuclearFiredGeneratorBlockEntity.maxCoolant).times(11f)).toInt().coerceIn(0, 10)

        matrices.push()
        if (fuelLevel != 10) fuel_empty[fuelLevel].render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
        if (fuelLevel != 0) fuel_fill[fuelLevel - 1].render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
        for (d in horizontalDirections) {
            matrices.push()
            d.blockEntityTransform(matrices)
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0f))
            matrices.translate(0.0, -1.0, 0.0)
            if (coolantLevel != 10) coolant_empty[coolantLevel].render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
            if (coolantLevel != 0) coolant_fill[coolantLevel - 1].render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
            matrices.pop()
        }
        matrices.pop()
    }

    companion object : ClientSpriteRegistryCallback {
        const val partAmount = 10
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/nuclear_generator"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            for (i in 0 until partAmount) {
                modelPartData.addChild("fuel_empty_$i", ModelPartBuilder.create().uv(0, 0).cuboid(-1f, -5f, -1f, 2f, 10f - i, 2f), ModelTransform.of(8.0f, 8.0f, 8.0f, PI.toFloat(), 0.0f, 0.0f))
                modelPartData.addChild("coolant_empty_$i", ModelPartBuilder.create().uv(16, 0).cuboid(-2f, -5f, -2f, 1f, 10f - i, 1f), ModelTransform.of(8.0f, 8.0f, 8.0f, PI.toFloat(), 0.0f, 0.0f))

                modelPartData.addChild("fuel_fill_$i", ModelPartBuilder.create().uv(8, 0).cuboid(-1f, 4f - i, -1f, 2f, i.plus(1f), 2f), ModelTransform.of(8.0f, 8.0f, 8.0f, PI.toFloat(), 0.0f, 0.0f))
                modelPartData.addChild("coolant_fill_$i", ModelPartBuilder.create().uv(20, 0).cuboid(-2f, 4f - i, -2f, 1f, i.plus(1f), 1f), ModelTransform.of(8.0f, 8.0f, 8.0f, PI.toFloat(), 0.0f, 0.0f))
            }
            return TexturedModelData.of(modelData, 32, 16)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}
