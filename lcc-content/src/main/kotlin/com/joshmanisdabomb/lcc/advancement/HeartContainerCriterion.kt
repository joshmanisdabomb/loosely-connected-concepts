package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity

class HeartContainerCriterion : AbstractCriterion<HeartContainerCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val type = obj.get("heart").asString
        val maxValue = NumberRange.FloatRange.fromJson(obj.get("value"))
        return Conditions(player, if (type.isNotEmpty()) type else null, maxValue)
    }

    fun trigger(player: ServerPlayerEntity, type: HeartType, maxValue: Double) {
        trigger(player) { it.matches(type, maxValue) }
    }

    class Conditions(player: EntityPredicate.Extended, val type: String?, val maxValue: NumberRange.FloatRange) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.addProperty("heart", this.type ?: "")
            jsonObject.add("value", this.maxValue.toJson())
            return jsonObject
        }

        fun matches(type: HeartType, maxValue: Double) = (this.type == null || type.asString() == this.type) && this.maxValue.test(maxValue)

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.heart_container].id }

        fun create(type: HeartType?, maxValue: NumberRange.FloatRange): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY, type?.asString(), maxValue)
        }
    }

}