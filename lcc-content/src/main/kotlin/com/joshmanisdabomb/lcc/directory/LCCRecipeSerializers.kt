package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe
import com.joshmanisdabomb.lcc.recipe.arcane.DungeonTableShapedRecipe
import com.joshmanisdabomb.lcc.recipe.arcane.DungeonTableShapelessRecipe
import com.joshmanisdabomb.lcc.recipe.cooking.KilnRecipe
import com.joshmanisdabomb.lcc.recipe.enhancing.EnhancingSimpleRecipe
import com.joshmanisdabomb.lcc.recipe.enhancing.special.OverlevelEnchantRecipe
import com.joshmanisdabomb.lcc.recipe.imbuing.ImbuingSimpleRecipe
import com.joshmanisdabomb.lcc.recipe.refining.RefiningShapedRecipe
import com.joshmanisdabomb.lcc.recipe.refining.RefiningShapelessRecipe
import com.joshmanisdabomb.lcc.recipe.refining.special.PolymerRefiningRecipe
import com.joshmanisdabomb.lcc.recipe.special.HazmatChestplateRecipe
import com.joshmanisdabomb.lcc.recipe.special.PlasticRecipe
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

    val polymerization by entry(::initialiser) { SpecialRecipeSerializer(::PolymerRefiningRecipe) }
    val plastic_shaped by entry(::initialiser) { PlasticRecipe.Serializer() }

    val enhancing by entry(::initialiser) { EnhancingSimpleRecipe.Serializer() }
    val overlevel_enchants by entry(::initialiser) { SpecialRecipeSerializer(::OverlevelEnchantRecipe) }

    val imbuing by entry(::initialiser) { ImbuingSimpleRecipe.Serializer() }

    override fun defaultProperties(name: String) = Unit

}