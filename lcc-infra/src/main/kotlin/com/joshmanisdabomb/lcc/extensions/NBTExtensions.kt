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

fun NbtList.asStringList() = this.map(NbtElement::asString)
fun NbtList.asByteList() = this.mapNotNull { (it as? AbstractNbtNumber)?.byteValue() }
fun NbtList.asShortList() = this.mapNotNull { (it as? AbstractNbtNumber)?.shortValue() }
fun NbtList.asIntList() = this.mapNotNull { (it as? AbstractNbtNumber)?.intValue() }
fun NbtList.asLongList() = this.mapNotNull { (it as? AbstractNbtNumber)?.longValue() }
fun NbtList.asFloatList() = this.mapNotNull { (it as? AbstractNbtNumber)?.floatValue() }
fun NbtList.asDoubleList() = this.mapNotNull { (it as? AbstractNbtNumber)?.doubleValue() }
fun NbtList.asListList() = this.mapNotNull { it as? NbtList }
fun NbtList.asCompoundList() = this.mapNotNull { it as? NbtCompound }
fun <T> NbtList.asObjectList(map: (nbt: NbtCompound) -> T?) = this.mapNotNull { (it as? NbtCompound)?.let(map) }

fun NbtList.addString(value: String, position: Int = size) = this.add(position, NbtString.of(value))
fun NbtList.addByte(value: Byte, position: Int = size) = this.add(position, NbtByte.of(value))
fun NbtList.addShort(value: Short, position: Int = size) = this.add(position, NbtShort.of(value))
fun NbtList.addInt(value: Int, position: Int = size) = this.add(position, NbtInt.of(value))
fun NbtList.addLong(value: Long, position: Int = size) = this.add(position, NbtLong.of(value))
fun NbtList.addFloat(value: Float, position: Int = size) = this.add(position, NbtFloat.of(value))
fun NbtList.addDouble(value: Double, position: Int = size) = this.add(position, NbtDouble.of(value))

fun NbtList.addStrings(values: Iterable<String>) = this.addAll(values.map(NbtString::of))
fun NbtList.addBytes(values: Iterable<Byte>) = this.addAll(values.map(NbtByte::of))
fun NbtList.addShorts(values: Iterable<Short>) = this.addAll(values.map(NbtShort::of))
fun NbtList.addInts(values: Iterable<Int>) = this.addAll(values.map(NbtInt::of))
fun NbtList.addLongs(values: Iterable<Long>) = this.addAll(values.map(NbtLong::of))
fun NbtList.addFloats(values: Iterable<Float>) = this.addAll(values.map(NbtFloat::of))
fun NbtList.addDoubles(values: Iterable<Double>) = this.addAll(values.map(NbtDouble::of))

fun NbtCompound.getStringOrNull(key: String) = if (this.contains(key, NBT_STRING)) this.getString(key) else null
fun NbtCompound.getByteOrNull(key: String) = if (this.contains(key, NBT_BYTE)) this.getByte(key) else null
fun NbtCompound.getShortOrNull(key: String) = if (this.contains(key, NBT_SHORT)) this.getShort(key) else null
fun NbtCompound.getIntOrNull(key: String) = if (this.contains(key, NBT_INT)) this.getInt(key) else null
fun NbtCompound.getLongOrNull(key: String) = if (this.contains(key, NBT_LONG)) this.getLong(key) else null
fun NbtCompound.getFloatOrNull(key: String) = if (this.contains(key, NBT_FLOAT)) this.getFloat(key) else null
fun NbtCompound.getDoubleOrNull(key: String) = if (this.contains(key, NBT_DOUBLE)) this.getDouble(key) else null
fun NbtCompound.getListOrNull(key: String, type: Int) = if (this.contains(key, NBT_LIST)) this.getList(key, type) else null
fun NbtCompound.getCompoundOrNull(key: String) = if (this.contains(key, NBT_COMPOUND)) this.getCompound(key) else null
fun NbtCompound.getUuidOrNull(key: String) = if (this.containsUuid(key)) this.getUuid(key) else null

