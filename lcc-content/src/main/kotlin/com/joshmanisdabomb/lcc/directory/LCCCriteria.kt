package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.advancement.NuclearExplosionCriterion
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions

object LCCCriteria : ThingDirectory<AbstractCriterion<out AbstractCriterionConditions>, Unit>() {

    val nuke by create { NuclearExplosionCriterion() }

}