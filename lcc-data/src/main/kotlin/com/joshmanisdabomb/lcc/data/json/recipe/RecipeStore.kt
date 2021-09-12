package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonArray
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class RecipeStore {

    private val map = mutableMapOf<Identifier, RecipeJsonProvider>()
    private val serialised by lazy {
        map.mapValues { (k, v) -> v.serializer.read(k, v.toJson()) }
    }

    private val tagHandlers = mutableMapOf<Identifier, Iterable<ItemConvertible>>()

    private val make by lazy {
        val m = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        map.forEach { (k, v) ->
            val recipe = serialised[k]!!
            if (!recipe.output.isEmpty) m.computeIfAbsent(recipe.output.item) { mutableListOf() }.add(v)
        }
        m
    }
    private val _using by lazy {
        val m = mutableMapOf<Ingredient, RecipeJsonProvider>()
        map.forEach { (k, v) ->
            val recipe = serialised[k]!!
            recipe.ingredients.forEach {
                if (!it.isEmpty) m[it] = v
            }
        }
        m
    }
    private val using by lazy {
        val map = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        _using.forEach { (k, v) ->
            val json = k.toJson()
            val jsonArray = if (!json.isJsonArray) JsonArray().apply { add(json) } else json.asJsonArray
            jsonArray.forEach {
                val entry = it.asJsonObject
                if (entry.has("item")) {
                    map.computeIfAbsent(Registry.ITEM.get(Identifier(entry.get("item").asString))) { mutableListOf() }.add(v)
                } else if (entry.has("tag")) {
                    val items = tagHandlers.get(Identifier(entry.get("tag").asString)) ?: error("Items cannot be derived from tags before world load, please map items to tag \"${Identifier(entry.get("tag").asString)}\" in RecipeStore.")
                    items.forEach {
                        map.computeIfAbsent(it.asItem()) { mutableListOf() }.add(v)
                    }
                }
            }
        }
        map
    }

    operator fun get(id: Identifier) = map[id]
    fun getOrThrow(id: Identifier) = this[id] ?: error("No recipe found under $id")

    fun findRecipes(item: ItemConvertible) = make[item.asItem()] ?: emptyList()

    fun findUsages(item: ItemConvertible) = using[item.asItem()] ?: emptyList()

    fun add(provider: RecipeJsonProvider) {
        map[provider.recipeId] = provider
    }

    fun <T> addTagHandler(tag: Tag<T>, items: Tag<T>.() -> Iterable<ItemConvertible>) {
        tagHandlers[(tag as Tag.Identified<T>).id] = tag.items()
    }

    fun <T> addTagHandlerList(tag: Tag<T>, vararg items: ItemConvertible) {
        addTagHandler(tag) { items.toList() }
    }

    fun <T> addTagHandlerFilter(tag: Tag<T>, filter: Tag<T>.(item: ItemConvertible) -> Boolean) {
        addTagHandler(tag) { Registry.ITEM.filter { tag.filter(it) } }
    }

}