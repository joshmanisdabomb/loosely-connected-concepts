package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier

class RiftFromTagRecipeFactory(val tag: Tag<Item>, val count: Int = 1, criterion: (SingleItemRecipeJsonFactory.(entry: Item) -> Unit)? = null, val name: Identifier? = null) : RecipeFactory {

    val ingredient = Ingredient.fromTag(tag)
    val criterion = criterion ?: { hasCriterionSingle(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val id = registry(entry)
        SingleItemRecipeJsonFactory(LCCRecipeSerializers.time_rift, ingredient, entry, count).apply { this@RiftFromTagRecipeFactory.criterion(this, LCCBlocks.time_rift.asItem()) }.apply { offerSingle(this, data, name ?: loc(id) { "${it}_from_rift" }) }
    }

}