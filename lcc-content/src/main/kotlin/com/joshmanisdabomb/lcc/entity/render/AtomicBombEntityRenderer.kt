package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.block.AtomicBombBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.TntMinecartEntityRenderer
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

class AtomicBombEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<AtomicBombEntity>(ctx) {
    
    init {
        shadowRadius = 1.3f
    }

    override fun render(entity: AtomicBombEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        matrices.push()
        matrices.translate(0.0, 0.5, 0.0)
        if (entity.fuse.toFloat() - tickDelta + 1.0f < 10.0f) {
            var f: Float = 1.0f - (entity.fuse.toFloat() - tickDelta + 1.0f) / 10.0f
            f = MathHelper.clamp(f, 0.0f, 1.0f)
            f *= f
            f *= f
            val f1 = 1.0f + f * 0.3f
            matrices.scale(f1, f1, f1)
        }
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f))
        matrices.translate(-0.5, -0.5, 0.5)
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f))
        this.renderBlock(entity, MinecraftClient.getInstance().blockEntityRenderDispatcher, entity.facing ?: Direction.NORTH, matrices, vertexConsumers, light)
        matrices.pop()
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    private fun renderBlock(entity: AtomicBombEntity, brd: BlockEntityRenderDispatcher, facing: Direction, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        matrices.push()
        TntMinecartEntityRenderer.renderFlashingBlock(MinecraftClient.getInstance().blockRenderManager, LCCBlocks.atomic_bomb.defaultState.with(AtomicBombBlock.segment, AtomicBombBlock.AtomicBombSegment.MIDDLE).with(Properties.HORIZONTAL_FACING, facing), matrices, vertexConsumers, light, entity.fuse / 5 % 2 == 1)
        matrices.translate(-facing.offsetX.toDouble(), 0.0, -facing.offsetZ.toDouble())
        TntMinecartEntityRenderer.renderFlashingBlock(MinecraftClient.getInstance().blockRenderManager, LCCBlocks.atomic_bomb.defaultState.with(AtomicBombBlock.segment, AtomicBombBlock.AtomicBombSegment.TAIL).with(Properties.HORIZONTAL_FACING, facing), matrices, vertexConsumers, light, entity.fuse / 5 % 2 == 1)
        matrices.translate(facing.offsetX.times(2.0), 0.0, facing.offsetZ.times(2.0))
        TntMinecartEntityRenderer.renderFlashingBlock(MinecraftClient.getInstance().blockRenderManager, LCCBlocks.atomic_bomb.defaultState.with(AtomicBombBlock.segment, AtomicBombBlock.AtomicBombSegment.HEAD).with(Properties.HORIZONTAL_FACING, facing), matrices, vertexConsumers, light, entity.fuse / 5 % 2 == 1)
        matrices.pop()
    }
    
    override fun getTexture(entity: AtomicBombEntity) = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE

}