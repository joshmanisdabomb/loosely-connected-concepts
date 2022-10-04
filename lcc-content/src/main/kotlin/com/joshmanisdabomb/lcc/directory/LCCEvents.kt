package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.block.WastelandRustingBlock
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.effect.HurtResistanceStatusEffect
import com.joshmanisdabomb.lcc.event.DamageEntityCallback
import com.joshmanisdabomb.lcc.event.InteractEntityCallback
import com.joshmanisdabomb.lcc.event.MobSpawnCallback
import com.joshmanisdabomb.lcc.event.RandomTickCallback
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.modifyInt
import com.joshmanisdabomb.lcc.item.KnifeItem
import com.joshmanisdabomb.lcc.recipe.imbuing.ImbuingRecipe
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.player.*
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback
import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.passive.WanderingTraderEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult

object LCCEvents : BasicDirectory<Any, Unit>() {

    val gauntletAttackDeny by entry(AttackEntityCallback.EVENT.initialiser) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletUseEntityDeny by entry(UseEntityCallback.EVENT.initialiser) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletInteractEntityDeny by entry(InteractEntityCallback.EVENT.initialiser) { InteractEntityCallback { player, world, hand, entity -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }

    val stunAttackDeny by entry(AttackEntityCallback.EVENT.initialiser) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunAttackBlockDeny by entry(AttackBlockCallback.EVENT.initialiser) { AttackBlockCallback { player, world, hand, pos, direction -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseBlockDeny by entry(UseBlockCallback.EVENT.initialiser) { UseBlockCallback { player, world, hand, blockHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseEntityDeny by entry(UseEntityCallback.EVENT.initialiser) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseItemDeny by entry(UseItemCallback.EVENT.initialiser) { UseItemCallback { player, world, hand -> if (player.hasStatusEffect(LCCEffects.stun)) TypedActionResult.fail(player.getStackInHand(hand)) else TypedActionResult.pass(player.getStackInHand(hand)) } }
    val stunInteractEntityDeny by entry(InteractEntityCallback.EVENT.initialiser) { InteractEntityCallback { player, world, hand, entity -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }

    val hurtResistanceStatusEffect by entry(DamageEntityCallback.EVENT.initialiser) { DamageEntityCallback { entity, source, initial, original ->
        entity.statusEffects.filter { it.effectType is HurtResistanceStatusEffect }.forEach {
            entity.hurtTime = entity.hurtTime.coerceAtMost(entity.hurtTime.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
            entity.timeUntilRegen = entity.timeUntilRegen.coerceAtMost(entity.timeUntilRegen.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
        }
        initial
    } }

    val nuclear_winter_spawn by entry(MobSpawnCallback.EVENT.initialiser) { MobSpawnCallback { entity, world, difficulty, spawnReason, entityData, entityNbt ->
        val winter = LCCComponents.nuclear.getNullable(world.toServerWorld())?.winter ?: return@MobSpawnCallback
        if (winter >= 1f) {
            NuclearUtil.mobSpawned(entity, world, NuclearUtil.getWinterLevel(winter), difficulty, spawnReason, entityData, entityNbt)
        }
    } }

    val iron_rust by entry(RandomTickCallback.EVENT.initialiser) { RandomTickCallback { world, state, pos, random ->
        if (state.isOf(Blocks.IRON_BLOCK)) return@RandomTickCallback LCCBlocks.rusted_iron_blocks.values.filterIsInstance<WastelandRustingBlock>().first().rust(state, world, pos, random)?.also { world.setBlockState(pos, it) } != null
        return@RandomTickCallback false
    } }

    val knifeAttackKnockback by entry(AttackEntityCallback.EVENT.initialiser) { AttackEntityCallback { player, world, hand, entity, entityHitResult ->
        if (player.getStackInHand(hand).isOf(LCCItems.knife)) {
            val kbResistance = (entity as? LivingEntity)?.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ?: return@AttackEntityCallback ActionResult.PASS
            if (kbResistance.getModifier(KnifeItem.knockback_resistance_modifier_uuid) == null) {
                kbResistance.addTemporaryModifier(EntityAttributeModifier(KnifeItem.knockback_resistance_modifier_uuid, "Knife knockback resistance", 1.0, EntityAttributeModifier.Operation.ADDITION))
            }
        }
        ActionResult.PASS
    } }

    val imbueAttack by entry(AttackEntityCallback.EVENT.initialiser) { AttackEntityCallback { player, world, hand, entity, entityHitResult ->
        if (entity !is LivingEntity) return@AttackEntityCallback ActionResult.PASS
        val stack = player.getStackInHand(hand)
        if (!stack.isIn(LCCItemTags.imbuable)) return@AttackEntityCallback ActionResult.PASS
        val imbue = stack.getSubNbt("lcc-imbue")
        imbue?.keys?.forEach {
            val recipe = world.recipeManager.get(Identifier(it)).orElse(null) as? ImbuingRecipe ?: return@forEach imbue.remove(it)
            recipe.getEffects().forEach { entity.addStatusEffect(StatusEffectInstance(it)) }
            if (player.isSurvival) {
                val new = imbue.modifyInt(it) { this.dec() }
                if (new <= 0) imbue.remove(it)
            }
        }
        ActionResult.PASS
    } }

    val wastelandEquipment by entry(ModifyItemAttributeModifiersCallback.EVENT.initialiser) { ModifyItemAttributeModifiersCallback { stack, slot, modifiers ->
        val item = stack.item
        if (item is ArmorItem && item.slotType == slot && stack.isIn(LCCItemTags.wasteland_equipment)) {
            modifiers.put(LCCAttributes.wasteland_protection, EntityAttributeModifier(ToolEffectivity.protection_modifier, "Armor modifier", 0.25, EntityAttributeModifier.Operation.ADDITION))
        }
    } }

    val wanderingTraderBundle by entry(UseEntityCallback.EVENT.initialiser) { UseEntityCallback { player, world, hand, entity, hitResult ->
        val stack = player.getStackInHand(hand)
        if (stack.isOf(Items.BUNDLE) && entity is WanderingTraderEntity) {
            entity.convertTo(LCCEntities.traveller, false)
            return@UseEntityCallback ActionResult.SUCCESS
        }
        ActionResult.PASS
    } }

    val <E> Event<E>.initialiser get() = { i: E, c: DirectoryContext<Unit>, p: Any -> i.also { this.register(i) } }

    override fun defaultProperties(name: String) = Unit

}