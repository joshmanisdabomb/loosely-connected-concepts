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

fun NbtList.addStrings(values: Iterable<String>) = this.addAll(values.map(NbtString::of))
fun NbtList.addBytes(values: Iterable<Byte>) = this.addAll(values.map(NbtByte::of))
fun NbtList.addShorts(values: Iterable<Short>) = this.addAll(values.map(NbtShort::of))
fun NbtList.addInts(values: Iterable<Int>) = this.addAll(values.map(NbtInt::of))
fun NbtList.addLongs(values: Iterable<Long>) = this.addAll(values.map(NbtLong::of))
fun NbtList.addFloats(values: Iterable<Float>) = this.addAll(values.map(NbtFloat::of))
fun NbtList.addDoubles(values: Iterable<Double>) = this.addAll(values.map(NbtDouble::of))

fun NbtCompound.getStringList(key: String) = this.getList(key, NBT_STRING).map(NbtElement::asString)
fun NbtCompound.getByteList(key: String) = this.getList(key, NBT_BYTE).mapNotNull { (it as? AbstractNbtNumber)?.byteValue() }
fun NbtCompound.getShortList(key: String) = this.getList(key, NBT_SHORT).mapNotNull { (it as? AbstractNbtNumber)?.shortValue() }
fun NbtCompound.getIntList(key: String) = this.getList(key, NBT_INT).mapNotNull { (it as? AbstractNbtNumber)?.intValue() }
fun NbtCompound.getLongList(key: String) = this.getList(key, NBT_LONG).mapNotNull { (it as? AbstractNbtNumber)?.longValue() }
fun NbtCompound.getFloatList(key: String) = this.getList(key, NBT_FLOAT).mapNotNull { (it as? AbstractNbtNumber)?.floatValue() }
fun NbtCompound.getDoubleList(key: String) = this.getList(key, NBT_DOUBLE).mapNotNull { (it as? AbstractNbtNumber)?.doubleValue() }
fun NbtCompound.getListList(key: String) = this.getList(key, NBT_LIST).mapNotNull { it as? NbtList }
fun NbtCompound.getCompoundList(key: String) = this.getList(key, NBT_COMPOUND).mapNotNull { it as? NbtCompound }
fun <T> NbtCompound.getObjectList(key: String, map: (nbt: NbtCompound) -> T) = this.getList(key, NBT_COMPOUND).mapNotNull { (it as? NbtCompound)?.let { map(it) } }.toList()