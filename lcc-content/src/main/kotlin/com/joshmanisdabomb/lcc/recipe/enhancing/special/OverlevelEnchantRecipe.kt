package com.joshmanisdabomb.lcc.recipe.enhancing.special

import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.recipe.enhancing.EnhancingSpecialRecipe
import net.minecraft.enchantment.EnchantmentLevelEntry
import net.minecraft.item.EnchantedBookItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class OverlevelEnchantRecipe(id: Identifier) : EnhancingSpecialRecipe(id) {

    override fun matches(inventory: LCCInventory, world: World): Boolean {
        if (!inventory.getStack(1).isOf(LCCItems.enhancing_pyre_omega)) return false
        val stack = inventory.getStack(0)
        if (!stack.isOf(Items.ENCHANTED_BOOK)) return false
        val enchantments = EnchantedBookItem.getEnchantmentNbt(stack)
        if (enchantments.size != 1) return false
        val data = enchantments.firstOrNull() as? NbtCompound ?: return false
        val enchantment = Registry.ENCHANTMENT[Identifier(data.getString("id"))] ?: return false
        return enchantment.maxLevel > 1 && data.getShort("lvl").toInt() == enchantment.maxLevel
    }

    override fun craft(inventory: LCCInventory): ItemStack {
        val stack = inventory.getStack(0).copy()
        if (!stack.isOf(Items.ENCHANTED_BOOK)) return ItemStack.EMPTY
        val enchantments = EnchantedBookItem.getEnchantmentNbt(stack)
        val data = enchantments.firstOrNull() as? NbtCompound ?: return ItemStack.EMPTY
        val enchantment = Registry.ENCHANTMENT[Identifier(data.getString("id"))] ?: return ItemStack.EMPTY
        val output = EnchantedBookItem.forEnchantment(EnchantmentLevelEntry(enchantment, enchantment.maxLevel.plus(1)))
        output.getOrCreateSubNbt("lcc-overlevel").putByte(data.getString("id"), enchantment.maxLevel.plus(1).toByte())
        return output
    }

    override fun getSerializer() = LCCRecipeSerializers.overlevel_enchants

}