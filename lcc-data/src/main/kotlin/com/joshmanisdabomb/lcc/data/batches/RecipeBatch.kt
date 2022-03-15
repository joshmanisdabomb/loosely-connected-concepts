package com.joshmanisdabomb.lcc.data.storage

import com.joshmanisdabomb.lcc.data.recipe.IdentifiableIngredient
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Consumer

class RecipeBatch : Consumer<RecipeJsonProvider> {

    private val map = mutableMapOf<Identifier, RecipeJsonProvider>()
    private lateinit var analysis: Map<Identifier, RecipeBatchAnalysis>

    private lateinit var inputIn: Map<Item, List<RecipeBatchAnalysis>>
    private lateinit var outputIn: Map<Item, List<RecipeBatchAnalysis>>

    private val tagHandlers = mutableMapOf<Identifier, Iterable<ItemConvertible>>()

    fun index() {
        val inputIn = mutableMapOf<Item, MutableList<RecipeBatchAnalysis>>()
        val outputIn = mutableMapOf<Item, MutableList<RecipeBatchAnalysis>>()

        analysis = map.mapValues { (k, v) -> analyse(v) }.onEach { (k, v) ->
            v.allInputItems.forEach { inputIn.computeIfAbsent(it) { mutableListOf() }.add(v) }
            v.outputItems.forEach { outputIn.computeIfAbsent(it) { mutableListOf() }.add(v) }
        }

        this.inputIn = inputIn
        this.outputIn = outputIn
    }

    fun analyse(recipe: RecipeJsonProvider) = RecipeBatchAnalysis(recipe)

    operator fun get(id: Identifier) = map[id]
    fun getOrThrow(id: Identifier) = this[id] ?: error("No recipe found under $id")

    fun findRecipes(item: ItemConvertible) = outputIn[item.asItem()] ?: emptyList()

    fun findUsages(item: ItemConvertible) = inputIn[item.asItem()] ?: emptyList()

    fun getRecipe(id: Identifier) = analysis[id]
    fun getRecipeOrThrow(id: Identifier) = analysis[id] ?: error("No recipe object found under $id")

    fun getIngredientsOf(id: Identifier) = analysis[id]?.allInputItems ?: emptyList()
    fun getOutputsOf(id: Identifier) = analysis[id]?.outputItems ?: emptyList()
    fun getItemsOf(id: Identifier) = analysis[id]?.items ?: emptyList()

    fun getItemIngredientsOf(id: Identifier) = analysis[id]?.inputItems ?: emptyList()
    fun getTagIngredientsOf(id: Identifier) = analysis[id]?.inputTags ?: emptyList()

    fun add(provider: RecipeJsonProvider) {
        map[provider.recipeId] = provider
    }

    override fun accept(provider: RecipeJsonProvider) = add(provider)

    fun <T> addTagHandler(tag: TagKey<T>, items: TagKey<T>.() -> Iterable<ItemConvertible>) {
        tagHandlers[tag.id] = tag.items()
    }

    fun <T> addTagHandlerList(tag: TagKey<T>, vararg items: ItemConvertible) {
        addTagHandler(tag) { items.toList() }
    }

    fun <T> addTagHandlerFilter(tag: TagKey<T>, filter: TagKey<T>.(item: ItemConvertible) -> Boolean) {
        addTagHandler(tag) { Registry.ITEM.filter { tag.filter(it) } }
    }

    fun getItemsInTag(tag: Identifier) = tagHandlers[tag] ?: error("Items cannot be derived from tags before a world is loaded, please map items to tag \"$tag\" in recipes.")

    fun getRecipes() = map.values.toList()

    inner class RecipeBatchAnalysis(val provider: RecipeJsonProvider) {

        val id = provider.recipeId
        val json = provider.toJson()
        val recipe = provider.serializer.read(id, json)

        val ingredients = recipe.ingredients

        val inputStacks = ingredients.flatMap { (it as IdentifiableIngredient).getItemStacks() }
        val inputItems = inputStacks.mapNotNull { it.item }.distinct()
        val inputTags = ingredients.flatMap { (it as IdentifiableIngredient).getTags() }.distinct()
        val inputTagItems = (ingredients.flatMap { (it as IdentifiableIngredient).getTagStacks() }.mapNotNull { it.item } + inputTags.flatMap { getItemsInTag(it.id) }).map { it.asItem() }.distinct()
        val inputTagStacks = inputTagItems.map { it.stack() }

        val allInputStacks = ingredients.flatMap { (it as IdentifiableIngredient).getAllStacks() } + inputTagStacks
        val allInputItems = allInputStacks.mapNotNull { it.item }.distinct()

        val outputStacks = ((recipe as? LCCRecipe)?.getAllOutputs()?.plus(recipe.output) ?: listOf(recipe.output))
        val outputItems = outputStacks.mapNotNull { it.item }.distinct()

        val items = allInputItems + outputItems

    }

}