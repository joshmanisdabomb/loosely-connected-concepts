package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.renderer.v1.RendererAccess
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
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

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        Direction.values().forEach {
            renderContext.emitter
                .cubeFace(it, pos1, pos2) {
                    it[2] = it[2].coerceAtMost(it[0] + 0.25f)
                    it[3] = it[3].coerceAtMost(it[1] + 0.25f)
                }
                .spriteBake(0, map.get("top", "base"), MutableQuadView.BAKE_LOCK_UV)
                .spriteColor(0, -1, -1, -1, -1)
                .material(RendererAccess.INSTANCE.renderer!!.materialFinder().find())
                .emit()
        }
    }

    override fun emitItemQuads(stack: ItemStack, random: Supplier<Random>, renderContext: RenderContext) {

    }

    override fun getSprite() = map.particleTexture

    companion object {
        val segments = listOf("base", "corners_i", "corners_o", "lines_h", "lines_v")
        val sides = listOf("top", "bottom", "north", "east", "south", "west")
    }

    inner class ConnectedTextureMap internal constructor(val default: String) {

        private val raw = sides.map { it to default }.toMap().toMutableMap()
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
        fun get(side: String, segment: String) = bakedSprites[getKey(side, segment)]
        val particleTexture by lazy { bakedSprites.getOrDefault("particle", bakedSprites[particle]) }

        val prefixId by lazy { raw.values.distinct() }
        val sideId by lazy { raw.mapValues { (_, v) -> prefixId.indexOf(v) } }
        //val bindings by lazy { raw.toList().groupBy { it.second }.mapValues { (_, v) -> v.map { it.first } }.mapKeys { (k, _) -> textureGroupIds.indexOf(k) } }
        val map: Map<String, SpriteIdentifier> by lazy {
            val m = raw.toList().distinctBy { it.second }.flatMap(::expand).map { it.first to SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/${it.second}")) }.toMap().toMutableMap()
            if (!m.containsKey(particle)) m["particle"] = SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, LCC.id("block/$particle"))
            m.toMap()
        }

        private fun expand(side: Pair<String, String>) = segments.map { sideId[side.first].toString().plus("_$it") to side.second.plus("_$it") }

    }

}