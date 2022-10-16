package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class HeartCondenserRecipeJsonFactory(private val output: ItemConvertible, private val count: Int = 1) : JsonFactoryAccess {

    private val values = mutableMapOf<Ingredient, Int>()
    private lateinit var state: HeartCondenserBlock.HeartState
    private val builder = Advancement.Builder.create()
    private var group: String? = null

    fun input(ingredient: Ingredient, value: Int): HeartCondenserRecipeJsonFactory {
        this.values += ingredient to value
        return this
    }

    fun input(item: ItemConvertible, value: Int): HeartCondenserRecipeJsonFactory {
        input(Ingredient.ofItems(item), value)
        return this
    }

    override fun criterion(criterionName: String, conditions: CriterionConditions): HeartCondenserRecipeJsonFactory {
        builder.criterion(criterionName, conditions)
        return this
    }

    fun group(group: String): HeartCondenserRecipeJsonFactory {
        this.group = group
        return this
    }

    fun state(type: HeartCondenserBlock.HeartState): HeartCondenserRecipeJsonFactory {
        this.state = type
        return this
    }

    override fun offerTo(exporter: (RecipeJsonProvider) -> Unit): HeartCondenserRecipeJsonFactory {
        this.offerAs(exporter, Registry.ITEM.getId(output.asItem()))
        return this
    }

    fun offerAsString(exporter: (RecipeJsonProvider) -> Unit, recipeIdStr: String): HeartCondenserRecipeJsonFactory {
        val identifier = Registry.ITEM.getId(output.asItem())
        check(Identifier(recipeIdStr) != identifier) { "Shaped Recipe $recipeIdStr should remove its 'save' argument" }
        this.offerAs(exporter, Identifier(recipeIdStr))
        return this
    }

    override fun offerAs(exporter: (RecipeJsonProvider) -> Unit, recipeId: Identifier): HeartCondenserRecipeJsonFactory {
        builder.parent(Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR)
        exporter(HeartCondenserRecipeJsonProvider(recipeId, group ?: "", values, state, output.asItem(), count, builder, Identifier(recipeId.namespace, "recipes/" + output.asItem().group?.name + "/" + recipeId.path)))
        return this
    }

    class HeartCondenserRecipeJsonProvider(private val recipeId: Identifier, private val group: String, private val values: Map<Ingredient, Int>, private val state: HeartCondenserBlock.HeartState, private val output: Item, private val count: Int, private val builder: Advancement.Builder, private val advancementId: Identifier) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) {

            if (group.isNotEmpty()) json.addProperty("group", group)

            val jsonIngredients = JsonArray()
            values.forEach { (k, v) ->
                val element = k.toJson().asJsonObject
                element.addProperty("value", v)
                jsonIngredients.add(element)
            }
            json.add("ingredients", jsonIngredients)

            json.addProperty("state", state.asString())

            json.addProperty("result", Registry.ITEM.getId(output).toString())
            json.addProperty("count", this.count)
        }

        override fun getSerializer() = LCCRecipeSerializers.heart_condenser

        override fun getRecipeId() = recipeId

        override fun toAdvancementJson() = builder.toJson()

        override fun getAdvancementId() = advancementId

    }

}