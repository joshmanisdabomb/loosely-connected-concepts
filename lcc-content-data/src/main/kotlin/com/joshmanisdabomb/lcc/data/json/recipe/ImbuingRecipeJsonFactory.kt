package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.recipe.IdentifiableIngredient
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.extensions.prefix
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class ImbuingRecipeJsonFactory(private val input: Ingredient, private val hits: Int) : JsonFactoryAccess {

    private val effects = mutableListOf<StatusEffectInstance>()
    private val builder = Advancement.Task.create()
    private var group: String? = null

    fun addEffect(vararg effects: StatusEffectInstance): ImbuingRecipeJsonFactory {
        this.effects.addAll(effects)
        return this
    }

    override fun criterion(criterionName: String, conditions: CriterionConditions): ImbuingRecipeJsonFactory {
        builder.criterion(criterionName, conditions)
        return this
    }

    fun group(group: String): ImbuingRecipeJsonFactory {
        this.group = group
        return this
    }

    override fun offerTo(exporter: (RecipeJsonProvider) -> Unit): ImbuingRecipeJsonFactory {
        this.offerAs(exporter, (input as IdentifiableIngredient).id!!.prefix("imbuing"))
        return this
    }

    fun offerAsString(exporter: (RecipeJsonProvider) -> Unit, recipeIdStr: String): ImbuingRecipeJsonFactory {
        val identifier = (input as IdentifiableIngredient).id
        check(Identifier(recipeIdStr) != identifier) { "Shaped Recipe $recipeIdStr should remove its 'save' argument" }
        this.offerAs(exporter, Identifier(recipeIdStr))
        return this
    }

    override fun offerAs(exporter: (RecipeJsonProvider) -> Unit, recipeId: Identifier): ImbuingRecipeJsonFactory {
        builder.parent(Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR)
        exporter(ImbuingRecipeJsonProvider(recipeId, group ?: "", input, effects, hits, builder, Identifier(recipeId.namespace, "recipes/" + (input as IdentifiableIngredient).getAllStacks().first().item.group?.name + "/" + recipeId.path)))
        return this
    }

    class ImbuingRecipeJsonProvider(private val recipeId: Identifier, private val group: String, private val input: Ingredient, private val effects: List<StatusEffectInstance>, private val hits: Int, private val builder: Advancement.Task, private val advancementId: Identifier) : RecipeJsonProvider {

        override fun serialize(json: JsonObject) {

            if (group.isNotEmpty()) json.addProperty("group", group)

            json.add("ingredient", input.toJson())

            val jsonEffects = JsonArray()
            effects.forEachIndexed { i, s ->
                val obj = JsonObject()
                obj.addProperty("effect", Registry.STATUS_EFFECT.getId(s.effectType).toString())
                obj.addProperty("duration", s.duration)
                obj.addProperty("amplifier", s.amplifier)
                obj.addProperty("ambient", s.isAmbient)
                obj.addProperty("particles", s.shouldShowParticles())
                obj.addProperty("icon", s.shouldShowIcon())
                jsonEffects.add(obj)
            }
            json.add("effects", jsonEffects)

            json.addProperty("hits", hits)

        }

        override fun getSerializer() = LCCRecipeSerializers.imbuing

        override fun getRecipeId() = recipeId

        override fun toAdvancementJson() = builder.toJson()

        override fun getAdvancementId() = advancementId

    }

}