fun <T> NbtCompound.getObject(key: String, read: (nbt: NbtCompound) -> T, ref: NbtCompound = this.getCompound(key)) = read(ref)
fun <T> NbtCompound.getObjectOrNull(key: String, read: (nbt: NbtCompound) -> T) = if (this.contains(key, NBT_COMPOUND)) read(this.getCompound(key)) else null
fun <T> NbtCompound.putObject(key: String, obj: T, write: (obj: T) -> NbtCompound): NbtCompound {
    val nbt = write(obj)
    put(key, nbt)
    return nbt
}

fun NbtCompound.modifyString(key: String, ref: String = this.getString(key), modify: String.() -> String): String {
    val new = modify(ref)
    this.putString(key, new)
    return new
}
fun NbtCompound.modifyByte(key: String, ref: Byte = this.getByte(key), modify: Byte.() -> Byte): Byte {
    val new = modify(ref)
    this.putByte(key, new)
    return new
}
fun NbtCompound.modifyShort(key: String, ref: Short = this.getShort(key), modify: Short.() -> Short): Short {
    val new = modify(ref)
    this.putShort(key, new)
    return new
}
fun NbtCompound.modifyInt(key: String, ref: Int = this.getInt(key), modify: Int.() -> Int): Int {
    val new = modify(ref)
    this.putInt(key, new)
    return new
}
fun NbtCompound.modifyLong(key: String, ref: Long = this.getLong(key), modify: Long.() -> Long): Long {
    val new = modify(ref)
    this.putLong(key, new)
    return new
}
fun NbtCompound.modifyFloat(key: String, ref: Float = this.getFloat(key), modify: Float.() -> Float): Float {
    val new = modify(ref)
    this.putFloat(key, new)
    return new
}
fun NbtCompound.modifyDouble(key: String, ref: Double = this.getDouble(key), modify: Double.() -> Double): Double {
    val new = modify(ref)
    this.putDouble(key, new)
    return new
}
fun NbtCompound.modifyCompound(key: String, ref: NbtCompound = this.getCompound(key), modify: NbtCompound.() -> Unit): NbtCompound {
    modify(ref)
    this.put(key, ref)
    return ref
}
fun <T> NbtCompound.modifyObject(key: String, read: (nbt: NbtCompound) -> T, write: (obj: T) -> NbtCompound, ref: NbtCompound = this.getCompound(key), modify: T.() -> Unit): NbtCompound {
    val new = getObject(key, read)
    modify(new)
    return putObject(key, new, write)
}

fun NbtCompound.getStringList(key: String, ref: NbtList = this.getList(key, NBT_STRING)) = ref.asStringList()
fun NbtCompound.getByteList(key: String, ref: NbtList = this.getList(key, NBT_BYTE)) = ref.asByteList()
fun NbtCompound.getShortList(key: String, ref: NbtList = this.getList(key, NBT_SHORT)) = ref.asShortList()
fun NbtCompound.getIntList(key: String, ref: NbtList = this.getList(key, NBT_INT)) = ref.asIntList()
fun NbtCompound.getLongList(key: String, ref: NbtList = this.getList(key, NBT_LONG)) = ref.asLongList()
fun NbtCompound.getFloatList(key: String, ref: NbtList = this.getList(key, NBT_FLOAT)) = ref.asFloatList()
fun NbtCompound.getDoubleList(key: String, ref: NbtList = this.getList(key, NBT_DOUBLE)) = ref.asDoubleList()
fun NbtCompound.getListList(key: String, ref: NbtList = this.getList(key, NBT_LIST)) = ref.asListList()
fun NbtCompound.getCompoundList(key: String, ref: NbtList = this.getList(key, NBT_COMPOUND)) = ref.asCompoundList()
fun <T> NbtCompound.getObjectList(key: String, ref: NbtList = this.getList(key, NBT_COMPOUND), map: (nbt: NbtCompound) -> T?) = ref.asObjectList(map)

