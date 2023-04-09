package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.extensions.forEachByte
import com.joshmanisdabomb.lcc.extensions.getStringList
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import com.joshmanisdabomb.lcc.extensions.putStringList
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.StringIdentifiable

class PortalChargeComponent(private val entity: Entity) : ComponentV3, CommonTickingComponent {

    private val ticks = mutableMapOf<PortalType, Byte>()
    private val change = mutableSetOf<PortalType>()

    fun tick(type: PortalType) {
        if (change.contains(type)) return
        ticks.compute(type) { k, v -> v?.inc() ?: 0 }
        change.add(type)
    }

    fun reset(type: PortalType) {
        ticks.remove(type)
        change.add(type)
    }

    fun get(type: PortalType) = ticks.getOrDefault(type, 0)

    override fun readFromNbt(tag: NbtCompound) {
        ticks.clear()
        tag.getCompound("Ticks").forEachByte { k, v ->
            ticks[PortalType.get(k)!!] = v
        }

        change.clear()
        change.addAll(tag.getStringList("Change").mapNotNull(PortalType::get))
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.modifyCompound("Ticks", NbtCompound()) {
            ticks.forEach { (k, v) -> this.putByte(k.asString(), v) }
        }
        tag.putStringList("Change", change.map(PortalType::asString))
    }

    override fun tick() {
        ticks.replaceAll { k, v ->
            if (change.contains(k)) return@replaceAll v
            v.dec().coerceAtLeast(0)
        }
        change.clear()
    }

    enum class PortalType : StringIdentifiable {
        RAINBOW;

        override fun asString() = name.lowercase()

        companion object {
            fun get(type: String) = values().associateBy { it.asString() }[type]
        }
    }

}