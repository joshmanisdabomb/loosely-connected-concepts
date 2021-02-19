package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.effect.HurtResistanceStatusEffect
import com.joshmanisdabomb.lcc.event.DamageEntityCallback
import com.joshmanisdabomb.lcc.event.InteractEntityCallback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.player.*
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult

object LCCEvents : BasicDirectory<Any, Unit>() {

    val gauntletAttackDeny by entry({ i, c, p -> initialiser(i, c, p, AttackEntityCallback.EVENT) }) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletUseEntityDeny by entry({ i, c, p -> initialiser(i, c, p, UseEntityCallback.EVENT) }) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletInteractEntityDeny by entry({ i, c, p -> initialiser(i, c, p, InteractEntityCallback.EVENT) }) { InteractEntityCallback { player, world, hand, entity -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }

    val stunAttackDeny by entry({ i, c, p -> initialiser(i, c, p, AttackEntityCallback.EVENT) }) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunAttackBlockDeny by entry({ i, c, p -> initialiser(i, c, p, AttackBlockCallback.EVENT) }) { AttackBlockCallback { player, world, hand, pos, direction -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseBlockDeny by entry({ i, c, p -> initialiser(i, c, p, UseBlockCallback.EVENT) }) { UseBlockCallback { player, world, hand, blockHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseEntityDeny by entry({ i, c, p -> initialiser(i, c, p, UseEntityCallback.EVENT) }) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }
    val stunUseItemDeny by entry({ i, c, p -> initialiser(i, c, p, UseItemCallback.EVENT) }) { UseItemCallback { player, world, hand -> if (player.hasStatusEffect(LCCEffects.stun)) TypedActionResult.fail(player.getStackInHand(hand)) else TypedActionResult.pass(player.getStackInHand(hand)) } }
    val stunInteractEntityDeny by entry({ i, c, p -> initialiser(i, c, p, InteractEntityCallback.EVENT) }) { InteractEntityCallback { player, world, hand, entity -> if (player.hasStatusEffect(LCCEffects.stun)) ActionResult.FAIL else ActionResult.PASS } }

    val hurtResistanceStatusEffect by entry({ i, c, p -> initialiser(i, c, p, DamageEntityCallback.EVENT) }) { DamageEntityCallback { entity, source, initial, original ->
        entity.statusEffects.filter { it.effectType is HurtResistanceStatusEffect }.forEach {
            entity.hurtTime = entity.hurtTime.coerceAtMost(entity.hurtTime.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
            entity.timeUntilRegen = entity.timeUntilRegen.coerceAtMost(entity.timeUntilRegen.times((it.effectType as HurtResistanceStatusEffect).getResistanceMultiplier(source, it.amplifier)).toInt())
        }
        initial
    } }

    fun <E> initialiser(input: E, context: DirectoryContext<Unit>, parameters: Any, registry: Event<E>): E = input.also { registry.register(input) }

    override fun defaultProperties(name: String) = Unit

}