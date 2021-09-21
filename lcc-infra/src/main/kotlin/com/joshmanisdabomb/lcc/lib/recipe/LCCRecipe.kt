package com.joshmanisdabomb.lcc.lib.recipe

import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.item.ItemStack

interface LCCRecipe {

    fun getAllOutputs(): List<ItemStack>

    @JvmDefault
    fun getExtraTranslations() = this.getAllOutputs().map { it.item.identifier.toString() to it.item.translationKey }.toMap()

}