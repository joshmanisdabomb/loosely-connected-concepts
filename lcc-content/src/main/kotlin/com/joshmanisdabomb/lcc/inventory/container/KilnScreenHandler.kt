package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.book.RecipeBookCategory
import net.minecraft.screen.AbstractFurnaceScreenHandler
import net.minecraft.screen.PropertyDelegate

class KilnScreenHandler : AbstractFurnaceScreenHandler {

    constructor(syncId: Int, playerInventory: PlayerInventory, inventory: Inventory, propertyDelegate: PropertyDelegate) : super(LCCScreenHandlers.kiln, LCCRecipeTypes.kiln, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate)
    constructor(syncId: Int, playerInventory: PlayerInventory) : super(LCCScreenHandlers.kiln, LCCRecipeTypes.kiln, RecipeBookCategory.FURNACE, syncId, playerInventory)

}