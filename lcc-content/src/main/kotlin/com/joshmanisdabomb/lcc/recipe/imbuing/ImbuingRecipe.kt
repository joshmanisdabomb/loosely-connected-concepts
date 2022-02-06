package com.joshmanisdabomb.lcc.recipe.imbuing

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.util.Identifier

abstract class ImbuingRecipe(protected val _id: Identifier) : Recipe<Inventory>, LCCRecipe {

    override fun getType() = LCCRecipeTypes.imbuing

    override fun createIcon() = LCCBlocks.imbuing_press.stack()

    override fun getId() = _id

    override fun getAllOutputs() = listOf(output)

    override fun fits(width: Int, height: Int) = true

    abstract fun getMaxHits(stack: ItemStack): Int

    abstract fun getEffects(): List<StatusEffectInstance>

}
