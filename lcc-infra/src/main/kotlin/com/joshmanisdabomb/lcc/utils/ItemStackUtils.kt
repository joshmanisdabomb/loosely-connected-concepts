package com.joshmanisdabomb.lcc.utils

import com.google.gson.JsonObject
import com.mojang.serialization.Dynamic
import com.mojang.serialization.JsonOps
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps

object ItemStackUtils {

    fun fromTagIntCount(tag: NbtCompound) = ItemStack.fromNbt(tag).apply { count = tag.getInt("Count") }

    fun toTagIntCount(stack: ItemStack, tag: NbtCompound) = stack.writeNbt(tag).apply { tag.apply { remove("Count"); putInt("Count", stack.count) } }

    fun toJson(stack: ItemStack): JsonObject {
        val json = Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, toTagIntCount(stack, NbtCompound())).asJsonObject
        json.add("count", json.remove("Count"))
        return json
    }

}