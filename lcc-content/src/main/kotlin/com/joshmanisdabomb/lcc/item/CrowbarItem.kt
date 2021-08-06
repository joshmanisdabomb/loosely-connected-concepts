package com.joshmanisdabomb.lcc.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.mixin.content.common.PlayerEntityAccessor
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrowbarItem(settings: Settings) : Item(settings), LCCContentItemTrait {

    val modifiers = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 4.0, EntityAttributeModifier.Operation.ADDITION))
        .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.37, EntityAttributeModifier.Operation.ADDITION))
        .build()

    override fun lcc_content_isEffectiveWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = effectivity == ToolEffectivity.WASTELAND

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity) = !miner.isCreative

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return when (slot) {
            EquipmentSlot.MAINHAND -> modifiers
            else -> super.getAttributeModifiers(slot)
        }
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.damage(1, attacker) { it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        if ((attacker as? PlayerEntityAccessor)?.lastHitCritical == true) {
            target.addStatusEffect(StatusEffectInstance(LCCEffects.stun, (target is PlayerEntity).transformInt(2, 10)))
            (attacker as? ServerPlayerEntity)?.networkHandler?.sendPacket(GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F))
        }
        return true
    }

}