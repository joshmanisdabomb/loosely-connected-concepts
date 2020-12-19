package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.recipe.DungeonTableRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry

object LCCRecipeTypes : RegistryDirectory<RecipeType<*>, Unit>() {

    override val _registry by lazy { Registry.RECIPE_TYPE }

    val spawner_table by createWithName<RecipeType<DungeonTableRecipe>>(this@LCCRecipeTypes::default)

    private fun <T : Recipe<*>> default(name: String) = object : RecipeType<T> { override fun toString() = name }

}
