package com.joshmanisdabomb.lcc.data.generators.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.storage.RecipeBatch
import com.joshmanisdabomb.lcc.mixin.data.common.RecipeProviderAccessor
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter

class RecipeData(val batch: RecipeBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        batch.getRecipes().forEach {
            RecipeProviderAccessor.saveRecipe(writer, it.toJson(), da.path.resolve("data/" + it.recipeId.namespace + "/recipes/" + it.recipeId.path + ".json"))
            val json = it.toAdvancementJson() ?: return@forEach
            RecipeProviderAccessor.saveRecipeAdvancement(writer, json, da.path.resolve("data/" + it.advancementId!!.namespace + "/advancements/" + it.advancementId!!.path + ".json"))
        }
    }

    override fun getName() = "${da.modid} Recipe Data"

}