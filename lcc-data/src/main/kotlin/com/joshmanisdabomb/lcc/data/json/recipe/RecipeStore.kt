package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonArray
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class RecipeStore {

    private val map = mutableMapOf<Identifier, RecipeJsonProvider>()
    private lateinit var objects: Map<Identifier, Recipe<*>>

    private lateinit var make: Map<Item, List<RecipeJsonProvider>>
    private lateinit var using: Map<Item, List<RecipeJsonProvider>>
    private lateinit var makeReverse: Map<Identifier, List<Item>>
    private lateinit var usingReverse: Map<Identifier, List<Item>>

    private val tagHandlers = mutableMapOf<Identifier, Iterable<ItemConvertible>>()

    fun init() {
        objects = map.mapValues { (k, v) -> v.serializer.read(k, v.toJson()) }

        val make = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        val makeReverse = mutableMapOf<Identifier, List<Item>>()
        map.forEach { (k, v) ->
            val recipe = objects[k]!!
            if (!recipe.output.isEmpty) {
                make.computeIfAbsent(recipe.output.item) { mutableListOf() }.add(v)
                makeReverse.put(k, listOf(recipe.output.item))
            }
        }
        this.make = make
        this.makeReverse = makeReverse

        val _using = mutableMapOf<Ingredient, RecipeJsonProvider>()
        map.forEach { (k, v) ->
            val recipe = objects[k]!!
            recipe.ingredients.forEach {
                if (!it.isEmpty) _using[it] = v
            }
        }

        val using = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        val usingReverse = mutableMapOf<Identifier, MutableList<Item>>()
        _using.forEach { (k, v) ->
            val json = k.toJson()
            val jsonArray = if (!json.isJsonArray) JsonArray().apply { add(json) } else json.asJsonArray
            jsonArray.forEach {
                val entry = it.asJsonObject
                if (entry.has("item")) {
                    val item = Registry.ITEM.get(Identifier(entry.get("item").asString))
                    using.computeIfAbsent(item) { mutableListOf() }.add(v)
                    usingReverse.computeIfAbsent(v.recipeId) { mutableListOf() }.add(item)
                } else if (entry.has("tag")) {
                    val items = tagHandlers.get(Identifier(entry.get("tag").asString)) ?: error("Items cannot be derived from tags before world load, please map items to tag \"${Identifier(entry.get("tag").asString)}\" in RecipeStore.")
                    items.forEach {
                        using.computeIfAbsent(it.asItem()) { mutableListOf() }.add(v)
                        usingReverse.computeIfAbsent(v.recipeId) { mutableListOf() }.add(it.asItem())
                    }
                }
            }
        }
        this.using = using
        this.usingReverse = usingReverse
    }

    operator fun get(id: Identifier) = map[id]
    fun getOrThrow(id: Identifier) = this[id] ?: error("No recipe found under $id")

    fun findRecipes(item: ItemConvertible) = make[item.asItem()] ?: emptyList()

    fun findUsages(item: ItemConvertible) = using[item.asItem()] ?: emptyList()

    fun getRecipe(id: Identifier) = objects[id]
    fun getRecipeOrThrow(id: Identifier) = objects[id] ?: error("No recipe object found under $id")

    fun getIngredientsOf(id: Identifier) = usingReverse[id] ?: emptyList()
    fun getOutputsOf(id: Identifier) = makeReverse[id] ?: emptyList()
    fun getItemsOf(id: Identifier) = getIngredientsOf(id) + getOutputsOf(id)

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