package com.joshmanisdabomb.lcc.item.render

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper
import net.fabricmc.fabric.api.client.model.ExtraModelProvider
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry
import net.fabricmc.fabric.impl.client.indigo.renderer.accessor.AccessItemRenderer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayers
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import java.util.function.Consumer

@Environment(EnvType.CLIENT)
class CalendarItemRenderer : BuiltinItemRendererRegistry.DynamicItemRenderer, ExtraModelProvider {

    private val model = LCC.id("item/calendar_base")

    override fun render(stack: ItemStack, mode: ModelTransformation.Mode, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        matrices.push()
        val direct = mode != ModelTransformation.Mode.GUI && !mode.isFirstPerson
        val layer = RenderLayers.getItemLayer(stack, direct)
        val vertexConsumer = if (direct) ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, layer, true, stack.hasGlint()) else ItemRenderer.getItemGlintConsumer(vertexConsumers, layer, true, stack.hasGlint())
        val model = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().bakedModelManager, model)
        (MinecraftClient.getInstance().itemRenderer as? AccessItemRenderer)?.fabric_renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer)
        matrices.pop()

        val day = MinecraftClient.getInstance().world?.timeOfDay?.div(24000)?.toString()
        if (day != null) {
            matrices.push()
            val width = MinecraftClient.getInstance().textRenderer.getWidth(day)
            val scale = 18f.div(width).coerceAtMost(1f)
            matrices.translate(0.0, 0.5, 0.5)
            matrices.translate(0.5.plus(0.015625.times(scale)), 0.09 - 0.046.div(scale), 0.036)
            matrices.scale(scale.times(0.04f), -scale.times(0.04f), scale.times(0.04f))
            MinecraftClient.getInstance().textRenderer.draw(day, width.div(-2f), 0f, 0xFF1A1A1A.toInt(), false, matrices.peek().positionMatrix, vertexConsumers, false, 0, light)
            matrices.scale(-1f, 1f, 1f)
            matrices.translate(0.0, 0.0, 1.7.div(-scale))
            MinecraftClient.getInstance().textRenderer.draw(day, width.div(-2f), 0f, 0xFF1A1A1A.toInt(), false, matrices.peek().positionMatrix, vertexConsumers, false, 0, light)
            matrices.pop()
        }
    }

    override fun provideExtraModels(manager: ResourceManager, loader: Consumer<Identifier>) {
        loader.accept(model)
    }

}