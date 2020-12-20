package com.joshmanisdabomb.lcc.recipe

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.CuttingRecipe
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.world.World

class TimeRiftRecipe(id: Identifier, group: String, input: Ingredient, output: ItemStack) : CuttingRecipe(LCCRecipeTypes.time_rift, LCCRecipeSerializers.time_rift, id, group, input, output) {

    override fun matches(inv: Inventory, world: World) = input.test(inv.getStack(0))

    override fun getRecipeKindIcon() = LCCBlocks.time_rift.asItem().defaultStack

    class Serializer : CuttingRecipe.Serializer<TimeRiftRecipe>(::TimeRiftRecipe)

}
