package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.block.BlockState
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LocationPredicate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

class ExplosivePasteTriggeredCriterion : AbstractCriterion<ExplosivePasteTriggeredCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val state = StatePredicate.fromJson(obj.get("state"))
        val location = LocationPredicate.fromJson(obj.get("location"))
        return Conditions(player, state, location)
    }

    fun trigger(player: ServerPlayerEntity, pos: BlockPos) {
        trigger(player) { it.matches(player.getWorld(), pos, player.getWorld().getBlockState(pos)) }
    }

    class Conditions(player: EntityPredicate.Extended, val state: StatePredicate, val location: LocationPredicate) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.add("state", this.state.toJson())
            jsonObject.add("location", this.location.toJson())
            return jsonObject
        }

        fun matches(world: ServerWorld, pos: BlockPos, state: BlockState): Boolean {
            return this.state.test(state) && this.location.test(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        }

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.explosive_paste].id }

        fun create(state: StatePredicate, location: LocationPredicate): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY, state, location)
        }
    }

}