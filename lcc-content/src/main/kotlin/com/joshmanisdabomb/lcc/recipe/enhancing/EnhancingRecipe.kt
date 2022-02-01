package com.joshmanisdabomb.lcc.recipe.enhancing

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.recipe.Recipe
import net.minecraft.util.Identifier

abstract class EnhancingRecipe(protected val _id: Identifier) : Recipe<LCCInventory>, LCCRecipe {

    override fun getType() = LCCRecipeTypes.enhancing

    override fun createIcon() = LCCBlocks.enhancing_chamber.stack()

    override fun getId() = _id

    override fun getAllOutputs() = listOf(output)

    override fun fits(width: Int, height: Int) = true

}
