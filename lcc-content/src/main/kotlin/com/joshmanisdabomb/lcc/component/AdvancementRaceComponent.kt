package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.directory.LCCCriteria
import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import net.minecraft.advancement.Advancement
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.world.WorldProperties
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class AdvancementRaceComponent(private val properties: WorldProperties) : ComponentV3 {

    val map = ConcurrentHashMap<Identifier, UUID>()

    fun check(player: ServerPlayerEntity, advancement: Advancement) {
        map.computeIfAbsent(advancement.id) {
            LCCCriteria.race.trigger(player, advancement)
            player.uuid
        }
    }

    override fun readFromNbt(tag: NbtCompound) {
        map.clear()
        tag.getCompound("Advancements").apply {
            keys.forEach { map[Identifier(it)] = getUuid(it) }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.build("Advancements", NbtCompound()) {
            map.forEach { (k, v) -> putUuid(k.toString(), v) }
        }
    }

}