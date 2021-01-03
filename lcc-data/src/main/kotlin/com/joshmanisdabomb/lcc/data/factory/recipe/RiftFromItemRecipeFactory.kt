package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

class RiftFromItemRecipeFactory(val item: ItemConvertible, criterion: (SingleItemRecipeJsonFactory.(entry: Item) -> Unit)? = null, val name: Identifier? = null) : RecipeFactory {

    val ingredient = Ingredient.ofItems(item)
    val criterion = criterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val id = registry(entry)
        SingleItemRecipeJsonFactory(LCCRecipeSerializers.time_rift, ingredient, entry, 1).apply { this@RiftFromItemRecipeFactory.criterion(this, LCCBlocks.time_rift.asItem()) }.apply { offer(this, data, name ?: loc(id) { "${it}_from_rift" }) }
    }

}