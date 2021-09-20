package com.joshmanisdabomb.lcc.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList

fun RecipeSerializer<*>.getShapedKeys(key: JsonObject): Map<String, Ingredient> {
    return key.entrySet().map {
        if (it.key.length != 1) throw JsonSyntaxException("Invalid key entry: '${it.key}' is an invalid symbol (must be 1 character only).")
        if (it.key == " ") throw JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.")
        it.key to Ingredient.fromJson(it.value)
    }.toMap()
}

fun RecipeSerializer<*>.getShapedKeysWithCount(key: JsonObject): Map<String, Pair<Ingredient, Int>> {
    return key.entrySet().map {
        if (it.key.length != 1) throw JsonSyntaxException("Invalid key entry: '${it.key}' is an invalid symbol (must be 1 character only).")
        if (it.key == " ") throw JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.")
        val count = try {
            JsonHelper.getInt(it.value.asJsonObject, "count")
        } catch (ex: Exception) {
            1
        }
        it.key to (Ingredient.fromJson(it.value) to count)
    }.toMap()
}

fun RecipeSerializer<*>.getShapedPattern(pattern: JsonArray, maxWidth: Int, maxHeight: Int, minWidth: Int = 1, minHeight: Int = 1): Array<String> {
    if (pattern.size() > maxHeight) throw JsonSyntaxException("Invalid pattern: row amount greater than maximum recipe height of $maxHeight.")
    if (pattern.size() < minHeight) throw JsonSyntaxException("Invalid pattern: row amount lesser than minimum recipe height of $minHeight.")
    return pattern.mapIndexed { k, v ->
        val str = JsonHelper.asString(v, "pattern[$k]")
        if (str.length > maxWidth) throw JsonSyntaxException("Invalid pattern: pattern[$k] is greater than maximum recipe width of $maxWidth.")
        if (str.length < minWidth) throw JsonSyntaxException("Invalid pattern: pattern[$k] is lesser than minimum recipe width of $minWidth.")
        if (k > 0 && str.length != pattern[0].asString.length) throw JsonSyntaxException("Invalid pattern: each row must be the same width.")
        str
    }.toTypedArray()
}

fun RecipeSerializer<*>.getShapedIngredients(keys: Map<String, Ingredient>, pattern: Array<String>, w: Int, h: Int): DefaultedList<Ingredient> {
    return DefaultedList.copyOf(Ingredient.EMPTY, *pattern.flatMapIndexed { k, v -> v.map { if (it == ' ') Ingredient.EMPTY else keys[it.toString()] ?: throw JsonSyntaxException("Pattern references symbol '$it' but it's not defined in the key") } }.toTypedArray())
}

fun RecipeSerializer<*>.getShapedIngredientsWithCount(keys: Map<String, Pair<Ingredient, Int>>, pattern: Array<String>, w: Int, h: Int): DefaultedList<Pair<Ingredient, Int>> {
    return DefaultedList.copyOf(Ingredient.EMPTY to 0, *pattern.flatMapIndexed { k, v -> v.map { if (it == ' ') Ingredient.EMPTY to 0 else keys[it.toString()] ?: throw JsonSyntaxException("Pattern references symbol '$it' but it's not defined in the key") } }.toTypedArray())
}

fun RecipeSerializer<*>.getShapelessIngredients(ingredients: JsonArray): DefaultedList<Ingredient> {
    val defaultedList = DefaultedList.of<Ingredient>()

    for (i in 0 until ingredients.size()) {
        val ingredient = Ingredient.fromJson(ingredients[i])
        if (!ingredient.isEmpty) defaultedList += ingredient
    }

    return defaultedList
}

fun RecipeSerializer<*>.getShapelessIngredientsWithCount(ingredients: JsonArray): DefaultedList<Pair<Ingredient, Int>> {
    val defaultedList = DefaultedList.of<Pair<Ingredient, Int>>()

    for (i in 0 until ingredients.size()) {
        val ingredient = Ingredient.fromJson(ingredients[i])
        if (!ingredient.isEmpty) defaultedList += ingredient to JsonHelper.getInt(ingredients[i].asJsonObject, "count")
    }

    return defaultedList
}

fun RecipeSerializer<*>.getStack(result: JsonObject) = ShapedRecipe.getItem(result).stack(JsonHelper.getInt(result, "count", 1))