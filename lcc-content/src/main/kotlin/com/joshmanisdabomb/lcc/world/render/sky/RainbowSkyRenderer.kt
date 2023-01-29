package com.joshmanisdabomb.lcc.world.render.sky

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.minecraft.client.gl.VertexBuffer
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.random.Random


class RainbowSkyRenderer : DimensionRenderingRegistry.SkyRenderer {

    internal val skyline by lazy(::buildSkyline)
    internal val starfields by lazy { Array(4) { Starfield(2048L + it) } }
    internal val rainbow by lazy(::buildRainbow)
    val rainbowTexture = LCC.id("textures/environment/rainbow/rainbow.png")
    val nebulae = Array(6) { LCC.id("textures/environment/rainbow/sky$it.png") }

    override fun render(context: WorldRenderContext) {
        val night = context.world().method_23787(context.tickDelta())
        renderSkyline(context)
        renderStars(context, night)
        renderNebula(context, night)
        renderRainbow(context, night)

        RenderSystem.depthMask(true)
        RenderSystem.enableTexture()
        RenderSystem.disableBlend()
    }

    fun buildSkyline(): VertexBuffer {
        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer

        RenderSystem.setShader(GameRenderer::getPositionShader)
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION)
        buffer.vertex(0.0, 16.0, 0.0).next()
        for (i in -180..180 step 45) {
            buffer.vertex((512f * MathHelper.cos(i.toFloat() * (Math.PI.toFloat() / 180))).toDouble(), 16.0, (512.0f * MathHelper.sin(i.toFloat() * (Math.PI.toFloat() / 180))).toDouble()).next()
        }
        val built = buffer.end()

