package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.advancement.*
import net.fabricmc.fabric.api.`object`.builder.v1.advancement.CriterionRegistry
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions

object LCCCriteria : BasicDirectory<AbstractCriterion<out AbstractCriterionConditions>, Unit>() {

    val nuke by entry(::initialiser) { NuclearExplosionCriterion() }
    val nuclear by entry(::initialiser) { NuclearGeneratorCriterion() }
    val oxygen by entry(::initialiser) { ContainedArmorDepletionCriterion() }
    val race by entry(::initialiser) { RaceCriterion() }
    val netherReactor by entry(::initialiser) { NetherReactorChallengeCriterion() }

    private fun <R : AbstractCriterionConditions, C : AbstractCriterion<R>> initialiser(input: C, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out AbstractCriterion<out AbstractCriterionConditions>, out AbstractCriterion<out AbstractCriterionConditions>>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { CriterionRegistry.register(it.entry) }
    }

    override fun defaultProperties(name: String) = Unit

}