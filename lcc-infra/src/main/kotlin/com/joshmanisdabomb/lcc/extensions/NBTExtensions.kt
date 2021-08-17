package com.joshmanisdabomb.lcc.extensions

import net.minecraft.nbt.*

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

fun NbtList.addString(value: String) = this.add(NbtString.of(value))
fun NbtList.addByte(value: Byte) = this.add(NbtByte.of(value))
fun NbtList.addShort(value: Short) = this.add(NbtShort.of(value))
fun NbtList.addInt(value: Int) = this.add(NbtInt.of(value))
fun NbtList.addLong(value: Long) = this.add(NbtLong.of(value))
fun NbtList.addFloat(value: Float) = this.add(NbtFloat.of(value))
fun NbtList.addDouble(value: Double) = this.add(NbtDouble.of(value))