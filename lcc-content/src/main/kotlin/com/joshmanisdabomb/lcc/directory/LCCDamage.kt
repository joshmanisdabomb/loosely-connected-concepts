package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.LCCDamageSource
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.entity.damage.ProjectileDamageSource
import net.minecraft.entity.player.PlayerEntity

object LCCDamage : BasicDirectory<DamageSource, String>() {

    val gauntlet_punch_wall by entry(::initialiser) { LCCDamageSource(properties, bypassArmor = true, unblockable = true) }

    val heated by entry(::initialiser) { LCCDamageSource(properties, fire = true) }
    val boiled by entry(::initialiser) { LCCDamageSource(properties, fire = true) }
    val radiation by entry(::initialiser) { LCCDamageSource(properties, bypassArmor = true, unblockable = true) }
    val hazmat_anoxia by entry(::initialiser) { LCCDamageSource(properties, bypassArmor = true) }
    val salt by entry(::initialiser) { LCCDamageSource(properties) }
    val spikes by entry(::initialiser) { LCCDamageSource(properties) }

    fun <D : DamageSource> initialiser(input: D, context: DirectoryContext<String>, parameters: Unit) = input

    fun gauntletUppercut(player: PlayerEntity) = EntityDamageSource(name("gauntlet_uppercut"), player)

    fun gauntletPunch(player: PlayerEntity) = EntityDamageSource(name("gauntlet_punch"), player)

    fun nuke(attacker: LivingEntity?): DamageSource {
        return if (attacker != null) EntityDamageSource(name("nuke.player"), attacker).setExplosive() else LCCDamageSource(name("nuke")).setExplosive()
    }

    fun salt(projectile: Entity, attacker: Entity?) = ProjectileDamageSource(name("salt"), projectile, attacker).setProjectile()

    override fun defaultProperties(name: String) = name(name)
    fun name(name: String) = "${LCC.modid}.$name"

}