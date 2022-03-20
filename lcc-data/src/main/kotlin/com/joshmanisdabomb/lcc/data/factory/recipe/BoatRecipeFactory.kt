package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.advancement.criterion.EnterBlockCriterion
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.entity.EntityPredicate

class BoatRecipeFactory(val item: ItemConvertible, val output: Int = 1) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonBuilder.create(entry, output)
            .pattern("w w")
            .pattern("www")
            .input('w', item)
            .group("boat")
            .criterion("water", EnterBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, Blocks.WATER, StatePredicate.ANY))
            .apply { offerShaped(this, data) }
    }

}