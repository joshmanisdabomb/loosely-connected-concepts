package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.CodedPortals
import com.joshmanisdabomb.lcc.extensions.*
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.World
import net.minecraft.world.WorldProperties

class PortalDestinationComponent(private val properties: WorldProperties) : ComponentV3 {

    private val rainbow_portals = mutableMapOf<String, List<PortalPosition>>()
    private var rainbow_chunks: MutableMap<String, ChunkPos>? = null
    private var rainbow_chunks_flipped: MutableMap<ChunkPos, String>? = null

    fun init(world: ServerWorld) {
        if (rainbow_chunks == null) {
            rainbow_chunks = CodedPortals.RainbowCodedPortals.getCodeMap(world.seed).toMutableMap()
            rainbow_chunks_flipped = rainbow_chunks?.flip()?.toMutableMap()
        }
    }

    override fun readFromNbt(tag: NbtCompound) {
        rainbow_portals.clear()
        rainbow_chunks?.clear()
        val rainbow = tag.getCompound("Rainbow")
        rainbow.getCompound("Portals").apply {
            keys.forEach { rainbow_portals[it] = getCompoundObjectList(it, map = ::PortalPosition) }
        }
        rainbow.getCompoundOrNull("Chunks")?.apply {
            if (rainbow_chunks == null) rainbow_chunks = mutableMapOf()
            keys.forEach { rainbow_chunks?.set(it, ChunkPos(getLong(it))) }
        }
        rainbow_chunks_flipped = rainbow_chunks?.flip()?.toMutableMap()
    }

    override fun writeToNbt(tag: NbtCompound) {
        val rainbow = NbtCompound()
        tag.modifyCompound("Portals", NbtCompound()) {
            rainbow_portals.forEach { (k, v) -> putCompoundObjectList(k, v, PortalPosition::toNbt) }
        }
        if (rainbow_chunks != null) {
            tag.modifyCompound("Chunks", NbtCompound()) {
                rainbow_chunks?.forEach { (k, v) -> putLong(k, v.toLong()) }
            }
        }
        tag.put("Rainbow", rainbow)
    }

    fun getPositions(code: String): List<PortalPosition> {
        return (rainbow_portals[code] ?: emptyList()) + getDefaultPositions(code)
    }

    fun getDefaultPositions(code: String): List<PortalPosition> {
        val position = rainbow_chunks?.get(code)?.getCenterAtY(100) ?: return emptyList()
        return listOf(PortalPosition(RegistryKey.of(Registry.WORLD_KEY, LCC.id("rainbow")), position))
    }

    fun getCode(chunkPos: ChunkPos) = rainbow_chunks_flipped?.get(chunkPos)

    data class PortalPosition(val dimension: RegistryKey<World>, val pos: BlockPos) {

        constructor(nbt: NbtCompound) : this(RegistryKey.of(Registry.WORLD_KEY, Identifier(nbt.getString("Dimension"))), NbtHelper.toBlockPos(nbt.getCompound("Position")))

        fun toNbt(): NbtCompound {
            val nbt = NbtCompound()
            nbt.putString("Dimension", dimension.value.toString())
            nbt.put("Position", NbtHelper.fromBlockPos(pos))
            return nbt
        }

    }

}