package com.joshmanisdabomb.lcc.recipe.cooking

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import it.unimi.dsi.fastutil.ints.IntList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.SmeltingRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry

class KilnRecipe(id: Identifier, group: String, input: Ingredient, output: ItemStack, experience: Float, cookTime: Int) : AbstractCookingRecipe(LCCRecipeTypes.kiln, id, group, input, output, experience, cookTime) {

    @Environment(EnvType.CLIENT)
    override fun createIcon() = ItemStack(LCCBlocks.kiln)

    override fun getSerializer() = LCCRecipeSerializers.kiln

    companion object {
        fun injectRecipes(map: MutableMap<Identifier, JsonElement>) {
            val matches = mutableMapOf<CookingRecipeMatch, SmeltingRecipe?>()
            for ((k, v) in map) {
                val json = JsonHelper.asObject(v, "top element")
                val type = JsonHelper.getString(json, "type")
                val recipe = (Registry.RECIPE_SERIALIZER.getOrEmpty(Identifier(type)).orElse(null) ?: continue).read(k, json)
                if (recipe !is AbstractCookingRecipe) continue
                val match = CookingRecipeMatch(recipe.ingredients.first().matchingItemIds, recipe.output.item, recipe.output.count)
                when (recipe) {
                    is SmeltingRecipe -> if (!matches.containsKey(match)) matches[match] = recipe
                    else -> matches[match] = null
                }
            }
            matches.mapNotNull { it.value }.forEach {
                val id = LCC.id(it.id.namespace + "_" + it.id.path + "_kiln")
                val json = JsonObject()
                json.addProperty("type", "lcc:kiln")
                CookingRecipeJsonBuilder.CookingRecipeJsonProvider(id, "", it.ingredients.first(), it.output.item, it.experience, it.cookTime.div(2), null, null, null).serialize(json)
                map[id] = json
            }
        }

        private data class CookingRecipeMatch(val input: IntList, val output: Item, val count: Int)
    }

}
