package com.joshmanisdabomb.lcc.recipe

import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.item.OxygenStorageItem
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.util.Identifier
import net.minecraft.world.World

class HazmatChestplateRecipe(id: Identifier) : SpecialCraftingRecipe(id) {

    override fun matches(inv: CraftingInventory, world: World): Boolean {
        if (inv.width < 3 || inv.height < 3) return false
        for (i in 0..inv.width-2) {
            for (j in 0..inv.height-2) {
                if (match(inv, i, j) != null) return true
            }
        }
        return false
    }

    private fun match(inv: CraftingInventory, i: Int, j: Int): ItemStack? {
        var tank: ItemStack? = null
        for (x in 0 until 3) {
            for (y in 0 until 3) {
                val stack = inv.getStack(i.plus(x) + j.plus(y).times(inv.width))
                if (x == 1 && y == 0) {
                    if (stack.item !is OxygenStorageItem) return null
                    tank = stack
                } else {
                    if (stack.item != LCCItems.heavy_duty_rubber) return null
                }
            }
        }
        return tank
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        if (inv.width < 3 || inv.height < 3) return ItemStack.EMPTY
        for (i in 0..inv.width-2) {
            for (j in 0..inv.height-2) {
                match(inv, i, j)?.also { tank -> return LCCItems.hazmat_chestplate.defaultStack.also { LCCItems.hazmat_chestplate.setOxygen(it, (tank.item as? OxygenStorage)?.getOxygen(tank) ?: 0) } }
            }
        }
        return ItemStack.EMPTY
    }

    override fun fits(width: Int, height: Int) = width * height >= 9

    override fun getSerializer() = LCCRecipeSerializers.hazmat_chestplate

}
