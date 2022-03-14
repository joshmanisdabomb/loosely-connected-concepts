package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.tag.HeartItemTagFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import com.joshmanisdabomb.lcc.item.HeartItem
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.predicate.item.ItemPredicate

object HeartRecipeFactory : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        if (entry !is HeartItem) return
        if (entry is HeartContainerItem) return
        if (entry.value == 1f) {
            ShapelessRecipeJsonBuilder.create(entry, 2)
                .input(LCCItems.heart_full[entry.heart])
                .criterion("has_${entry.heart.asString()}_hearts", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(HeartItemTagFactory.getTag(entry.heart)).build()))
                .apply { offerShapeless(this, data) }
        } else {
            ShapelessRecipeJsonBuilder.create(entry)
                .input(LCCItems.heart_half[entry.heart], 2)
                .criterion("has_${entry.heart.asString()}_hearts", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(HeartItemTagFactory.getTag(entry.heart)).build()))
                .apply { offerShapeless(this, data) }
        }
    }

}
