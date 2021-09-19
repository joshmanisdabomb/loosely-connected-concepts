package com.joshmanisdabomb.lcc.data.knowledge

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeTranslator
import com.joshmanisdabomb.lcc.knowledge.KnowledgeRecipeTranslationHandler
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item

class LCCKnowledgeTranslator : KnowledgeTranslator() {

    override fun recipeTranslationsJson(recipe: RecipeJsonProvider, vararg items: Item): JsonObject {
        val json = super.recipeTranslationsJson(recipe, *items)
        val r = recipe.serializer.read(recipe.recipeId, recipe.toJson())
        if (r is KnowledgeRecipeTranslationHandler) {
            r.getExtraTranslations().forEach { (k, v) ->
                json.add(k, withTranslationJson(v))
            }
        }
        return json
    }

}