package com.joshmanisdabomb.lcc.extensions

import net.minecraft.nbt.NbtCompound

const val NBT_BYTE = 1
const val NBT_SHORT = 2
const val NBT_INT = 3
const val NBT_LONG = 4
const val NBT_FLOAT = 5
const val NBT_DOUBLE = 6
const val NBT_BYTE_ARRAY = 7
const val NBT_STRING = 8
const val NBT_LIST = 9
const val NBT_COMPOUND = 10
const val NBT_INT_ARRAY = 11
const val NBT_LONG_ARRAY = 12
const val NBT_NUMERIC = 99

fun NbtCompound.build(key: String, ref: NbtCompound = this.getCompound(key), modify: NbtCompound.() -> Unit): NbtCompound {
    modify(ref)
    this.put(key, ref)
    return ref
}