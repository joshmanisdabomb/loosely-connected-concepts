package com.joshmanisdabomb.lcc.recipe.imbuing

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class ImbuingSpecialRecipe(id: Identifier) : ImbuingRecipe(id) {

    override fun isIgnoredInRecipeBook() = true

    override fun getOutput() = ItemStack.EMPTY

}