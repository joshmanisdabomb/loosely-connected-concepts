package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.modifyCompound
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity

class HeartsComponent(private val entity: LivingEntity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent, PlayerComponent<HeartsComponent> {

    protected val health = mutableMapOf<HeartType, Float>()
    protected val maxHealth = mutableMapOf<HeartType, Float>()

    var damageLayer = HeartType.RED

    var crystalRegenAmount = 0f
    var crystalRegenTick = 0L

    operator fun get(type: HeartType) = getHealth(type)
    operator fun set(type: HeartType, amount: Float) = setHealth(type, amount)

    fun getHealth(type: HeartType) = health.getOrPut(type) { 0f }
    fun setHealth(type: HeartType, amount: Float) {
        health[type] = amount
        calculateDamageLayer()
    }

    fun getMaxHealth(type: HeartType) = maxHealth.getOrPut(type) { 0f }
    fun setMaxHealth(type: HeartType, amount: Float) {
        maxHealth[type] = amount
        calculateDamageLayer()
    }

    private fun calculateDamageLayer() {
        damageLayer = try {
            health.filterValues { it > 0f }.toSortedMap(Comparator.comparingInt { it.sortOrder }).firstKey()
        } catch (e: NoSuchElementException) {
            HeartType.RED
        }
    }

    override fun readFromNbt(tag: NbtCompound) {
        health.clear()
        maxHealth.clear()
        tag.getCompound("Values").apply {
            HeartType.values().forEach { health[it] = this.getFloat(it.tag) }
        }
        tag.getCompound("MaxValues").apply {
            HeartType.values().forEach { maxHealth[it] = this.getFloat(it.tag) }
        }
        crystalRegenAmount = tag.getFloat("CrystalRegenAmount")
        crystalRegenTick = tag.getLong("CrystalRegenTick")
        calculateDamageLayer()
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.modifyCompound("Values", ref = NbtCompound()) {
            health.forEach { (k, v) -> putFloat(k.tag, v) }
        }
        tag.modifyCompound("MaxValues", ref = NbtCompound()) {
            maxHealth.forEach { (k, v) -> putFloat(k.tag, v) }
        }
        tag.putFloat("CrystalRegenAmount", crystalRegenAmount)
        tag.putLong("CrystalRegenTick", crystalRegenTick)
    }

    override fun writeSyncPacket(buf: PacketByteBuf, recipient: ServerPlayerEntity) {
        if (entity == recipient) {
            buf.writeBoolean(true)
            super.writeSyncPacket(buf, recipient)
        } else {
            buf.writeBoolean(false)
            buf.writeByte(damageLayer.ordinal)
        }
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        if (buf.readBoolean()) {
            super.applySyncPacket(buf)
            calculateDamageLayer()
            HeartType.values().forEach { it.update(entity, health[it] ?: 0f, maxHealth[it] ?: 0f) }
        } else {
            damageLayer = HeartType.values().getOrElse(buf.readByte().toInt()) { HeartType.RED }
        }
    }

    override fun tick() {
        var sync = false
        for (it in HeartType.values()) {
            sync = it.tick(entity) || sync
        }
        if (!entity.world.isClient && sync) {
            calculateDamageLayer()
            LCCComponents.hearts.sync(entity)
        }
    }

    override fun shouldCopyForRespawn(lossless: Boolean, keepInventory: Boolean, sameCharacter: Boolean) = true

    override fun copyForRespawn(original: HeartsComponent, lossless: Boolean, keepInventory: Boolean, sameCharacter: Boolean) {
        if (lossless || keepInventory) {
            health.clear()
            health.putAll(original.health)
            maxHealth.clear()
            maxHealth.putAll(original.maxHealth)
            if (lossless) {
                crystalRegenAmount = original.crystalRegenAmount
                crystalRegenTick = original.crystalRegenTick
            }
            HeartType.values().forEach { it.update(entity, health[it] ?: 0f, maxHealth[it] ?: 0f) }
        } else {
            health.clear()
            health.putAll(original.health.filterKeys { it.remember })
            maxHealth.clear()
            maxHealth.putAll(original.maxHealth.filterKeys { it.rememberMax })
            HeartType.values().filter { it.remember || it.rememberMax }.forEach { it.update(entity, health[it] ?: 0f, maxHealth[it] ?: 0f) }
        }
    }

}
