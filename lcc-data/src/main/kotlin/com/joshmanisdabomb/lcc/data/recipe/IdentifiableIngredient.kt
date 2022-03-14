package com.joshmanisdabomb.lcc.data.recipe

import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey

interface IdentifiableIngredient {

    fun getItemStacks() : List<ItemStack>

    fun getTags() : List<TagKey<Item>>

    fun getTagStacks() : List<ItemStack>

    fun getAllStacks() = getItemStacks() + getTagStacks()

    val id get() = getTags().firstOrNull()?.id ?: getItemStacks().firstOrNull()?.item?.identifier

}