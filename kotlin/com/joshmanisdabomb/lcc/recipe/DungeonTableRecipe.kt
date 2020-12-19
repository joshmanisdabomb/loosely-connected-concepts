package com.joshmanisdabomb.lcc.recipe

import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Recipe

interface DungeonTableRecipe : Recipe<Inventory> {

    override fun getType() = LCCRecipeTypes.spawner_table

}