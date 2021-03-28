package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.TimeRiftBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.directory.LCCRenderLayers
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.utils.RenderingUtils
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3d
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.util.math.MathHelper
import java.util.*
import kotlin.math.min
import kotlin.math.pow

class TimeRiftBlockEntityRenderer(context: BlockEntityRendererFactory.Context?) : BlockEntityRenderer<TimeRiftBlockEntity>, BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    lateinit var parts: Array<ModelPart>

    private val rand = Random()

    private var tickLastChanged = 0L
    private val pos = Array(partAmount) { Vector3d(rand.nextDouble().minus(0.5), rand.nextDouble().minus(0.5), rand.nextDouble().minus(0.5)) }
    private val lastPos = Array(partAmount) { Vector3d(pos[it].x, pos[it].y, pos[it].z) }
    private val motions = Array(partAmount) { randomMotion(Vector3d(0.0, 0.0, 0.0)) }
    private val colors = Array(partAmount) { color(Vector3d(0.0, 0.0, 0.0), pos[it].x, pos[it].y, pos[it].z) }

    init {
        if (context != null) {
            val part = context.getLayerModelPart(LCCModelLayers.time_rift)
            parts = Array(partAmount) { part.getChild(it.toString()) }
        }
    }

    override fun apply(manager: ResourceManager) {
        val part = MinecraftClient.getInstance().entityModelLoader.getModelPart(LCCModelLayers.time_rift)
        parts = Array(partAmount) { part.getChild(it.toString()) }
    }

    override fun render(entity: TimeRiftBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        moveTick(entity.world?.time)

        val progress = RenderingUtils.breakingProgress(entity.pos) ?: 0f
        val size = 1f - progress
        parts.forEachIndexed { k, v ->
            render(k, v, tickDelta, matrices, vertexConsumers, light, overlay, MathHelper.lerp(tickDelta, entity.lastSize, entity.size) * size, 0f + progress, 1f - progress, 1f - progress)
        }
    }

    override fun render(stack: ItemStack, mode: ModelTransformation.Mode, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        moveTick(MinecraftClient.getInstance().world?.time)

        parts.forEachIndexed { k, v ->
            render(k, v, MinecraftClient.getInstance().tickDelta, matrices, vertexConsumers, light, overlay, 1f, 0f, 1f, 1f)
        }
    }

    private fun render(index: Int, part: ModelPart, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int, size: Float, red: Float, green: Float, blue: Float) {
        matrices.push()
        matrices.translate(0.5, 0.5, 0.5)
        matrices.scale(size, size, size)
        val x = MathHelper.lerp(tickDelta.toDouble(), lastPos[index].x, pos[index].x)
        val y = MathHelper.lerp(tickDelta.toDouble(), lastPos[index].y, pos[index].y)
        val z = MathHelper.lerp(tickDelta.toDouble(), lastPos[index].z, pos[index].z)
        color(colors[index], x, y, z)
        matrices.translate(x, y, z)
        part.render(matrices, texture.getVertexConsumer(vertexConsumers, LCCRenderLayers.bright), light, overlay, red * colors[index].x.toFloat(), green * colors[index].y.toFloat(), blue * colors[index].z.toFloat(), 1.0f)
        matrices.pop()
    }

    private fun moveTick(time: Long?) {
        if (tickLastChanged != time ?: 1L) {
            tickLastChanged = time ?: 0L
            parts.forEachIndexed { k, v -> move(k) }
        }
    }

    private fun move(index: Int) {
        val size = getSize(index)

        lastPos[index].x = pos[index].x
        lastPos[index].y = pos[index].y
        lastPos[index].z = pos[index].z
        pos[index].x += motions[index].x
        pos[index].y += motions[index].y
        pos[index].z += motions[index].z

        motions[index].x *= 1.0 - 0.008
        motions[index].y *= 1.0 - 0.008
        motions[index].z *= 1.0 - 0.008

        val bound = 0.5f - (size.div(16f))
        if (pos[index].x > bound || pos[index].x < -bound || pos[index].y > bound || pos[index].y < -bound || pos[index].z > bound || pos[index].z < -bound) {
            pos[index].x = 0.0
            pos[index].y = 0.0
            pos[index].z = 0.0
            lastPos[index].x = 0.0
            lastPos[index].y = 0.0
            lastPos[index].z = 0.0
            randomMotion(motions[index])
        }
    }

    private fun randomMotion(vec: Vector3d) = DoubleArray(3) { if (it == 0) rand.nextDouble().times(0.02).plus(0.02).times(rand.nextBoolean().transformInt(1, -1)) else rand.nextDouble().times(0.04).minus(0.02) }.run { shuffle(); vec.x = this[0]; vec.y = this[1]; vec.z = this[2]; vec }

    private fun color(color: Vector3d, x: Double, y: Double, z: Double): Vector3d {
        val distance = min((MathHelper.absMax(MathHelper.absMax(x, y), z) * 2).pow(4.0).times(3.0), 1.0)
        color.x = distance
        color.y = distance
        color.z = distance
        return color
    }

    companion object : ClientSpriteRegistryCallback {
        const val partAmount = 100
        val partSize = 1..4

        val texture = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("entity/time_rift"))

        fun data(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            for (i in 0 until partAmount) {
                val size = getSize(i)
                modelPartData.addChild(i.toString(), ModelPartBuilder.create().uv(0, 0).cuboid(0f, 0f, 0f, size, size, size), ModelTransform.of(-size.div(2), -size.div(2), -size.div(2), 0f, 0f, 0f))

            }
            return TexturedModelData.of(modelData, 64, 32)
        }

        override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
            registry.register(texture.textureId)
        }

        fun getSize(index: Int) = (index.rem(partSize.last - (partSize.first - 1)) + partSize.first).toFloat()
    }

    override fun getFabricId() = LCC.id("time_rift_item_dynamic_render_${hashCode()}")

}
