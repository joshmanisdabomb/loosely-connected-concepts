package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack

class NuclearExplosionEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<NuclearExplosionEntity>(ctx) {
    
    init {
        shadowRadius = 0.0f
    }

    override fun render(entity: NuclearExplosionEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {

    }
    
    override fun getTexture(entity: NuclearExplosionEntity) = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE

}