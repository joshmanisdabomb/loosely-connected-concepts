package com.joshmanisdabomb.lcc.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.block.ShatteredGlassBlock
import com.joshmanisdabomb.lcc.block.ShatteredPaneBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.mixin.content.common.PlayerEntityAccessor
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.AbstractGlassBlock
import net.minecraft.block.Block
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
import net.minecraft.item.ItemUsageContext
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CrowbarItem(settings: Settings) : Item(settings), LCCContentItemTrait {

    val shatters by lazy { (LCCBlocks.all.values.filterIsInstance<ShatteredGlassBlock>().map { it.unbroken to (it as Block) } + LCCBlocks.all.values.filterIsInstance<ShatteredPaneBlock>().map { it.unbroken to (it as Block) }).toMap() }

    val modifiers = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 2.0, EntityAttributeModifier.Operation.ADDITION))
        .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.55, EntityAttributeModifier.Operation.ADDITION))
        .build()

    override fun lcc_content_isEffectiveWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = effectivity == ToolEffectivity.WASTELAND

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity) = !miner.isCreative

    override fun isSuitableFor(state: BlockState): Boolean {
        val block = state.block
        return state.isOf(LCCBlocks.improvised_explosive) || block is AbstractGlassBlock
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        return if (isSuitableFor(state)) 32.0f else super.getMiningSpeedMultiplier(stack, state)
    }

    override fun lcc_content_isEffectiveTool(stack: ItemStack, state: BlockState, effectivity: ToolEffectivity): Boolean {
        return isSuitableFor(state) && effectivity == ToolEffectivity.WASTELAND
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return when (slot) {
            EquipmentSlot.MAINHAND -> modifiers
            else -> super.getAttributeModifiers(slot)
        }
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.damage(1, attacker) { it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        if ((attacker as? PlayerEntityAccessor)?.lastHitCritical == true) {
            target.addStatusEffect(StatusEffectInstance(LCCEffects.stun, (target is PlayerEntity).transformInt(6, 14)))
            (attacker as? ServerPlayerEntity)?.networkHandler?.sendPacket(GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F))
        }
        return true
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        if (world.isClient || state.getHardness(world, pos) == 0.0f) return true
        stack.damage(2, miner) { it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        return super.postMine(stack, world, state, pos, miner)
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val pos = context.blockPos
        val state = world.getBlockState(pos)
        val shattered = shatters[state.block] ?: return ActionResult.PASS
        val state2 = shattered.getStateWithProperties(state)
        if (!world.isClient) world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state))
        world.setBlockState(pos, state2)
        context.player?.also { context.stack.damage(1, it) { it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) } }
        return ActionResult.SUCCESS
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack) = ingredient.isOf(LCCItems.iron_oxide) || super.canRepair(stack, ingredient)

    companion object {
        val salvage = BooleanProperty.of("salvage")
    }

}