fun NbtCompound.putStringList(key: String, list: List<String>) = NbtList().apply { addAll(list.map(NbtString::of)); this@putStringList.put(key, this) }
fun NbtCompound.putByteList(key: String, list: List<Byte>) = NbtList().apply { addAll(list.map(NbtByte::of)); this@putByteList.put(key, this) }
fun NbtCompound.putShortList(key: String, list: List<Short>) = NbtList().apply { addAll(list.map(NbtShort::of)); this@putShortList.put(key, this) }
fun NbtCompound.putIntList(key: String, list: List<Int>) = NbtList().apply { addAll(list.map(NbtInt::of)); this@putIntList.put(key, this) }
fun NbtCompound.putLongList(key: String, list: List<Long>) = NbtList().apply { addAll(list.map(NbtLong::of)); this@putLongList.put(key, this) }
fun NbtCompound.putFloatList(key: String, list: List<Float>) = NbtList().apply { addAll(list.map(NbtFloat::of)); this@putFloatList.put(key, this) }
fun NbtCompound.putDoubleList(key: String, list: List<Double>) = NbtList().apply { addAll(list.map(NbtDouble::of)); this@putDoubleList.put(key, this) }
fun NbtCompound.putListList(key: String, list: List<NbtList>) = NbtList().apply { addAll(list); this@putListList.put(key, this) }
fun NbtCompound.putCompoundList(key: String, list: List<NbtCompound>) = NbtList().apply { addAll(list); this@putCompoundList.put(key, this) }
fun <T> NbtCompound.putObjectList(key: String, list: List<T>, write: (obj: T) -> NbtCompound?) = NbtList().apply { addAll(list.mapNotNull(write)); this@putObjectList.put(key, this) }

fun NbtCompound.modifyStringList(key: String, ref: NbtList = this.getList(key, NBT_STRING), modify: MutableList<String>.() -> List<String>?): NbtList {
    val objects = getStringList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putStringList(key, new)
}
fun NbtCompound.modifyByteList(key: String, ref: NbtList = this.getList(key, NBT_BYTE), modify: MutableList<Byte>.() -> List<Byte>?): NbtList {
    val objects = getByteList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putByteList(key, new)
}
fun NbtCompound.modifyShortList(key: String, ref: NbtList = this.getList(key, NBT_SHORT), modify: MutableList<Short>.() -> List<Short>?): NbtList {
    val objects = getShortList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putShortList(key, new)
}
fun NbtCompound.modifyIntList(key: String, ref: NbtList = this.getList(key, NBT_INT), modify: MutableList<Int>.() -> List<Int>?): NbtList {
    val objects = getIntList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putIntList(key, new)
}
fun NbtCompound.modifyLongList(key: String, ref: NbtList = this.getList(key, NBT_LONG), modify: MutableList<Long>.() -> List<Long>?): NbtList {
    val objects = getLongList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putLongList(key, new)
}
fun NbtCompound.modifyFloatList(key: String, ref: NbtList = this.getList(key, NBT_FLOAT), modify: MutableList<Float>.() -> List<Float>?): NbtList {
    val objects = getFloatList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putFloatList(key, new)
}
fun NbtCompound.modifyDoubleList(key: String, ref: NbtList = this.getList(key, NBT_DOUBLE), modify: MutableList<Double>.() -> List<Double>?): NbtList {
    val objects = getDoubleList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putDoubleList(key, new)
}
fun NbtCompound.modifyListList(key: String, ref: NbtList = this.getList(key, NBT_LIST), modify: MutableList<NbtList>.() -> List<NbtList>?): NbtList {
    val objects = getListList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putListList(key, new)
}
fun NbtCompound.modifyCompoundList(key: String, ref: NbtList = this.getList(key, NBT_COMPOUND), modify: MutableList<NbtCompound>.() -> List<NbtCompound>?): NbtList {
    val objects = getCompoundList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putCompoundList(key, new)
}
fun <T> NbtCompound.modifyObjectList(key: String, read: (el: NbtCompound) -> T?, write: (el: T) -> NbtCompound?, ref: NbtList = this.getList(key, NBT_COMPOUND), modify: MutableList<T>.() -> List<T>?): NbtList {
    val objects: MutableList<T> = getObjectList(key, ref, read).toMutableList()
    val new = modify(objects) ?: objects
    return putObjectList(key, new, write)
}

