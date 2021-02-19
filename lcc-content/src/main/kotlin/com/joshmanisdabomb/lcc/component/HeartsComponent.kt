package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.CopyableComponent
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity

class HeartsComponent(private val entity: LivingEntity) : ComponentV3, AutoSyncedComponent, CommonTickingComponent, CopyableComponent<HeartsComponent>, PlayerComponent<HeartsComponent> {

    protected val health = mutableMapOf<HeartType, Float>()
    protected val maxHealth = mutableMapOf<HeartType, Float>()

    var crystalRegenAmount = 0f
    var crystalRegenTick = 0L

    operator fun get(type: HeartType) = getHealth(type)
    operator fun set(type: HeartType, amount: Float) = setHealth(type, amount)

    fun getHealth(type: HeartType) = health.getOrPut(type) { 0f }
    fun setHealth(type: HeartType, amount: Float) { health[type] = amount }

    fun getMaxHealth(type: HeartType) = maxHealth.getOrPut(type) { 0f }
    fun setMaxHealth(type: HeartType, amount: Float) { maxHealth[type] = amount }

    override fun readFromNbt(tag: CompoundTag) {
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
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.build("Values", ref = CompoundTag()) {
            health.forEach { (k, v) -> putFloat(k.tag, v) }
        }
        tag.build("MaxValues", ref = CompoundTag()) {
            maxHealth.forEach { (k, v) -> putFloat(k.tag, v) }
        }
        tag.putFloat("CrystalRegenAmount", crystalRegenAmount)
        tag.putLong("CrystalRegenTick", crystalRegenTick)
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        super.applySyncPacket(buf)
        HeartType.values().forEach { it.update(entity, health[it] ?: 0f, maxHealth[it] ?: 0f) }
    }

    override fun shouldSyncWith(player: ServerPlayerEntity): Boolean {
        return entity == player
    }

    override fun tick() {
        var sync = false
        for (it in HeartType.values()) {
            sync = it.tick(entity) || sync
        }
        if (sync && !entity.world.isClient) LCCComponents.hearts.sync(entity)
    }

    override fun shouldCopyForRespawn(lossless: Boolean, keepInventory: Boolean) = true

    override fun copyForRespawn(original: HeartsComponent, lossless: Boolean, keepInventory: Boolean) {
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
