package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.directory.LCCPacketsClient
import com.joshmanisdabomb.lcc.directory.LCCPacketsServer
import com.joshmanisdabomb.lcc.directory.LCCPacketsServer.id
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation.MULTIPLY_TOTAL
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf

class StunStatusEffect(type: StatusEffectType, color: Int) : StatusEffect(type, color) {

    init {
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", -1.0, MULTIPLY_TOTAL)
    }

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

    override fun adjustModifierAmount(amplifier: Int, modifier: EntityAttributeModifier) = modifier.value

}
