package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.effect.RadiationStatusEffect
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.MathHelper
import java.util.*

class RadiationComponent(private val entity: LivingEntity) : ComponentV3, ServerTickingComponent, AutoSyncedComponent {

    private var _exposure = 0f
    val exposure get() = _exposure

    val healthMod get() = MathHelper.floor(exposure.div(2f)).times(2)

    val uuid by lazy { UUID.fromString("e322834e-028f-481c-bc3e-53f0065bb8ec") }

    override fun readFromNbt(tag: CompoundTag) {
        _exposure = tag.getFloat("Exposure")
    }

    override fun writeToNbt(tag: CompoundTag) {
        tag.putFloat("Exposure", _exposure)
    }

    override fun writeSyncPacket(buf: PacketByteBuf, player: ServerPlayerEntity) {
        writeSyncPacketExtra(buf, player, false)
    }

    private fun writeSyncPacketExtra(buf: PacketByteBuf, player: ServerPlayerEntity, extra: Boolean) {
        super.writeSyncPacket(buf, player)
        buf.writeBoolean(extra)
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        super.applySyncPacket(buf)
        if (buf.readBoolean() || entity.maxHealth > 6f) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.also {
                val new = EntityAttributeModifier(uuid, "LCC Radiation Exposure", -healthMod.toDouble(), EntityAttributeModifier.Operation.ADDITION)
                it.removeModifier(uuid)
                it.addPersistentModifier(new)
            }
        }
    }

    override fun serverTick() {
        if (entity is PlayerEntity) {
            println(exposure)
            println(healthMod)
        }
        val lastHealth = healthMod
        entity.statusEffects.filter { it.effectType is RadiationStatusEffect }.let {
            if (it.isEmpty()) {
                _exposure = _exposure.minus(0.00019f).coerceAtLeast(0f)
                return@let null
            }
            return@let it
        }?.forEach {
            _exposure = (it.effectType as RadiationStatusEffect).modifyExposure(_exposure, entity, it.amplifier)
        }
        if (lastHealth != healthMod) {
            if (healthMod < lastHealth || entity.maxHealth > 6f) {
                entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.also {
                    val new = EntityAttributeModifier(uuid, "LCC Radiation Exposure", -healthMod.toDouble(), EntityAttributeModifier.Operation.ADDITION)
                    it.removeModifier(uuid)
                    it.addPersistentModifier(new)
                    if (entity.health > entity.maxHealth) {
                        entity.health = entity.maxHealth
                        entity.damage(LCCDamage.radiation, 0.00001f)
                        entity.hurtTime = 0
                        entity.timeUntilRegen = 0
                    }
                }
            }
            LCCComponents.radiation.sync(entity) { buf, player -> writeSyncPacketExtra(buf, player, healthMod < lastHealth) }
        }
    }

}