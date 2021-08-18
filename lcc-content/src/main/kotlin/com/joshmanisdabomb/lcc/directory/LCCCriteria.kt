package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.advancement.*
import net.fabricmc.fabric.api.`object`.builder.v1.advancement.CriterionRegistry
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions

object LCCCriteria : BasicDirectory<AbstractCriterion<out AbstractCriterionConditions>, Unit>() {

    val nuke by entry(::initialiser) { NuclearExplosionCriterion() }
    val nuclear by entry(::initialiser) { NuclearGeneratorCriterion() }
    val oxygen by entry(::initialiser) { ContainedArmorDepletionCriterion() }
    val race by entry(::initialiser) { RaceCriterion() }
    val nether_reactor by entry(::initialiser) { NetherReactorChallengeCriterion() }
    val sapphire_altar by entry(::initialiser) { SapphireAltarCompleteCriterion() }
    val heart_container by entry(::initialiser) { HeartContainerCriterion() }
    val explosive_paste by entry(::initialiser) { ExplosivePasteTriggeredCriterion() }

    private fun <R : AbstractCriterionConditions, C : AbstractCriterion<R>> initialiser(input: C, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun afterInitAll(initialised: List<DirectoryEntry<out AbstractCriterion<out AbstractCriterionConditions>, out AbstractCriterion<out AbstractCriterionConditions>>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { CriterionRegistry.register(it.entry) }
    }

    override fun defaultProperties(name: String) = Unit

    override fun id(name: String) = LCC.id(name)

}