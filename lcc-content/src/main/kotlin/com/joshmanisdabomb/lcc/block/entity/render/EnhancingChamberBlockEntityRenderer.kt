package com.joshmanisdabomb.lcc.block.entity.render

import com.joshmanisdabomb.lcc.block.entity.EnhancingChamberBlockEntity
import com.joshmanisdabomb.lcc.extensions.blockEntityTransform
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Vec3f

class EnhancingChamberBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<EnhancingChamberBlockEntity> {

    override fun render(entity: EnhancingChamberBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val stack = entity.inventory.getStack(0)
        if (!stack.isEmpty) {
            matrices.push()
            entity.cachedState[Properties.FACING].blockEntityTransform(matrices)
            matrices.translate(0.5, 3.0.div(32.0).times(0.6), 0.5)
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f))
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f))
            matrices.scale(0.6f, 0.6f, 0.6f)
            MinecraftClient.getInstance().itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.pos.asLong().toInt())
            matrices.pop()
        }
    }

}