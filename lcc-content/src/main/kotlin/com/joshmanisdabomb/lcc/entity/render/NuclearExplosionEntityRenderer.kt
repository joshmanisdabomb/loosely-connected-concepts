package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.extensions.toInt
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.ParticlesMode
import net.minecraft.client.render.Frustum
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import java.util.*
import kotlin.math.max

class NuclearExplosionEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<NuclearExplosionEntity>(ctx) {

    val rand = Random()
    val bp by lazy { BlockPos.Mutable() }

    init {
        shadowRadius = 0.0f
    }

    override fun render(entity: NuclearExplosionEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        val world = MinecraftClient.getInstance().world ?: return
        if (MinecraftClient.getInstance().isPaused) return
        if (MinecraftClient.getInstance().options.particles == ParticlesMode.MINIMAL) return
        val particles = (MinecraftClient.getInstance().options.particles == ParticlesMode.DECREASED).toInt(5, 1)
        val lifetime = max(entity.lifetime, 1)
        val l = entity.radius / lifetime.toDouble()
        val f = (entity.ticks / lifetime.toDouble()).let { MathHelper.sqrt(it) }
        val range = (l * entity.ticks * (2.5 - f.times(1.6)))
        //fireball
        for (i in 0..particles.times(20)) {
            val vec = Vec3d(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5).normalize().multiply(range)
            val vec1 = vec.multiply(rand.nextDouble().times(0.02).plus(0.98)).add(entity.pos)
            MinecraftClient.getInstance().world?.addParticle(LCCParticles.nuclear, true, vec1.x, vec1.y, vec1.z, 0.0, 0.0, 0.0)
            if (i % 2 == 0) {
                val vec2 = vec.multiply(1.0 - rand.nextDouble().let { MathHelper.sqrt(it) }).add(entity.pos)
                MinecraftClient.getInstance().world?.addParticle(LCCParticles.nuclear, true, vec2.x, vec2.y, vec2.z, 0.0, 0.0, 0.0)
            }
        }
        //stem
        for (i in 0..particles.times(5)) {
            world.addParticle(LCCParticles.nuclear, true, entity.x + rand.nextDouble().minus(0.5).times(range).times(0.8), entity.y + rand.nextDouble().minus(0.5).times(range).times(0.8), entity.z + rand.nextDouble().minus(0.5).times(range).times(0.5), 0.0, entity.ticks.times(1.5).times(rand.nextDouble().times(0.3).plus(0.7)), 0.0)
        }
        //cap
        bp.set(entity.blockPos)
        for (i in entity.blockPos.y .. world.topY) {
            if (world.isTopSolid(bp, entity)) return
            bp.move(0, 1, 0)
        }
        for (i in 0..particles.times(40)) {
            var vec = Vec3d(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5).normalize().add(rand.nextDouble().minus(0.5).times(2), 0.0, rand.nextDouble().minus(0.5).times(2))
            val vec0 = vec.multiply(range)
            vec = vec.multiply(0.5)
            val vec1 = vec0.multiply(rand.nextDouble().times(0.02).plus(0.98)).add(entity.pos).add(0.0, entity.ticks.times(32).times(rand.nextDouble().times(0.1).plus(0.9)), 0.0)
            MinecraftClient.getInstance().world?.addParticle(LCCParticles.nuclear, true, vec1.x, vec1.y, vec1.z, vec.x, -0.3, vec.z)
            if (i % 2 == 0) {
                val vec2 = vec0.multiply(1.0 - rand.nextDouble().let { MathHelper.sqrt(it) }).add(entity.pos).add(0.0, entity.ticks.times(32).times(rand.nextDouble().times(0.1).plus(0.9)), 0.0)
                MinecraftClient.getInstance().world?.addParticle(LCCParticles.nuclear, true, vec2.x, vec2.y, vec2.z, vec.x, -0.3, vec.z)
            }
        }
    }
    
    override fun getTexture(entity: NuclearExplosionEntity) = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE

    override fun shouldRender(entity: NuclearExplosionEntity, frustum: Frustum, x: Double, y: Double, z: Double) = true

}