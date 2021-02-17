package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.advancement.NuclearExplosionCriterion
import com.joshmanisdabomb.lcc.advancement.RaceCriterion
import net.fabricmc.fabric.api.`object`.builder.v1.advancement.CriterionRegistry
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions

object LCCCriteria : ThingDirectory<AbstractCriterion<out AbstractCriterionConditions>, Unit>() {

    val nuke by create { NuclearExplosionCriterion() }
    val race by create { RaceCriterion() }

    override fun registerAll(things: Map<String, AbstractCriterion<out AbstractCriterionConditions>>, properties: Map<String, Unit>) {
        things.forEach { (k, v) ->
            CriterionRegistry.register(v)
        }
    }

}