fun NbtCompound.forEach(consumer: (key: String, value: NbtElement) -> Unit) = this.keys.forEach { consumer(it, this.get(it)!!) }
fun NbtCompound.forEachString(consumer: (key: String, value: String) -> Unit) = this.keys.forEach { consumer(it, this.getString(it)) }
fun NbtCompound.forEachByte(consumer: (key: String, value: Byte) -> Unit) = this.keys.forEach { consumer(it, this.getByte(it)) }
fun NbtCompound.forEachShort(consumer: (key: String, value: Short) -> Unit) = this.keys.forEach { consumer(it, this.getShort(it)) }
fun NbtCompound.forEachInt(consumer: (key: String, value: Int) -> Unit) = this.keys.forEach { consumer(it, this.getInt(it)) }
fun NbtCompound.forEachLong(consumer: (key: String, value: Long) -> Unit) = this.keys.forEach { consumer(it, this.getLong(it)) }
fun NbtCompound.forEachFloat(consumer: (key: String, value: Float) -> Unit) = this.keys.forEach { consumer(it, this.getFloat(it)) }
fun NbtCompound.forEachDouble(consumer: (key: String, value: Double) -> Unit) = this.keys.forEach { consumer(it, this.getDouble(it)) }
fun NbtCompound.forEachList(type: Int, consumer: (key: String, value: NbtList) -> Unit) = this.keys.forEach { consumer(it, this.getList(it, type)) }
fun NbtCompound.forEachStringList(consumer: (key: String, value: List<String>) -> Unit) = this.keys.forEach { consumer(it, this.getStringList(it)) }
fun NbtCompound.forEachByteList(consumer: (key: String, value: List<Byte>) -> Unit) = this.keys.forEach { consumer(it, this.getByteList(it)) }
fun NbtCompound.forEachShortList(consumer: (key: String, value: List<Short>) -> Unit) = this.keys.forEach { consumer(it, this.getShortList(it)) }
fun NbtCompound.forEachIntList(consumer: (key: String, value: List<Int>) -> Unit) = this.keys.forEach { consumer(it, this.getIntList(it)) }
fun NbtCompound.forEachLongList(consumer: (key: String, value: List<Long>) -> Unit) = this.keys.forEach { consumer(it, this.getLongList(it)) }
fun NbtCompound.forEachFloatList(consumer: (key: String, value: List<Float>) -> Unit) = this.keys.forEach { consumer(it, this.getFloatList(it)) }
fun NbtCompound.forEachDoubleList(consumer: (key: String, value: List<Double>) -> Unit) = this.keys.forEach { consumer(it, this.getDoubleList(it)) }
fun NbtCompound.forEachListList(consumer: (key: String, value: List<NbtList>) -> Unit) = this.keys.forEach { consumer(it, this.getListList(it)) }
fun NbtCompound.forEachCompoundList(consumer: (key: String, value: List<NbtCompound>) -> Unit) = this.keys.forEach { consumer(it, this.getCompoundList(it)) }
fun <T> NbtCompound.forEachObjectList(read: (el: NbtCompound) -> T?, consumer: (key: String, value: List<T>) -> Unit) = this.keys.forEach { consumer(it, this.getObjectList(it, map = read)) }
fun NbtCompound.forEachCompound(consumer: (key: String, value: NbtCompound) -> Unit) = this.keys.forEach { consumer(it, this.getCompound(it)) }
fun <T> NbtCompound.forEachObject(read: (el: NbtCompound) -> T?, consumer: (key: String, value: T) -> Unit) = this.keys.forEach { consumer(it, read(this.getCompound(it)) ?: return@forEach) }