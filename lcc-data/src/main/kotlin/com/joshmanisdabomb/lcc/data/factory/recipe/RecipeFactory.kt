package com.joshmanisdabomb.lcc.data.factory.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.Block
import net.minecraft.data.server.recipe.*
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface RecipeFactory : BlockDataFactory, ItemDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        apply(data, entry.asItem())
    }

    fun offerShaped(recipe: ShapedRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) {
        if (override != null) {
            var provider: RecipeJsonProvider? = null
            if (name != null) recipe.offerTo({ provider = it }, name) else recipe.offerTo { provider = it }
            data.recipes.accept(OverrideRecipeJsonProvider(override, provider!!))
        } else {
            if (name != null) recipe.offerTo(data.recipes, name) else recipe.offerTo(data.recipes)
        }
    }

    fun hasCriterionShaped(recipe: ShapedRecipeJsonFactory, item: ItemConvertible) {
        recipe.criterion("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
    }

    fun offerShapeless(recipe: ShapelessRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) {
        if (override != null) {
            var provider: RecipeJsonProvider? = null
            if (name != null) recipe.offerTo({ provider = it }, name) else recipe.offerTo { provider = it }
            data.recipes.accept(OverrideRecipeJsonProvider(override, provider!!))
        } else {
            if (name != null) recipe.offerTo(data.recipes, name) else recipe.offerTo(data.recipes)
        }
    }

    fun hasCriterionShapeless(recipe: ShapelessRecipeJsonFactory, item: ItemConvertible) {
        recipe.criterion("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
    }

    fun offerCooking(recipe: CookingRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) {
        if (override != null) {
            var provider: RecipeJsonProvider? = null
            if (name != null) recipe.offerTo({ provider = it }, name) else recipe.offerTo { provider = it }
            data.recipes.accept(OverrideRecipeJsonProvider(override, provider!!))
        } else {
            if (name != null) recipe.offerTo(data.recipes, name) else recipe.offerTo(data.recipes)
        }
    }

    fun hasCriterionCooking(recipe: CookingRecipeJsonFactory, item: ItemConvertible) {
        recipe.criterion("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
    }

    fun offerSingle(recipe: SingleItemRecipeJsonFactory, data: DataAccessor, name: Identifier, override: RecipeSerializer<*>? = null) {
        if (override != null) {
            var provider: RecipeJsonProvider? = null
            recipe.offerTo({ provider = it }, name)
            data.recipes.accept(OverrideRecipeJsonProvider(override, provider!!))
        } else {
            recipe.offerTo(data.recipes, name)
        }
    }

    fun hasCriterionSingle(recipe: SingleItemRecipeJsonFactory, item: ItemConvertible) {
        recipe.create("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
    }

    fun offerSmithing(recipe: SmithingRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) {
        if (override != null) {
            var provider: RecipeJsonProvider? = null
            recipe.offerTo({ provider = it }, name)
            data.recipes.accept(OverrideRecipeJsonProvider(override, provider!!))
        } else {
            recipe.offerTo(data.recipes, name)
        }
    }

    fun hasCriterionSmithing(recipe: SmithingRecipeJsonFactory, item: ItemConvertible) {
        recipe.criterion("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
    }

    fun loc(name: String, modid: String, path: (name: String) -> String = { it }) = Identifier(modid, path(name))

    fun loc(resource: Identifier, path: (name: String) -> String = { it }) = loc(resource.path, resource.namespace, path)

    fun loc(item: Item, path: (name: String) -> String = { it }) = loc(registry(item), path)

    fun suffix(id: Identifier, suffix: String?, glue: String = "_") = Identifier(id.namespace, suffix(id.path, suffix, glue))

    fun suffix(path: String, suffix: String?, glue: String = "_") = "${path}${if (suffix != null) "$glue$suffix" else ""}"

    fun assetPath(item: Item) = registry(item).path

    fun registry(item: Item) = Registry.ITEM.getId(item)

    class OverrideRecipeJsonProvider(val newSerializer: RecipeSerializer<*>, val provider: RecipeJsonProvider) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) = provider.serialize(json)

        override fun getRecipeId() = provider.recipeId

        override fun getSerializer() = newSerializer

        override fun toAdvancementJson() = provider.toAdvancementJson()

        override fun getAdvancementId() = provider.advancementId

    }

}