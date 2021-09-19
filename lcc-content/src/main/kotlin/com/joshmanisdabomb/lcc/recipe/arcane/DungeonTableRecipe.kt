package com.joshmanisdabomb.lcc.recipe.arcane

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Recipe

interface DungeonTableRecipe : Recipe<Inventory> {

    override fun getType() = LCCRecipeTypes.spawner_table

    override fun createIcon() = LCCBlocks.spawner_table.asItem().defaultStack

}