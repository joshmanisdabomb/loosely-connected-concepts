package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.perpendiculars
import net.fabricmc.fabric.api.renderer.v1.RendererAccess
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.client.util.math.Vector3f
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Supplier

class ConnectedTextureModel(defaultPrefix: String, val connector: (state: BlockState, other: BlockState) -> Boolean = { state, other -> state == other }, val borderSize: Int = 4, val pos1: Vector3f = Vector3f(0f, 0f, 0f), val pos2: Vector3f = Vector3f(1f, 1f, 1f), val innerSeams: Boolean = true, mapConsumer: (map: ConnectedTextureMap) -> Unit = {}) : LCCModel({ (this as ConnectedTextureModel).textureMap }) {

    val map by lazy { ConnectedTextureMap(defaultPrefix).also(mapConsumer) }
    val textureMap by lazy { map.map }

    val borderSizeFloat = borderSize.toFloat().div(16)

    val bp by lazy { BlockPos.Mutable() }
    val bpc by lazy { BlockPos.Mutable() }

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        Direction.values().forEach {
            val perps = it.perpendiculars

            val cUp = connection(renderView, state, pos, it, perps[0])
            val cRight = connection(renderView, state, pos, it, perps[1])
            val cDown = connection(renderView, state, pos, it, perps[2])
            val cLeft = connection(renderView, state, pos, it, perps[3])
            val cUpLeft = connection(renderView, state, pos, it, perps[0], perps[3])
            val cUpRight = connection(renderView, state, pos, it, perps[1], perps[0])
            val cDownRight = connection(renderView, state, pos, it, perps[2], perps[1])
            val cDownLeft = connection(renderView, state, pos, it, perps[3], perps[2])

            //base
            face(it, renderContext, map.getBase(it)) {
                it[0] += borderSizeFloat
                it[1] += borderSizeFloat
                it[2] -= borderSizeFloat
                it[3] -= borderSizeFloat
            }

            //sides (from left clockwise)
            face(it, renderContext, map.getSide(it, cLeft, true)) {
                it[2] = it[2].coerceAtMost(it[0] + borderSizeFloat)
                it[1] += borderSizeFloat
                it[3] -= borderSizeFloat
            }
            face(it, renderContext, map.getSide(it, cUp, false)) {
                it[1] = it[1].coerceAtLeast(it[3] - borderSizeFloat)
                it[0] += borderSizeFloat
                it[2] -= borderSizeFloat
            }
            face(it, renderContext, map.getSide(it, cRight, true)) {
                it[0] = it[0].coerceAtLeast(it[2] - borderSizeFloat)
                it[1] += borderSizeFloat
                it[3] -= borderSizeFloat
            }
            face(it, renderContext, map.getSide(it, cDown, false)) {
                it[3] = it[3].coerceAtMost(it[1] + borderSizeFloat)
                it[0] += borderSizeFloat
                it[2] -= borderSizeFloat
            }

            //corners (from bottom left clockwise)
            face(it, renderContext, map.getCorner(it, cDown, cLeft, cDownLeft)) {
                it[2] = it[2].coerceAtMost(it[0] + borderSizeFloat)
                it[3] = it[3].coerceAtMost(it[1] + borderSizeFloat)
            }
            face(it, renderContext, map.getCorner(it, cUp, cLeft, cUpLeft)) {
                it[2] = it[2].coerceAtMost(it[0] + borderSizeFloat)
                it[1] = it[1].coerceAtLeast(it[3] - borderSizeFloat)
            }
            face(it, renderContext, map.getCorner(it, cUp, cRight, cUpRight)) {
                it[0] = it[0].coerceAtLeast(it[2] - borderSizeFloat)
                it[1] = it[1].coerceAtLeast(it[3] - borderSizeFloat)
            }
            face(it, renderContext, map.getCorner(it, cDown, cRight, cDownRight)) {
                it[0] = it[0].coerceAtLeast(it[2] - borderSizeFloat)
                it[3] = it[3].coerceAtMost(it[1] + borderSizeFloat)
            }
        }
    }

    private fun cull(state: BlockState, renderView: BlockRenderView, pos: BlockPos, direction: Direction): Boolean {
        return try {
            !Block.shouldDrawSide(state, renderView, pos, direction, bpc)
        } catch (exception: ArrayIndexOutOfBoundsException) {
            false
        }
    }

    private fun connection(renderView: BlockRenderView, state: BlockState, pos: BlockPos, innerSeamPath: Direction, vararg path: Direction): Boolean {
        bp.set(pos).apply { path.map { this.move(it) } }
        return try {
            if (innerSeams && connector(state, renderView.getBlockState(bp.move(innerSeamPath)))) return false
            connector(state, renderView.getBlockState(bp.move(innerSeamPath, -1)))
        } catch (exception: ArrayIndexOutOfBoundsException) {
            false
        }
    }

    private fun face(direction: Direction, renderContext: RenderContext, sprite: Sprite, faceTransform: (FloatArray) -> Unit = {}) {
        renderContext.emitter
            .cubeFace(direction, pos1, pos2, faceTransform)
            ?.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV)
            ?.lightmap(0, 15)?.lightmap(1, 15)?.lightmap(2, 15)?.lightmap(3, 15)
            ?.spriteColor(0, -1, -1, -1, -1)
            ?.material(RendererAccess.INSTANCE.renderer!!.materialFinder().find())
            ?.emit()
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    override fun getSprite() = map.particleTexture

    companion object {
        val segments = listOf("base", "corners_i", "corners_o", "lines_h", "lines_v")
        val sides = mapOf(Direction.UP to "top", Direction.DOWN to "bottom", Direction.NORTH to "north", Direction.EAST to "east", Direction.SOUTH to "south", Direction.WEST to "west")
    }

    inner class ConnectedTextureMap internal constructor(val default: String) {

        private val raw = sides.map { (_, v) -> v to default }.toMap().toMutableMap()
        private var particleGetter = { getKey("north", "base") }
        val particle by lazy { particleGetter() }

        fun top(prefix: String) = raw.put("top", prefix).let { this }
        fun bottom(prefix: String) = raw.put("bottom", prefix).let { this }
        fun north(prefix: String) = raw.put("north", prefix).let { this }
        fun east(prefix: String) = raw.put("east", prefix).let { this }
        fun south(prefix: String) = raw.put("south", prefix).let { this }
        fun west(prefix: String) = raw.put("west", prefix).let { this }

        fun sides(prefix: String) = this.north(prefix).east(prefix).south(prefix).west(prefix)

        fun particle(texture: String) { particleGetter = { texture } }
        fun particle(side: String, segment: String) { particleGetter = { getKey(side, segment) } }

        fun getKey(side: String, segment: String) = "${sideId[side].toString()}_$segment"
        fun get(side: String, segment: String) = bakedSprites[getKey(side, segment)]!!

        fun getBase(direction: Direction) = get(sides[direction]!!, "base")
        fun getSide(direction: Direction, connection: Boolean, vertical: Boolean) = if (connection) getBase(direction) else get(sides[direction]!!, if (vertical) "lines_v" else "lines_h")
        fun getCorner(direction: Direction, connectionV: Boolean, connectionH: Boolean, connectionVH: Boolean) = when {
            connectionVH && connectionV && connectionH -> getBase(direction)
            !connectionVH && connectionV && connectionH -> get(sides[direction]!!, "corners_i")
            connectionV -> get(sides[direction]!!, "lines_v")
            connectionH -> get(sides[direction]!!, "lines_h")
            else -> get(sides[direction]!!, "corners_o")
        }

        val particleTexture by lazy { bakedSprites.getOrDefault("particle", bakedSprites[particle]) }

        val prefixId by lazy { raw.values.distinct() }
        val sideId by lazy { raw.mapValues { (_, v) -> prefixId.indexOf(v) } }
        val map: Map<String, SpriteIdentifier> by lazy {
            val m = raw.toList().distinctBy { it.second }.flatMap(::expand).map { it.first to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/${it.second}")) }.toMap().toMutableMap()
            if (!m.containsKey(particle)) m["particle"] = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/$particle"))
            m.toMap()
        }

        private fun expand(side: Pair<String, String>) = segments.map { sideId[side.first].toString().plus("_$it") to side.second.plus("_$it") }

    }

}