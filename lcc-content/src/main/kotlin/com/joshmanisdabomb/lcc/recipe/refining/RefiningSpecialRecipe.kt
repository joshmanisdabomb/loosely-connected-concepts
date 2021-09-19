package com.joshmanisdabomb.lcc.recipe.refining

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class RefiningSpecialRecipe(protected val _id: Identifier) : RefiningRecipe() {

    override fun getId() = _id

    override fun isIgnoredInRecipeBook() = true

    override fun getOutput() = ItemStack.EMPTY

}