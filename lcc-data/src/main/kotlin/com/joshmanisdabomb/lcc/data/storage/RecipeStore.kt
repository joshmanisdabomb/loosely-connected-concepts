package com.joshmanisdabomb.lcc.data.storage

import com.google.gson.JsonArray
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class RecipeStore {

    private val map = mutableMapOf<Identifier, RecipeJsonProvider>()
    private lateinit var objects: Map<Identifier, Recipe<*>>

    private lateinit var makes: Map<Item, List<RecipeJsonProvider>>
    private lateinit var usedIn: Map<Item, List<RecipeJsonProvider>>
    private lateinit var inputsOf: Map<Identifier, List<Item>>
    private lateinit var outputsOf: Map<Identifier, List<Item>>

    private lateinit var itemsIn: Map<Identifier, List<Item>>
    private lateinit var tagsIn: Map<Identifier, List<Identifier>>

    private val tagHandlers = mutableMapOf<Identifier, Iterable<ItemConvertible>>()

    fun init() {
        objects = map.mapValues { (k, v) -> v.serializer.read(k, v.toJson()) }

        val make = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        val makeReverse = mutableMapOf<Identifier, List<Item>>()
        map.forEach { (k, v) ->
            val recipe = objects[k]!!
            val outputs = ((recipe as? LCCRecipe)?.getAllOutputs()?.plus(recipe.output) ?: listOf(recipe.output)).mapNotNull { it.item }.distinct()
            outputs.forEach { make.computeIfAbsent(it) { mutableListOf() }.add(v) }
            makeReverse[k] = outputs
        }
        this.makes = make
        this.outputsOf = makeReverse

        val _using = mutableMapOf<Ingredient, RecipeJsonProvider>()
        map.forEach { (k, v) ->
            val recipe = objects[k]!!
            recipe.ingredients.forEach {
                if (!it.isEmpty) _using[it] = v
            }
        }

        val using = mutableMapOf<Item, MutableList<RecipeJsonProvider>>()
        val usingReverse = mutableMapOf<Identifier, MutableList<Item>>()
        val itemsIn = mutableMapOf<Identifier, MutableList<Item>>()
        val tagsIn = mutableMapOf<Identifier, MutableList<Identifier>>()
        _using.forEach { (k, v) ->
            val json = k.toJson()
            val jsonArray = if (!json.isJsonArray) JsonArray().apply { add(json) } else json.asJsonArray
            jsonArray.forEach {
                val entry = it.asJsonObject
                if (entry.has("item")) {
                    val item = Registry.ITEM.get(Identifier(entry.get("item").asString))
                    using.computeIfAbsent(item) { mutableListOf() }.add(v)
                    usingReverse.computeIfAbsent(v.recipeId) { mutableListOf() }.add(item)
                    itemsIn.computeIfAbsent(v.recipeId) { mutableListOf() }.add(item)
                } else if (entry.has("tag")) {
                    val tag = Identifier(entry.get("tag").asString)
                    val items = getItemsInTag(tag)
                    items.forEach {
                        using.computeIfAbsent(it.asItem()) { mutableListOf() }.add(v)
                        usingReverse.computeIfAbsent(v.recipeId) { mutableListOf() }.add(it.asItem())
                    }
                    tagsIn.computeIfAbsent(v.recipeId) { mutableListOf() }.add(tag)
                }
            }
        }
        this.usedIn = using
        this.inputsOf = usingReverse
        this.itemsIn = itemsIn
        this.tagsIn = tagsIn
    }

    operator fun get(id: Identifier) = map[id]
    fun getOrThrow(id: Identifier) = this[id] ?: error("No recipe found under $id")

    fun findRecipes(item: ItemConvertible) = makes[item.asItem()] ?: emptyList()

    fun findUsages(item: ItemConvertible) = usedIn[item.asItem()] ?: emptyList()

    fun getRecipe(id: Identifier) = objects[id]
    fun getRecipeOrThrow(id: Identifier) = objects[id] ?: error("No recipe object found under $id")

    fun getIngredientsOf(id: Identifier) = inputsOf[id] ?: emptyList()
    fun getOutputsOf(id: Identifier) = outputsOf[id] ?: emptyList()
    fun getItemsOf(id: Identifier) = getIngredientsOf(id) + getOutputsOf(id)

    fun getItemIngredientsOf(id: Identifier) = itemsIn[id] ?: emptyList()
    fun getTagIngredientsOf(id: Identifier) = tagsIn[id] ?: emptyList()

    fun add(provider: RecipeJsonProvider) {
        map[provider.recipeId] = provider
    }

    fun <T> addTagHandler(tag: TagKey<T>, items: TagKey<T>.() -> Iterable<ItemConvertible>) {
        tagHandlers[tag.id] = tag.items()
    }

    fun <T> addTagHandlerList(tag: TagKey<T>, vararg items: ItemConvertible) {
        addTagHandler(tag) { items.toList() }
    }

    fun <T> addTagHandlerFilter(tag: TagKey<T>, filter: TagKey<T>.(item: ItemConvertible) -> Boolean) {
        addTagHandler(tag) { Registry.ITEM.filter { tag.filter(it) } }
    }

    fun getItemsInTag(tag: Identifier) = tagHandlers[tag] ?: error("Items cannot be derived from tags before a world is loaded, please map items to tag \"$tag\" in RecipeStore.")

}