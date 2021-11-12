package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class SapphireAltarCompleteCriterion : AbstractCriterion<SapphireAltarCompleteCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val id = obj.get("challenge").asString
        val challenge = if (id.isEmpty()) null else Identifier.tryParse(id)
        val rewards = NumberRange.IntRange.fromJson(obj.get("rewards"))
        return Conditions(player, challenge, rewards)
    }

    fun trigger(player: ServerPlayerEntity, challenge: AltarChallenge, rewards: Int) {
        trigger(player) { it.matches(challenge, rewards) }
    }

    class Conditions(player: EntityPredicate.Extended, val challenge: Identifier?, val rewards: NumberRange.IntRange) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.addProperty("challenge", this.challenge?.toString() ?: "")
            jsonObject.add("rewards", this.rewards.toJson())
            return jsonObject
        }

        fun matches(challenge: AltarChallenge, rewards: Int) = (this.challenge == null || challenge.id == this.challenge) && this.rewards.test(rewards)

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.sapphire_altar].id }

        fun create(challenge: AltarChallenge?, rewards: NumberRange.IntRange): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY, challenge?.id, rewards)
        }
    }

}