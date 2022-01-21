package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.forEachStringUuidList
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import com.joshmanisdabomb.lcc.extensions.putStringUuidList
import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.player.PlayerEntity
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
    fun getFirstTarget(effect: StatusEffect, predicate: StatusEffect.(entity: Entity) -> Boolean = { (effect as? LCCContentEffectTrait)?.lcc_content_canTarget(entity, it) == true }) = targetsTemporary?.get(effect)?.firstNotNullOfOrNull { entity.world.getEntityById(it)?.takeIf { effect.predicate(it) } }

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
            if (!entity.hasStatusEffect(k) || v.isEmpty()) {
                targetsTemporary?.remove(k)
                true
            } else {
                false
            }
        }
    }

    override fun serverTick() {
        val sworld = entity.world as ServerWorld
        if (entity is PlayerEntity && targetsTemporary == null) {
            targetsTemporary = targetsPermanent.mapValues { (k, v) -> v.mapNotNull { sworld.getEntity(it)?.id }.toMutableList() }.toMutableMap()
            LCCComponents.targeted_effects.sync(entity)
        }
        tick()
        var sync = false
        targetsTemporary?.entries?.forEach { (k, v) ->
            v.removeIf entity@{
                val other = sworld.getEntityById(it) ?: return@entity false
                if ((k as? LCCContentEffectTrait)?.lcc_content_canTarget(entity, other) != true) {
                    targetsPermanent[k]?.remove(other.uuid)
                    sync = true
                    true
                } else {
                    false
                }
            }
        }
        if (sync) LCCComponents.targeted_effects.sync(entity)
    }

}
