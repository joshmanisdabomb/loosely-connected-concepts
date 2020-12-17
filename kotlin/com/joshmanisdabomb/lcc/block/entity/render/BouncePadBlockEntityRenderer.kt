package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.extensions.blockEntityTransform
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.MathHelper
import kotlin.math.PI

class BouncePadBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<BouncePadBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer {

    val bouncers: Array<ModelPart>

    init {
        val part = context.getLayerModelPart(LCCModelLayers.bounce_pad)
        bouncers = Array(bouncerAmount) { part.getChild(it.toString()) }
    }

    override fun render(entity: BouncePadBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val direction = entity.cachedState.get(Properties.FACING) ?: return
        val extension = MathHelper.lerp(tickDelta, entity.lastExtension, entity.extension).times(9)
        val extensionWhole = MathHelper.ceil(extension)
        val extensionRem = extension.minus(extensionWhole)

        matrices.push()
        if (extensionWhole < 8) matrices.translate(direction.offsetX.times(0.0625).times(extensionRem), direction.offsetY.times(0.0625).times(extensionRem), direction.offsetZ.times(0.0625).times(extensionRem))
        direction.blockEntityTransform(matrices)
        bouncers[8.minus(extensionWhole).coerceIn(0, 8)].render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay)
        matrices.pop()
    }

    override fun render(stack: ItemStack, mode: ModelTransformation.Mode, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {

    }

    companion object : ClientSpriteRegistryCallback {
        const val bouncerAmount = 9
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/bounce_pad"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            for (i in 0 until bouncerAmount) {
                modelPartData.addChild(i.toString(), ModelPartBuilder.create().uv(0, 0).cuboid(-7.0f, i.minus(1).minus(7).toFloat(), -7.0f, 14f, 9f - i, 14f), ModelTransform.of(8.0f, 8.0f, 8.0f, PI.toFloat(), 0.0f, 0.0f))
            }
            return TexturedModelData.of(modelData, 64, 32)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

}
