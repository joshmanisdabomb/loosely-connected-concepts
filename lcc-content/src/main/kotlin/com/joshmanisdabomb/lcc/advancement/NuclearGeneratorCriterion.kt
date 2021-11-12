package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity

class NuclearGeneratorCriterion : AbstractCriterion<NuclearGeneratorCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        return Conditions(player)
    }

    fun trigger(player: ServerPlayerEntity) {
        trigger(player) { it.matches() }
    }

    class Conditions(player: EntityPredicate.Extended) : AbstractCriterionConditions(id, player) {

        fun matches() = true

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.nuclear].id }

        fun create(): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY)
        }
    }

}