package com.joshmanisdabomb.lcc.item.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

@Environment(EnvType.CLIENT)
class ConsumerMawItemRenderer : BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    private lateinit var head: ModelPart
    private lateinit var jaw: ModelPart
    private lateinit var teeth: List<ModelPart>

    override fun render(stack: ItemStack, mode: ModelTransformation.Mode, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val world = MinecraftClient.getInstance().world
        val players = world?.players
        val player = players?.firstOrNull { it.mainHandStack === stack }
        val swing = MathHelper.sin(MathHelper.PI.times(player?.getHandSwingProgress(MinecraftClient.getInstance().tickDelta) ?: 0f))
        if (swing != 0f) {
            println(swing)
        }
        jaw.pitch = swing.times(2f)
        head.pitch = swing.times(-1f)
        matrices.push()
        matrices.scale(1.3f, 1.3f, 1.3f)
        matrices.translate(0.38, -0.175, 0.34)
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180f))
        head.render(matrices, texture.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay)
        matrices.pop()
    }

    override fun reload(manager: ResourceManager) {
        val part = MinecraftClient.getInstance().entityModelLoader.getModelPart(LCCModelLayers.consumer_maw)
        head = part.getChild("head")
        jaw = head.getChild("jaw")
        teeth = List(7) { jaw.getChild("tooth_${it+1}") }
    }

    companion object : ClientSpriteRegistryCallback {
        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/consumer_maw"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val root = modelData.root
            val head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5f, -5.0f, -8.0f, 13.0f, 5.0f, 8.0f), ModelTransform.pivot(0f, 0f, 0f))

            val jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 13).cuboid(-6.5f, 0.0f, -8.0f, 13.0f, 1.0f, 8.0f), ModelTransform.pivot(0f, 0f, 0f))

            val teeth = List(7) {
                jaw.addChild("tooth_${it + 1}", ModelPartBuilder.create().uv(it.times(4), 22).cuboid(-0.5f, -4f, 0f, 1.0f, 4.0f, 1.0f), ModelTransform.pivot(-5.7f + it.times(1.9f), 0f, -7.7f))
            }

            return TexturedModelData.of(modelData, 64, 32)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }
    }

    override fun getFabricId() = LCC.id("consumer_maw_item_dynamic_render_${hashCode()}")

}