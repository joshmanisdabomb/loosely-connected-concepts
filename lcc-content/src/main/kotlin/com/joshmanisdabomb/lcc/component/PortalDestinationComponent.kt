package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.extensions.getCompoundObjectList
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import com.joshmanisdabomb.lcc.extensions.putCompoundObjectList
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.World
import net.minecraft.world.WorldProperties

class PortalDestinationComponent(private val properties: WorldProperties) : ComponentV3 {

    private val rainbow_portals = mutableMapOf<String, List<PortalPosition>>()
    private val rainbow_broken_default_portals = mutableMapOf<String, List<PortalPosition>>()

    override fun readFromNbt(tag: NbtCompound) {
        rainbow_portals.clear()
        rainbow_broken_default_portals.clear()
        val rainbow = tag.getCompound("Rainbow")
        rainbow.getCompound("Portals").apply {
            keys.forEach { rainbow_portals[it] = getCompoundObjectList(it, map = ::PortalPosition) }
        }
        rainbow.getCompound("BrokenDefaults").apply {
            keys.forEach { rainbow_broken_default_portals[it] = getCompoundObjectList(it, map = ::PortalPosition) }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        val rainbow = NbtCompound()
        tag.modifyCompound("Portals", NbtCompound()) {
            rainbow_portals.forEach { (k, v) -> putCompoundObjectList(k.toString(), v, PortalPosition::toNbt) }
        }
        tag.modifyCompound("BrokenDefaults", NbtCompound()) {
            rainbow_broken_default_portals.forEach { (k, v) -> putCompoundObjectList(k.toString(), v, PortalPosition::toNbt) }
        }
        tag.put("Rainbow", rainbow)
    }

    fun getPositions(code: ByteArray): List<PortalPosition> {
        val key = code.joinToString(separator = "-")
        return (rainbow_portals[key] ?: emptyList()) + getDefaultPositions(code)
    }

    fun getDefaultPositions(code: ByteArray): List<PortalPosition> {
        return listOf(PortalPosition(RegistryKey.of(Registry.WORLD_KEY, LCC.id("rainbow")), BlockPos(0, 100, 0)))
    }

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