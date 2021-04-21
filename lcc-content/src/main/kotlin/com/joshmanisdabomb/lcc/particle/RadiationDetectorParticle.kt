package com.joshmanisdabomb.lcc.particle

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.render.*
import net.minecraft.client.world.ClientWorld

class RadiationDetectorParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, sp: SpriteProvider) : StaticBillboardParticle(world, x, y, z, dx, dy, dz, sp) {

    init {

    }

    override fun tick() {
        colorAlpha = maxAge.minus(age).toFloat().div(maxAge)
        super.tick()
    }

    override fun getType() = ParticleTextureSheet.CUSTOM

    override fun buildGeometry(vertexConsumer: VertexConsumer, camera: Camera, tickDelta: Float) {
        Tessellator.getInstance().buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT)
        RenderSystem.disableDepthTest()
        RenderSystem.enableBlend()
        super.buildGeometry(vertexConsumer, camera, tickDelta)
        Tessellator.getInstance().draw()
    }

    override fun getBrightness(tint: Float) = 15728880

}