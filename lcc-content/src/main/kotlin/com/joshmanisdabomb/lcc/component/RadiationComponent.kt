package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.effect.RadiationStatusEffect
import com.joshmanisdabomb.lcc.extensions.isSurvival
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.MathHelper
import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

class RadiationComponent(private val entity: LivingEntity) : ComponentV3, ServerTickingComponent, CommonTickingComponent, AutoSyncedComponent {

    var exposure = 0f

    private var lastHealthMod = 0
    val healthMod get() = MathHelper.floor(exposure.div(2f)).times(2)

    override fun readFromNbt(tag: NbtCompound) {
        exposure = tag.getFloat("Exposure")
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.putFloat("Exposure", exposure)
    }

    override fun writeSyncPacket(buf: PacketByteBuf, player: ServerPlayerEntity) {
        writeSyncPacketExtra(buf, player, false)
    }

    private fun writeSyncPacketExtra(buf: PacketByteBuf, player: ServerPlayerEntity, extra: Boolean) {
        buf.writeFloat(exposure)
        buf.writeFloat(if (extra) -1f else exposureLimit)
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        exposure = buf.readFloat()
        val limit = buf.readFloat()
        if (limit < 0f || exposure <= limit) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.also {
                val new = EntityAttributeModifier(uuid, modifierName, -healthMod.toDouble(), EntityAttributeModifier.Operation.ADDITION)
                it.removeModifier(uuid)
                it.addPersistentModifier(new)
            }
        }
    }

    override fun tick() {
        if ((entity as? PlayerEntity)?.isSurvival == false) return
        if (entity.statusEffects.none { it.effectType is RadiationStatusEffect }) {
            exposure = exposure.minus(0.00011f).coerceAtLeast(0f)
        }
    }

    override fun serverTick() {
        if ((entity as? PlayerEntity)?.isSurvival == false) return
        LCCComponents.nuclear.maybeGet(entity.world).orElse(null)?.also {
            var amp = -1
            var time = 1
            for ((p, r, t) in it.strikes) {
                if (p.isWithinDistance(entity.pos, r+20.0)) {
                    val distance = sqrt(p.getSquaredDistance(entity.pos, false))
                    amp = max(amp, 1.minus(distance / r.plus(20.0)).times(5).toInt())
                    time += 4.minus((time - t).div(1000000)).coerceIn(0, 4).toInt()
                }
            }
            if (amp > -1 && time > 1) NuclearUtil.addRadiation(entity, time, amp)
        }
        if (lastHealthMod != healthMod) {
            if (healthMod < lastHealthMod || exposure <= exposureLimit) {
                entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.also {
                    val new = EntityAttributeModifier(uuid, modifierName, -healthMod.toDouble(), EntityAttributeModifier.Operation.ADDITION)
                    it.removeModifier(uuid)
                    it.addPersistentModifier(new)
                    if (entity.health > entity.maxHealth) {
                        entity.damage(LCCDamage.radiation, 0.00001f)
                        entity.health = entity.maxHealth
                        entity.hurtTime = 0
                        entity.timeUntilRegen = 0
                    }
                }
            }
            LCCComponents.radiation.sync(entity) { buf, player -> writeSyncPacketExtra(buf, player, healthMod < lastHealthMod) }
        }
        lastHealthMod = healthMod
    }

    val exposureLimit get() = 18f//.plus(LCCComponents.hearts.maybeGet(entity).map { it.getMaxHealth(HeartType.RED) }.orElse(0f))

    companion object {
        val uuid by lazy { UUID.fromString("e322834e-028f-481c-bc3e-53f0065bb8ec") }
        const val modifierName = "LCC Radiation Exposure"
    }

}