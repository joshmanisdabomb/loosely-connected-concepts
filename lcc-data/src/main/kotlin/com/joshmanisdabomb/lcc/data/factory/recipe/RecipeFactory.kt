package com.joshmanisdabomb.lcc.data.factory.recipe

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import com.joshmanisdabomb.lcc.data.json.recipe.JsonFactoryAccess
import net.minecraft.advancement.criterion.CriterionConditions
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

    fun offerInterface(recipe: JsonFactoryAccess, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) = offer(recipe, { offerTo(it) }, { r, n -> offerAs(r, n) }, data, name, override)

    fun hasCriterionInterface(recipe: JsonFactoryAccess, item: ItemConvertible) = criterion(recipe, { s, c -> criterion(s, c) }, item)

    fun offerShaped(recipe: ShapedRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) = offer(recipe, { offerTo(it) }, { r, n -> offerTo(r, n) }, data, name, override)

    fun hasCriterionShaped(recipe: ShapedRecipeJsonFactory, item: ItemConvertible) = criterion(recipe, { s, c -> criterion(s, c) }, item)

    fun offerShapeless(recipe: ShapelessRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) = offer(recipe, { offerTo(it) }, { r, n -> offerTo(r, n) }, data, name, override)

    fun hasCriterionShapeless(recipe: ShapelessRecipeJsonFactory, item: ItemConvertible) = criterion(recipe, { s, c -> criterion(s, c) }, item)

    fun offerCooking(recipe: CookingRecipeJsonFactory, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) = offer(recipe, { offerTo(it) }, { r, n -> offerTo(r, n) }, data, name, override)

    fun hasCriterionCooking(recipe: CookingRecipeJsonFactory, item: ItemConvertible) = criterion(recipe, { s, c -> criterion(s, c) }, item)

    fun offerSingle(recipe: SingleItemRecipeJsonFactory, data: DataAccessor, name: Identifier, override: RecipeSerializer<*>? = null) = offerName(recipe, { r, n -> offerTo(r, n) }, data, name, override)

    fun hasCriterionSingle(recipe: SingleItemRecipeJsonFactory, item: ItemConvertible) = criterion(recipe, { s, c -> createStonecutting(s, c) }, item)

    fun offerSmithing(recipe: SmithingRecipeJsonFactory, data: DataAccessor, name: Identifier, override: RecipeSerializer<*>? = null) = offerName(recipe, { r, n -> offerTo(r, n) }, data, name, override)

    fun hasCriterionSmithing(recipe: SmithingRecipeJsonFactory, item: ItemConvertible) = criterion(recipe, { s, c -> criterion(s, c) }, item)

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

    private companion object {
        private fun accept(provider: RecipeJsonProvider, data: DataAccessor) {
            data.recipes.accept(provider)
            data.recipeStore.add(provider)
        }

        private fun <R> offer(recipe: R, offer: R.((RecipeJsonProvider) -> Unit) -> Unit, offerId: R.((RecipeJsonProvider) -> Unit, Identifier) -> Unit, data: DataAccessor, name: Identifier? = null, override: RecipeSerializer<*>? = null) {
            if (override != null) {
                var provider: RecipeJsonProvider? = null
                if (name != null) recipe.offerId({ provider = it }, name) else recipe.offer { provider = it }
                accept(OverrideRecipeJsonProvider(override, provider!!), data)
            } else {
                if (name != null) recipe.offerId({ accept(it, data) }, name) else recipe.offer { accept(it, data) }
            }
        }

        private fun <R> offerName(recipe: R, offer: R.((RecipeJsonProvider) -> Unit, Identifier) -> Unit, data: DataAccessor, name: Identifier, override: RecipeSerializer<*>? = null) {
            if (override != null) {
                var provider: RecipeJsonProvider? = null
                recipe.offer({ provider = it }, name)
                accept(OverrideRecipeJsonProvider(override, provider!!), data)
            } else {
                recipe.offer({ accept(it, data) }, name)
            }
        }

        private fun <R> criterion(recipe: R, criterion: R.(String, CriterionConditions) -> Unit, item: ItemConvertible) {
            recipe.criterion("has_${Registry.ITEM.getId(item.asItem()).path}", InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, arrayOf(ItemPredicate.Builder.create().item(item).build())))
        }
    }

}