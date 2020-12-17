package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.effect.HurtResistanceStatusEffect
import com.joshmanisdabomb.lcc.event.DamageEntityCallback
import com.joshmanisdabomb.lcc.event.InteractEntityCallback
import net.fabricmc.fabric.api.event.player.*
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult

object LCCEvents : ThingDirectory<Any, Function1<*, Unit>>() {

    val gauntletAttackDeny by create(AttackEntityCallback.EVENT::register) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletUseEntityDeny by create(UseEntityCallback.EVENT::register) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletInteractEntityDeny by create(InteractEntityCallback.EVENT::register) { InteractEntityCallback { player, world, hand, entity -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }

    val stunAttackDeny by create(AttackEntityCallback.EVENT::register) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunAttackBlockDeny by create(AttackBlockCallback.EVENT::register) { AttackBlockCallback { player, world, hand, pos, direction -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseBlockDeny by create(UseBlockCallback.EVENT::register) { UseBlockCallback { player, world, hand, blockHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseEntityDeny by create(UseEntityCallback.EVENT::register) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseItemDeny by create(UseItemCallback.EVENT::register) { UseItemCallback { player, world, hand -> if (player.hasStatusEffect(LCCEffects.stun)) TypedActionResult.fail(player.getStackInHand(hand)) else TypedActionResult.pass(player.getStackInHand(hand)) } }
    val stunInteractEntityDeny by create(InteractEntityCallback.EVENT::register) { InteractEntityCallback { player, world, hand, entity -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }

    val hurtResistanceStatusEffect by create(DamageEntityCallback.EVENT::register) { DamageEntityCallback { entity, source, initial, original ->
        entity.statusEffects.filter { it.effectType is HurtResistanceStatusEffect }.forEach {
            entity.hurtTime = entity.hurtTime.coerceAtMost(entity.hurtTime.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
            entity.timeUntilRegen = entity.timeUntilRegen.coerceAtMost(entity.timeUntilRegen.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
        }
        initial
    } }

    override fun registerAll(things: Map<String, Any>, properties: Map<String, Function1<*, Unit>>) {
        for ((k, event) in things) {
            (properties[k] as Function1<Any, Unit>).invoke(event)
        }
    }

}