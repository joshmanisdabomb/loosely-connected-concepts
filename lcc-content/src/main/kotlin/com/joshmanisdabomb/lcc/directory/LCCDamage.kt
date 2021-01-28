package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.LCCDamageSource
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.entity.player.PlayerEntity

object LCCDamage : ThingDirectory<DamageSource, Unit>() {

    val gauntlet_punch_wall by createWithName { LCCDamageSource(id(it), bypassArmor = true, unblockable = true) }

    fun gauntletUppercut(player: PlayerEntity) = EntityDamageSource(id("gauntlet_uppercut"), player)
    fun gauntletPunch(player: PlayerEntity) = EntityDamageSource(id("gauntlet_punch"), player)

    fun nuke(attacker: LivingEntity?): DamageSource {
        return if (attacker != null) EntityDamageSource(id("nuke.player"), attacker).setExplosive() else LCCDamageSource(id("nuke")).setExplosive()
    }

    val heated by createWithName { LCCDamageSource(id(it), fire = true) }
    val boiled by createWithName { LCCDamageSource(id(it), fire = true) }

    private fun id(name: String) = "${LCC.modid}.$name"

}