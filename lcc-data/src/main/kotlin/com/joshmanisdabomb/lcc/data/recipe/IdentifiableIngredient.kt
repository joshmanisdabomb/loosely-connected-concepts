package com.joshmanisdabomb.lcc.data.recipe

import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag

interface IdentifiableIngredient {

    fun getItemStacks() : List<ItemStack>

    fun getTags() : List<Tag<Item>>

    fun getTagStacks() : List<ItemStack> = getTags().flatMap { it.values().map { it.stack() } }

    fun getAllStacks() = getItemStacks() + getTagStacks()

    val id get() = (getTags().firstOrNull() as? Tag.Identified)?.id ?: getItemStacks().firstOrNull()?.item?.identifier

}