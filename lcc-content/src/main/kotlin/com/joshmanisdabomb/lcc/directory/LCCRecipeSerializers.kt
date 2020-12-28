package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.recipe.DungeonTableShapedRecipe
import com.joshmanisdabomb.lcc.recipe.DungeonTableShapelessRecipe
import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.registry.Registry

object LCCRecipeSerializers : RegistryDirectory<RecipeSerializer<*>, Unit>() {

    override val _registry by lazy { Registry.RECIPE_SERIALIZER }

    override fun id(path: String) = LCC.id(path)

    val spawner_table_shaped by create { DungeonTableShapedRecipe.Serializer() }
    val spawner_table_shapeless by create { DungeonTableShapelessRecipe.Serializer() }

    val time_rift by create { TimeRiftRecipe.Serializer() }

}