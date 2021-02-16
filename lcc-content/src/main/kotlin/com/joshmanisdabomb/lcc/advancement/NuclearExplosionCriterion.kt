package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity

class NuclearExplosionCriterion : AbstractCriterion<NuclearExplosionCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val intRange = NumberRange.IntRange.fromJson(obj.get("level"))
        return Conditions(player, intRange)
    }

    fun trigger(player: ServerPlayerEntity, uranium: Int) {
        test(player) { it.matches(uranium) }
    }

    class Conditions(player: EntityPredicate.Extended, val uranium: NumberRange.IntRange) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.add("uranium", this.uranium.toJson())
            return jsonObject
        }

        fun matches(uranium: Int) = this.uranium.test(uranium)

        companion object {
            fun uranium(uranium: NumberRange.IntRange): Conditions {
                return Conditions(EntityPredicate.Extended.EMPTY, uranium)
            }
        }

    }

    companion object {
        val id by lazy { LCC.id(LCCCriteria[LCCCriteria.nuke] ?: error("Could not determine ID.")) }
    }

}