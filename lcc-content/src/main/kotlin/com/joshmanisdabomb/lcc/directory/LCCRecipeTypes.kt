package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.recipe.HeartCondenserRecipe
import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe
import com.joshmanisdabomb.lcc.recipe.arcane.DungeonTableRecipe
import com.joshmanisdabomb.lcc.recipe.cooking.KilnRecipe
import com.joshmanisdabomb.lcc.recipe.enhancing.EnhancingRecipe
import com.joshmanisdabomb.lcc.recipe.imbuing.ImbuingRecipe
import com.joshmanisdabomb.lcc.recipe.refining.RefiningRecipe
import com.joshmanisdabomb.lcc.recipe.special.PlasticRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry
import kotlin.reflect.KClass

object LCCRecipeTypes : AdvancedDirectory<KClass<out Recipe<*>>, RecipeType<out Recipe<*>>, Unit, Unit>(), RegistryDirectory<RecipeType<out Recipe<*>>, Unit, Unit> {

    override val registry by lazy { Registry.RECIPE_TYPE }

    override fun regId(name: String) = LCC.id(name)

    val spawner_table by entry(::recipeInitialiser) { DungeonTableRecipe::class }
    val time_rift by entry(::recipeInitialiser) { TimeRiftRecipe::class }
    val refining by entry(::recipeInitialiser) { RefiningRecipe::class }

    val kiln by entry(::recipeInitialiser) { KilnRecipe::class }

    val plastic_shaped by entry(::recipeInitialiser) { PlasticRecipe::class }

    val enhancing by entry(::recipeInitialiser) { EnhancingRecipe::class }
    val imbuing by entry(::recipeInitialiser) { ImbuingRecipe::class }
    val heart_condenser by entry(::recipeInitialiser) { HeartCondenserRecipe::class }

    fun <T : Recipe<*>> recipeInitialiser(input: KClass<T>, context: DirectoryContext<Unit>, parameters: Unit) = this.initialiser(object : RecipeType<T> { override fun toString() = context.name }, context, parameters)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
