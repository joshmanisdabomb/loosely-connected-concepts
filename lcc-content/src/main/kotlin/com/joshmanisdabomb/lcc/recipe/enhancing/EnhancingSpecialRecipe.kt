package com.joshmanisdabomb.lcc.recipe.enhancing

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class EnhancingSpecialRecipe(id: Identifier) : EnhancingRecipe(id) {

    override fun isIgnoredInRecipeBook() = true

    override fun getOutput() = ItemStack.EMPTY

}