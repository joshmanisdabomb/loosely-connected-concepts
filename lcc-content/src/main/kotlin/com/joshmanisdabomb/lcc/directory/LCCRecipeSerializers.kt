package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.recipe.*
import com.joshmanisdabomb.lcc.widens.CommonWidens
import net.minecraft.recipe.CookingRecipeSerializer
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialRecipeSerializer
import net.minecraft.util.registry.Registry

object LCCRecipeSerializers : BasicDirectory<RecipeSerializer<out Recipe<*>>, Unit>(), RegistryDirectory<RecipeSerializer<out Recipe<*>>, Unit, Unit> {

    override val registry by lazy { Registry.RECIPE_SERIALIZER }

    override fun regId(name: String) = LCC.id(name)

    val spawner_table_shaped by entry(::initialiser) { DungeonTableShapedRecipe.Serializer() }
    val spawner_table_shapeless by entry(::initialiser) { DungeonTableShapelessRecipe.Serializer() }

    val refining_shaped by entry(::initialiser) { RefiningShapedRecipe.Serializer() }
    val refining_shapeless by entry(::initialiser) { RefiningShapelessRecipe.Serializer() }

    val time_rift by entry(::initialiser) { TimeRiftRecipe.Serializer() }

    val hazmat_chestplate by entry(::initialiser) { SpecialRecipeSerializer(::HazmatChestplateRecipe) }

    val kiln by entry(::initialiser) { CookingRecipeSerializer(CommonWidens.cookingRecipeFactory(::KilnRecipe), 100) }

    override fun defaultProperties(name: String) = Unit

}