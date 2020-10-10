package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.events.InteractEntityCallback
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.minecraft.util.ActionResult

object LCCEvents : ThingDirectory<Any, Function1<*, Unit>>() {

    val gauntletAttackDeny by create(AttackEntityCallback.EVENT::register) { AttackEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletUseEntityDeny by create(UseEntityCallback.EVENT::register) { UseEntityCallback { player, world, hand, entity, entityHitResult -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }
    val gauntletInteractEntityDeny by create(InteractEntityCallback.EVENT::register) { InteractEntityCallback { player, world, hand, entity -> if (player.mainHandStack?.item == LCCItems.gauntlet) ActionResult.FAIL else ActionResult.PASS } }

    override fun registerAll(things: Map<String, Any>, properties: Map<String, Function1<*, Unit>>) {
        for ((k, event) in things) {
            (properties[k] as Function1<Any, Unit>).invoke(event)
        }
    }

}