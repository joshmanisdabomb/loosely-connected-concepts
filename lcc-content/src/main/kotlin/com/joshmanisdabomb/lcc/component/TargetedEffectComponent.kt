package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.extensions.forEachStringUuidList
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import com.joshmanisdabomb.lcc.extensions.putStringUuidList
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.*

class TargetedEffectComponent(private val entity: LivingEntity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent {

    protected val targetsPermanent = mutableMapOf<StatusEffect, MutableList<UUID>>()
    protected var targetsTemporary: MutableMap<StatusEffect, MutableList<Int>>? = mutableMapOf()

    operator fun get(effect: StatusEffect) = targetsTemporary?.get(effect)?.map(entity.world::getEntityById)
    fun getFirstAliveTarget(effect: StatusEffect) = targetsTemporary?.get(effect)?.firstOrNull()?.let(entity.world::getEntityById)

    operator fun set(effect: StatusEffect, entity: LivingEntity) {
        targetsTemporary?.set(effect, mutableListOf(entity.id))
        targetsPermanent[effect] = mutableListOf(entity.uuid)
    }

    fun add(effect: StatusEffect, entity: LivingEntity) {
        targetsTemporary?.getOrPut(effect) { mutableListOf() }?.add(entity.id)
        targetsPermanent.getOrPut(effect) { mutableListOf() }.add(entity.uuid)
    }

    override fun readFromNbt(tag: NbtCompound) {
        targetsTemporary = null
        targetsPermanent.clear()
        tag.getCompound("Targets").forEachStringUuidList { key, value ->
            val effect = Registry.STATUS_EFFECT.get(Identifier(key)) ?: return@forEachStringUuidList
            targetsPermanent[effect] = value.toMutableList()
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.modifyCompound("Targets") {
            targetsPermanent.forEach { (k, v) ->
                putStringUuidList(Registry.STATUS_EFFECT.getId(k).toString(), v)
            }
        }
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        targetsTemporary = buf.readMap(::LinkedHashMap, { Registry.STATUS_EFFECT.get(it.readIdentifier()) }, { it.readIntArray().toMutableList() })
    }

    override fun writeSyncPacket(buf: PacketByteBuf, recipient: ServerPlayerEntity) {
        buf.writeMap(targetsTemporary ?: mutableMapOf(), { b, e -> b.writeIdentifier(Registry.STATUS_EFFECT.getId(e)) }, { b, i -> b.writeIntArray(i.toIntArray()) })
    }

    override fun shouldSyncWith(player: ServerPlayerEntity) = entity == player

    override fun tick() {
        targetsPermanent.entries.removeIf { (k, v) ->
            if (!entity.hasStatusEffect(k)) {
                targetsTemporary?.remove(k)
                true
            } else {
                false
            }
        }
    }

    override fun serverTick() {
        if (targetsTemporary == null) {
            val sworld = entity.world as ServerWorld
            targetsTemporary = targetsPermanent.mapValues { (k, v) -> v.mapNotNull { sworld.getEntity(it)?.id }.toMutableList() }.toMutableMap()
        }
        tick()
    }

}
