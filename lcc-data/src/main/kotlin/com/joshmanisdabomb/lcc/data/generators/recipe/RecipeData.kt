package com.joshmanisdabomb.lcc.data.generators.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.storage.RecipeBatch
import com.joshmanisdabomb.lcc.mixin.data.common.RecipesProviderAccessor
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider

class RecipeData(val batch: RecipeBatch, val da: DataAccessor) : DataProvider {

    override fun run(cache: DataCache) {
        batch.getRecipes().forEach {
            RecipesProviderAccessor.saveRecipe(cache, it.toJson(), da.path.resolve("data/" + it.recipeId.namespace + "/recipes/" + it.recipeId.path + ".json"))
            val json = it.toAdvancementJson() ?: return@forEach
            RecipesProviderAccessor.saveRecipeAdvancement(cache, json, da.path.resolve("data/" + it.advancementId!!.namespace + "/advancements/" + it.advancementId!!.path + ".json"))
        }
    }

    override fun getName() = "${da.modid} Recipe Data"

}