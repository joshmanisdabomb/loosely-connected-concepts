package com.joshmanisdabomb.lcc.world.render.sky

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.extensions.rotateTransform
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f


class RainbowSkyRenderer : DimensionRenderingRegistry.SkyRenderer {

    val textures = Array(6) {
        LCC.id("textures/environment/rainbow/sky$it.png")
    }

    override fun render(context: WorldRenderContext) {
        RenderSystem.enableBlend()
        RenderSystem.depthMask(false)
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader)
        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

        val matrices = context.matrixStack()
        matrices.push()
        val time = context.world().timeOfDay + context.tickDelta()
        val nebulaX = time.div(9000f).mod(MathHelper.PI.times(2f))
        val nebulaY = time.div(12000f).plus(1f).mod(MathHelper.PI.times(2f))
        val nebulaZ = time.div(13000f).plus(2f).mod(MathHelper.PI.times(2f))
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(nebulaX))
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(nebulaY))
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(nebulaZ))
        for ((k, v) in Direction.values().withIndex()) {
            val k2 = when (k) {
                0 -> 0
                1 -> 5
                2 -> 3
                3 -> 1
                4 -> 2
                5 -> 4
                else -> k
            }
            RenderSystem.setShaderTexture(0, textures[k2])

            matrices.push()
            v.rotateTransform(matrices)
            val matrix4f = matrices.peek().positionMatrix

            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
            RenderSystem.blendFunc(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE)
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 1.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0f, 1.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0f, 0.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 1.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0f, 1.0f).color(255, 255, 255, 255).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0f, 0.0f).color(255, 255, 255, 255).next()
            tessellator.draw()

            matrices.pop()
        }
        matrices.pop()

        RenderSystem.depthMask(true)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()
    }

}