        val vb = VertexBuffer()
        vb.bind()
        vb.upload(built)
        VertexBuffer.unbind()
        return vb
    }

    fun renderSkyline(context: WorldRenderContext) {
        RenderSystem.disableTexture()
        BackgroundRenderer.setFogBlack()
        RenderSystem.depthMask(false)
        val color = context.world().getSkyColor(context.camera().pos, context.tickDelta())
        RenderSystem.setShaderColor(color.x.toFloat(), color.y.toFloat(), color.z.toFloat(), 1.0f)

        skyline.bind()
        skyline.draw(context.matrixStack().peek().positionMatrix, context.projectionMatrix(), GameRenderer.getPositionShader())
        VertexBuffer.unbind()
    }

    fun renderStars(context: WorldRenderContext, night: Float) {
        RenderSystem.enableBlend()
        if (night > 0.0f) {
            RenderSystem.setShaderColor(1f, 1f, 1f, night)
            BackgroundRenderer.clearFog()

            for (stars in starfields) {
                val matrices = context.matrixStack()
                matrices.push()
                val time = context.world().timeOfDay + context.tickDelta()
                val rx = time.div(stars.vx).plus(stars.sx).mod(MathHelper.PI.times(2f))
                val ry = time.div(stars.vy).plus(stars.sy).mod(MathHelper.PI.times(2f))
                val rz = time.div(stars.vz).plus(stars.sz).mod(MathHelper.PI.times(2f))
                matrices.multiply((if (stars.nx) Vec3f.NEGATIVE_X else Vec3f.POSITIVE_X).getRadialQuaternion(rx))
                matrices.multiply((if (stars.ny) Vec3f.NEGATIVE_Y else Vec3f.POSITIVE_Y).getRadialQuaternion(ry))
                matrices.multiply((if (stars.nz) Vec3f.NEGATIVE_Z else Vec3f.POSITIVE_Z).getRadialQuaternion(rz))

                stars.buffer.bind()
                stars.buffer.draw(context.matrixStack().peek().positionMatrix, context.projectionMatrix(), GameRenderer.getPositionShader())
                VertexBuffer.unbind()

                matrices.pop()
            }
        }
    }

    fun renderNebula(context: WorldRenderContext, night: Float) {
        RenderSystem.enableBlend()
        RenderSystem.depthMask(false)
        RenderSystem.setShader(GameRenderer::getPositionTexShader)

        val matrices = context.matrixStack()
        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

        matrices.push()
        val time = context.world().timeOfDay + context.tickDelta()
        val rx = time.div(9000f).mod(MathHelper.PI.times(2f))
        val ry = time.div(12000f).plus(1f).mod(MathHelper.PI.times(2f))
        val rz = time.div(13000f).plus(2f).mod(MathHelper.PI.times(2f))
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(rx))
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(ry))
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(rz))
        for (i in 0 until 6) {
            matrices.push()
            rotateSkyboxPlane(matrices, i)
            RenderSystem.setShaderTexture(0, nebulae[i])

            val matrix4f = matrices.peek().positionMatrix
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE)
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE)
            RenderSystem.setShaderColor(1f, 1f, 1f, night)
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).next()
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 1.0f).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0f, 1.0f).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0f, 0.0f).next()
            tessellator.draw()
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE)
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).next()
            bufferBuilder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 1.0f).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0f, 1.0f).next()
            bufferBuilder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0f, 0.0f).next()
            tessellator.draw()

            matrices.pop()
        }
        matrices.pop()
    }

    private fun rotateSkyboxPlane(matrices: MatrixStack, index: Int) {
        matrices.multiply(when (index) {
            0 -> Vec3f.POSITIVE_X.getDegreesQuaternion(180.0f)
            3 -> Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f)
            1 -> Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0f)
            4 -> Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0f)
            2 -> Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f)
            else -> return
        })
        matrices.multiply(when (index) {
            4 -> Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f)
            2 -> Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f)
            3 -> Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f)
            else -> return
        })
    }

    fun buildRainbow(): VertexBuffer {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer

        buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_TEXTURE)
        for (i in 0..360 step 10) {
            val theta = i * 3.14159265f / 180
            val a = MathHelper.cos(theta).times(100.0)
            val b = MathHelper.sin(theta).times(100.0)
            buffer.vertex(a, b, -15.0).texture(0.0f, 0.0f).next()
            buffer.vertex(a, b, 15.0).texture(1.0f, 1.0f).next()
        }
        val built = buffer.end()

        val vb = VertexBuffer()
        vb.bind()
        vb.upload(built)
        VertexBuffer.unbind()
        return vb
    }

    fun renderRainbow(context: WorldRenderContext, night: Float) {
        RenderSystem.enableBlend()
        RenderSystem.depthMask(false)
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, rainbowTexture)

        val matrices = context.matrixStack()
        matrices.push()
        val random = Random.create(14850L) //TODO use lcc world seed
        val px = random.nextDouble().times(0.15).plus(0.15).times(random.nextBoolean().transformInt(1, -1))
        val py = random.nextDouble().times(0.15).plus(0.15).times(random.nextBoolean().transformInt(1, -1))
        val pz = random.nextDouble().times(0.15).plus(0.15).times(random.nextBoolean().transformInt(1, -1))
        val rx = random.nextFloat().times(0.4f).plus(0.4f).times(random.nextBoolean().transformInt(1, -1))
        val ry = random.nextFloat().times(0.4f).plus(0.4f).times(random.nextBoolean().transformInt(1, -1))
        matrices.translate(px, py, pz)
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(rx))
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(ry))

        val rainbow = buildRainbow()
        rainbow.bind()

        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        rainbow.draw(matrices.peek().positionMatrix, context.projectionMatrix(), GameRenderer.getPositionTexShader())

        RenderSystem.defaultBlendFunc()
        RenderSystem.setShaderColor(1f, 1f, 1f, 0.35f - night.times(0.15f))
        rainbow.draw(matrices.peek().positionMatrix, context.projectionMatrix(), GameRenderer.getPositionTexShader())

        VertexBuffer.unbind()

        matrices.pop()
    }

    internal class Starfield(seed: Long) {

        val sx: Float
        val sy: Float
        val sz: Float
        val vx: Float
        val vy: Float
        val vz: Float
        val nx: Boolean
        val ny: Boolean
        val nz: Boolean
        val buffer: VertexBuffer

        init {
            val random = Random.create(seed)
            buffer = build(random)
            vx = random.nextFloat().times(10000f).plus(1000f)
            vy = random.nextFloat().times(10000f).plus(1000f)
            vz = random.nextFloat().times(10000f).plus(1000f)
            sx = random.nextFloat().times(MathHelper.PI.times(2))
            sy = random.nextFloat().times(MathHelper.PI.times(2))
            sz = random.nextFloat().times(MathHelper.PI.times(2))
            nx = random.nextBoolean()
            ny = random.nextBoolean()
            nz = random.nextBoolean()
        }

        private fun build(random: Random): VertexBuffer {
            val tessellator = Tessellator.getInstance()
            val buffer = tessellator.buffer

            RenderSystem.setShader(GameRenderer::getPositionShader)
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION)
            for (i in 0 until 1500) {
                var d = (random.nextFloat() * 2.0f - 1.0f).toDouble()
                var e = (random.nextFloat() * 2.0f - 1.0f).toDouble()
                var f = (random.nextFloat() * 2.0f - 1.0f).toDouble()
                val g = (0.15f + random.nextFloat() * 0.1f).toDouble()
                var h = d * d + e * e + f * f
                if (h >= 1.0 || h <= 0.01) continue
                h = 1.0 / Math.sqrt(h)
                val j = h.let { d *= it; d } * 100.0
                val k = h.let { e *= it; e } * 100.0
                val l = h.let { f *= it; f } * 100.0
                val m = Math.atan2(d, f)
                val n = Math.sin(m)
                val o = Math.cos(m)
                val p = Math.atan2(Math.sqrt(d * d + f * f), e)
                val q = Math.sin(p)
                val r = Math.cos(p)
                val s = random.nextDouble() * Math.PI * 2.0
                val t = Math.sin(s)
                val u = Math.cos(s)
                for (v in 0..3) {
                    var ab: Double
                    val w = 0.0
                    val x = ((v and 2) - 1).toDouble() * g
                    val y = ((v + 1 and 2) - 1).toDouble() * g
                    val z = 0.0
                    val aa = x * u - y * t
                    ab = y * u + x * t
                    val ac = ab
                    val ad = aa * q + 0.0 * r
                    val ae = 0.0 * q - aa * r
                    val af = ae * n - ac * o
                    val ah = ac * n + ae * o
                    buffer.vertex(j + af, k + ad, l + ah).next()
                }
            }
            val built = buffer.end()

            val vb = VertexBuffer()
            vb.bind()
            vb.upload(built)
            VertexBuffer.unbind()
            return vb
        }

    }

}
