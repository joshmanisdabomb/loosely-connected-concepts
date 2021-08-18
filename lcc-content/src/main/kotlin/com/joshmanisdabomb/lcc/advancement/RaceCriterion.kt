package com.joshmanisdabomb.lcc.advancement;

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper

class RaceCriterion : AbstractCriterion<RaceCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val id = Identifier(JsonHelper.getString(obj, "advancement"))
        return Conditions(player, id)
    }

    fun trigger(player: ServerPlayerEntity, advancement: Advancement) {
        test(player) { it.matches(advancement.id) }
    }

    class Conditions(player: EntityPredicate.Extended, val advancement: Identifier) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.addProperty("advancement", this.advancement.toString())
            return jsonObject
        }

        fun matches(advancement: Identifier) = this.advancement == advancement

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.race].id }

        fun create(advancement: Identifier): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY, advancement)
        }

        fun onGrant(player: ServerPlayerEntity, advancement: Advancement) {
            LCCComponents.advancement_race.maybeGet(player.world.levelProperties).orElse(null)?.check(player, advancement)
        }
    }

}
