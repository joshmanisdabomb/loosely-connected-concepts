package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.extensions.perpendiculars
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f
import net.minecraft.world.BlockRenderView
import net.minecraft.world.BlockView
import java.util.*
import java.util.function.Supplier

open class ConnectedTextureModel(defaultPrefix: Identifier, val connector: (world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) -> Boolean = { world, state, pos, other, otherPos, path -> state == other }, val borderSize: Int = 4, val pos1: Vec3f = Vec3f(0f, 0f, 0f), val pos2: Vec3f = Vec3f(1f, 1f, 1f), val innerSeams: Boolean = true, mapConsumer: ConnectedTextureMap.() -> Unit = {}) : LCCModel({ (this as ConnectedTextureModel).textureMap }) {

    val map by lazy { ConnectedTextureMap().all(defaultPrefix).also(mapConsumer) }
    val textureMap by lazy { map.map }

    val borderSizeFloat = borderSize.toFloat().div(16)

    protected val faceConnections by lazy { FaceConnectionMap() }

    val bp by lazy { BlockPos.Mutable() }
    val bp2 by lazy { BlockPos.Mutable() }
    val bpc by lazy { BlockPos.Mutable() }

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        Direction.values().forEach {
            println(map.excludes)
            if (!map.excludes.contains(it)) {
                faceConnections.reload(it, renderView, state, pos)
                emitConnectedFace(it, renderContext)
            } else {
                emitFace(it, renderContext, bakedSprites[it.asString()] ?: error("Excluded face without an alternative texture."), pos1, pos2)
            }
        }
    }

    protected open fun emitConnectedFace(face: Direction, renderContext: RenderContext, borderSizeFloat: (part: Int) -> Float = { this.borderSizeFloat }, index: (part: Int) -> Int = { 0 }) {
        //base
        emitFace(face, renderContext, map.getBase(face, index(0)), pos1, pos2) {
            with (borderSizeFloat(0)) {
                it[0] += this
                it[1] += this
                it[2] -= this
                it[3] -= this
            }
        }

        //sides (from left clockwise)
        emitFace(face, renderContext, map.getSide(face, faceConnections.left, true, index(1)), pos1, pos2) {
            with (borderSizeFloat(1)) {
                it[2] = it[2].coerceAtMost(it[0] + this)
                it[1] += this
                it[3] -= this
            }
        }
        emitFace(face, renderContext, map.getSide(face, faceConnections.up, false, index(2)), pos1, pos2) {
            with (borderSizeFloat(2)) {
                it[1] = it[1].coerceAtLeast(it[3] - this)
                it[0] += this
                it[2] -= this
            }
        }
        emitFace(face, renderContext, map.getSide(face, faceConnections.right, true, index(3)), pos1, pos2) {
            with (borderSizeFloat(3)) {
                it[0] = it[0].coerceAtLeast(it[2] - this)
                it[1] += this
                it[3] -= this
            }
        }
        emitFace(face, renderContext, map.getSide(face, faceConnections.down, false, index(4)), pos1, pos2) {
            with (borderSizeFloat(4)) {
                it[3] = it[3].coerceAtMost(it[1] + this)
                it[0] += this
                it[2] -= this
            }
        }

        //corners (from bottom left clockwise)
        emitFace(face, renderContext, map.getCorner(face, faceConnections.down, faceConnections.left, faceConnections.downLeft, index(5)), pos1, pos2) {
            with (borderSizeFloat(5)) {
                it[2] = it[2].coerceAtMost(it[0] + this)
                it[3] = it[3].coerceAtMost(it[1] + this)
            }
        }
        emitFace(face, renderContext, map.getCorner(face, faceConnections.up, faceConnections.left, faceConnections.upLeft, index(6)), pos1, pos2) {
            with (borderSizeFloat(6)) {
                it[2] = it[2].coerceAtMost(it[0] + this)
                it[1] = it[1].coerceAtLeast(it[3] - this)
            }
        }
        emitFace(face, renderContext, map.getCorner(face, faceConnections.up, faceConnections.right, faceConnections.upRight, index(7)), pos1, pos2) {
            with (borderSizeFloat(7)) {
                it[0] = it[0].coerceAtLeast(it[2] - this)
                it[1] = it[1].coerceAtLeast(it[3] - this)
            }
        }
        emitFace(face, renderContext, map.getCorner(face, faceConnections.down, faceConnections.right, faceConnections.downRight, index(8)), pos1, pos2) {
            with (borderSizeFloat(8)) {
                it[0] = it[0].coerceAtLeast(it[2] - this)
                it[3] = it[3].coerceAtMost(it[1] + this)
            }
        }
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    protected open fun cull(state: BlockState, renderView: BlockRenderView, pos: BlockPos, direction: Direction): Boolean {
        return try {
            !Block.shouldDrawSide(state, renderView, pos, direction, bpc)
        } catch (exception: ArrayIndexOutOfBoundsException) {
            false
        }
    }

    override fun getSprite() = map.getParticle(0)

    companion object {
        val segments = listOf("base", "corners_i", "corners_o", "lines_h", "lines_v")
    }

    inner class ConnectedTextureMap internal constructor() {

        private val sets = mutableListOf<Identifier>()
        private val faces = mutableListOf<List<Direction?>>()
        private val predicates = mutableListOf<(Int) -> Boolean>()

        private val particles = mutableListOf<Identifier>()
        private val particlePredicates = mutableListOf<(Int) -> Boolean>()

        internal val excludes = mutableSetOf<Direction>()
        internal val includes = mutableMapOf<String, Identifier>()

        private val keyCache = mutableMapOf<Pair<Direction?, Int>, Identifier>()

        fun set(prefix: Identifier, vararg faces: Direction?, on: (index: Int) -> Boolean = { true }): ConnectedTextureMap {
            this.sets += prefix
            this.faces += faces.toList()
            this.predicates += on
            return this
        }

        fun top(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.UP).apply { if (particle) this.add(null) }.toTypedArray(), on = on)
        fun bottom(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.DOWN).apply { if (particle) this.add(null) }.toTypedArray(), on = on)
        fun north(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.NORTH).apply { if (particle) this.add(null) }.toTypedArray(), on = on)
        fun east(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.EAST).apply { if (particle) this.add(null) }.toTypedArray(), on = on)
        fun south(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.SOUTH).apply { if (particle) this.add(null) }.toTypedArray(), on = on)
        fun west(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, *mutableListOf<Direction?>(Direction.WEST).apply { if (particle) this.add(null) }.toTypedArray(), on = on)

        fun sides(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, on = on)
        fun all(prefix: Identifier, particle: Boolean = false, on: (index: Int) -> Boolean = { true }) = set(prefix, null, *Direction.values(), on = on)

        fun particle(texture: Identifier, on: (index: Int) -> Boolean = { true }) = this.also { particles.add(texture); particlePredicates.add(on) }

        fun exclude(vararg faces: Direction, replacement: Identifier? = null): ConnectedTextureMap {
            faces.forEach { excludes.add(it) }
            replacement?.apply { faces.forEach { includes[it.asString()] = this } }
            return this
        }

        fun include(key: String, texture: Identifier): ConnectedTextureMap {
            includes[key] = texture
            return this
        }

        fun getKey(face: Direction?, segment: String? = if (face == null) null else "base", index: Int = 0): String {
            val key = keyCache.getOrPut(face to index, when (face) {
                null -> { { particles.filterIndexed { i, s -> particlePredicates[i](index) }.lastOrNull() ?: sets.filterIndexed { i, s -> faces[i].contains(face) && predicates[i](index) }.map { Identifier(it.namespace, "${it.path}_${segment ?: "base"}") }.last() } }
                else -> { { sets.filterIndexed { i, s -> faces[i].contains(face) && predicates[i](index) }.last() } }
            })
            return if (face == null) key.toString() else key.toString().plus("_").plus(segment!!)
        }

        fun get(face: Direction?, segment: String? = if (face == null) null else "base", index: Int = 0) = bakedSprites[getKey(face, segment, index)]!!

        fun getBase(face: Direction, index: Int = 0) = get(face, "base", index)
        fun getSide(face: Direction, connection: Boolean, vertical: Boolean, index: Int = 0) = if (connection) getBase(face) else get(face, if (vertical) "lines_v" else "lines_h", index)
        fun getCorner(face: Direction, connectionV: Boolean, connectionH: Boolean, connectionVH: Boolean, index: Int = 0) = when {
            connectionVH && connectionV && connectionH -> getBase(face, index)
            !connectionVH && connectionV && connectionH -> get(face, "corners_i", index)
            connectionV -> get(face, "lines_v", index)
            connectionH -> get(face, "lines_h", index)
            else -> get(face, "corners_o", index)
        }
        fun getParticle(index: Int = 0) = get(null, null, index)

        val map by lazy {
            sets.distinct().flatMap { s -> segments.map { "${s}_$it" to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier(s.namespace, "block/${s.path}_$it")) } }.toMap().plus(particles.distinct().map { it.toString() to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier(it.namespace, "block/${it.path}")) }).plus(includes.mapValues { SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, it.value) })
        }

    }

    protected inner class FaceConnectionMap {

        private lateinit var _face: Direction
        private lateinit var _facePerps: List<Direction>
        private lateinit var _renderView: BlockRenderView
        private lateinit var _state: BlockState
        private lateinit var _pos: BlockPos
        private lateinit var _connector: (world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) -> Boolean

        private val cache = mutableMapOf<String, Boolean>()
        val up get() = cache.getOrPut("up", { connection(_face, _facePerps[0]) })
        val right get() = cache.getOrPut("right", { connection(_face, _facePerps[1]) })
        val down get() = cache.getOrPut("down", { connection(_face, _facePerps[2]) })
        val left get() = cache.getOrPut("left", { connection(_face, _facePerps[3]) })
        val upLeft get() = cache.getOrPut("upLeft", { connection(_face, _facePerps[0], _facePerps[3]) })
        val upRight get() = cache.getOrPut("upRight", { connection(_face, _facePerps[1], _facePerps[0]) })
        val downRight get() = cache.getOrPut("downRight", { connection(_face, _facePerps[2], _facePerps[1]) })
        val downLeft get() = cache.getOrPut("downLeft", { connection(_face, _facePerps[3], _facePerps[2]) })

        val front get() = cache.getOrPut("front", { connection(null, _face) })
        val back get() = cache.getOrPut("back", { connection(null, _face.opposite) })
        val frontLeft get() = cache.getOrPut("frontLeft", { connection(null, _face, _facePerps[3]) })
        val backLeft get() = cache.getOrPut("backLeft", { connection(null, _face.opposite, _facePerps[3]) })
        val frontRight get() = cache.getOrPut("frontRight", { connection(null, _face, _facePerps[1]) })
        val backRight get() = cache.getOrPut("backRight", { connection(null, _face.opposite, _facePerps[1]) })

        val allSides get() = up && right && down && left

        fun reload(face: Direction, renderView: BlockRenderView, state: BlockState, pos: BlockPos, connector: (world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) -> Boolean = this@ConnectedTextureModel.connector) {
            _face = face
            _facePerps = face.perpendiculars
            _renderView = renderView
            _state = state
            _pos = pos
            _connector = connector
            cache.clear()
        }

        private fun connection(innerSeamPath: Direction?, vararg path: Direction): Boolean {
            bp.set(_pos).apply { path.map { this.move(it) } }
            return try {
                if (innerSeamPath != null) {
                    bp2.set(bp).move(innerSeamPath)
                    if (innerSeams && connector(_renderView, _state, _pos, _renderView.getBlockState(bp2), bp2, arrayOf(innerSeamPath))) return false
                }
                connector(_renderView, _state, _pos, _renderView.getBlockState(bp), bp, path.toList().toTypedArray())
            } catch (exception: ArrayIndexOutOfBoundsException) {
                false
            }
        }

    }

}