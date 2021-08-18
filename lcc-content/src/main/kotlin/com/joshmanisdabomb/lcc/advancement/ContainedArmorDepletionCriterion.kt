package com.joshmanisdabomb.lcc.advancement

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.item.ItemStack
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.server.network.ServerPlayerEntity

class ContainedArmorDepletionCriterion : AbstractCriterion<ContainedArmorDepletionCriterion.Conditions>() {

    override fun getId() = Companion.id

    override fun conditionsFromJson(obj: JsonObject, player: EntityPredicate.Extended, deserializer: AdvancementEntityPredicateDeserializer): Conditions {
        val stack = ItemPredicate.fromJson(obj.get("item"))
        val before = NumberRange.FloatRange.fromJson(obj.get("before"))
        val loss = NumberRange.FloatRange.fromJson(obj.get("loss"))
        val after = NumberRange.FloatRange.fromJson(obj.get("after"))
        return Conditions(player, stack, before, loss, after)
    }

    fun trigger(player: ServerPlayerEntity, stack: ItemStack, before: Float, loss: Float, after: Float) {
        test(player) { it.matches(stack, before, loss, after) }
    }

    class Conditions(player: EntityPredicate.Extended, val item: ItemPredicate, val before: NumberRange.FloatRange, val loss: NumberRange.FloatRange, val after: NumberRange.FloatRange) : AbstractCriterionConditions(id, player) {

        override fun toJson(serializer: AdvancementEntityPredicateSerializer): JsonObject {
            val jsonObject = super.toJson(serializer)
            jsonObject.add("item", this.item.toJson())
            jsonObject.add("before", this.before.toJson())
            jsonObject.add("loss", this.loss.toJson())
            jsonObject.add("after", this.after.toJson())
            return jsonObject
        }

        fun matches(item: ItemStack, before: Float, loss: Float, after: Float) = this.item.test(item) && this.before.test(before) && this.loss.test(loss) && this.after.test(after)

    }

    companion object {
        val id by lazy { LCCCriteria[LCCCriteria.oxygen].id }

        fun create(item: ItemPredicate, before: NumberRange.FloatRange = NumberRange.FloatRange.ANY, loss: NumberRange.FloatRange = NumberRange.FloatRange.ANY, after: NumberRange.FloatRange = NumberRange.FloatRange.ANY): Conditions {
            return Conditions(EntityPredicate.Extended.EMPTY, item, before, loss, after)
        }
    }

}