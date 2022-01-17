package com.joshmanisdabomb.lcc.extensions

import net.minecraft.nbt.*
import net.minecraft.text.Text
import java.util.*

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

fun NbtCompound.getStringOrNull(key: String) = if (this.contains(key, NBT_STRING)) this.getString(key) else null
fun NbtCompound.getBooleanOrNull(key: String) = if (this.contains(key, NBT_BYTE)) this.getBoolean(key) else null
fun NbtCompound.getByteOrNull(key: String) = if (this.contains(key, NBT_BYTE)) this.getByte(key) else null
fun NbtCompound.getShortOrNull(key: String) = if (this.contains(key, NBT_SHORT)) this.getShort(key) else null
fun NbtCompound.getIntOrNull(key: String) = if (this.contains(key, NBT_INT)) this.getInt(key) else null
fun NbtCompound.getLongOrNull(key: String) = if (this.contains(key, NBT_LONG)) this.getLong(key) else null
fun NbtCompound.getFloatOrNull(key: String) = if (this.contains(key, NBT_FLOAT)) this.getFloat(key) else null
fun NbtCompound.getDoubleOrNull(key: String) = if (this.contains(key, NBT_DOUBLE)) this.getDouble(key) else null
fun NbtCompound.getListOrNull(key: String, type: Int) = if (this.contains(key, NBT_LIST)) this.getList(key, type) else null
fun NbtCompound.getCompoundOrNull(key: String) = if (this.contains(key, NBT_COMPOUND)) this.getCompound(key) else null
fun NbtCompound.getUuidOrNull(key: String) = if (this.containsUuid(key)) this.getUuid(key) else null

fun NbtCompound.putStringOrRemove(key: String, value: String?) = if (value != null) this.putString(key, value) else this.remove(key)
fun NbtCompound.putBooleanOrRemove(key: String, value: Boolean?) = if (value != null) this.putBoolean(key, value) else this.remove(key)
fun NbtCompound.putByteOrRemove(key: String, value: Byte?) = if (value != null) this.putByte(key, value) else this.remove(key)
fun NbtCompound.putShortOrRemove(key: String, value: Short?) = if (value != null) this.putShort(key, value) else this.remove(key)
fun NbtCompound.putIntOrRemove(key: String, value: Int?) = if (value != null) this.putInt(key, value) else this.remove(key)
fun NbtCompound.putLongOrRemove(key: String, value: Long?) = if (value != null) this.putLong(key, value) else this.remove(key)
fun NbtCompound.putFloatOrRemove(key: String, value: Float?) = if (value != null) this.putFloat(key, value) else this.remove(key)
fun NbtCompound.putDoubleOrRemove(key: String, value: Double?) = if (value != null) this.putDouble(key, value) else this.remove(key)
fun NbtCompound.putListOrRemove(key: String, value: NbtList?) { if (value != null) this.put(key, value) else this.remove(key) }
fun NbtCompound.putCompoundOrRemove(key: String, value: NbtCompound?) { if (value != null) this.put(key, value) else this.remove(key) }
fun NbtCompound.putUuidOrRemove(key: String, value: UUID?) = if (value != null) this.putUuid(key, value) else this.remove(key)

fun <T> NbtCompound.getCompoundObject(key: String, read: (nbt: NbtCompound) -> T) = read(this.getCompound(key))
fun <T> NbtCompound.getCompoundObjectOrNull(key: String, read: (nbt: NbtCompound) -> T?) = if (this.contains(key, NBT_COMPOUND)) read(this.getCompound(key)) else null
fun <T> NbtCompound.putCompoundObject(key: String, obj: T, write: (obj: T) -> NbtCompound): NbtCompound {
    val nbt = write(obj)
    put(key, nbt)
    return nbt
}
fun <T> NbtCompound.putCompoundObjectOrRemove(key: String, obj: T?, write: (obj: T) -> NbtCompound) { if (obj != null) this.putCompoundObject(key, obj, write) else this.remove(key) }
fun <T> NbtCompound.getStringObject(key: String, read: (string: String) -> T) = read(this.getString(key))
fun <T> NbtCompound.getStringObjectOrNull(key: String, read: (string: String) -> T?) = if (this.contains(key, NBT_STRING)) read(this.getString(key)) else null
fun <T> NbtCompound.putStringObject(key: String, obj: T, write: (obj: T) -> String): String {
    val str = write(obj)
    putString(key, str)
    return str
}
fun <T> NbtCompound.putStringObjectOrRemove(key: String, obj: T?, write: (obj: T) -> String) { if (obj != null) this.putStringObject(key, obj, write) else this.remove(key) }

fun NbtCompound.getText(key: String) = getStringObject(key, Text.Serializer::fromJson)
fun NbtCompound.getTextOrNull(key: String) = getStringObjectOrNull(key, Text.Serializer::fromJson)
fun NbtCompound.putText(key: String, text: Text) = putStringObject(key, text, Text.Serializer::toJson)
fun NbtCompound.putTextOrRemove(key: String, value: Text?) { if (value != null) this.putText(key, value) else this.remove(key) }
fun NbtCompound.getStringUuid(key: String) = getStringObject(key, UUID::fromString)
fun NbtCompound.getStringUuidOrNull(key: String) = getStringObjectOrNull(key) {
    try {
        UUID.fromString(it)
    } catch (e: IllegalArgumentException) {
        null
    }
}
fun NbtCompound.putStringUuid(key: String, uuid: UUID) = putStringObject(key, uuid, UUID::toString)
fun NbtCompound.putStringUuidOrRemove(key: String, value: UUID?) { if (value != null) this.putStringUuid(key, value) else this.remove(key) }

fun NbtList.asStringList() = this.map(NbtElement::asString)
fun NbtList.asBooleanList() = this.asByteList().map { it != 0.toByte() }
fun NbtList.asByteList() = this.mapNotNull { (it as? AbstractNbtNumber)?.byteValue() }
fun NbtList.asShortList() = this.mapNotNull { (it as? AbstractNbtNumber)?.shortValue() }
fun NbtList.asIntList() = this.mapNotNull { (it as? AbstractNbtNumber)?.intValue() }
fun NbtList.asLongList() = this.mapNotNull { (it as? AbstractNbtNumber)?.longValue() }
fun NbtList.asFloatList() = this.mapNotNull { (it as? AbstractNbtNumber)?.floatValue() }
fun NbtList.asDoubleList() = this.mapNotNull { (it as? AbstractNbtNumber)?.doubleValue() }
fun NbtList.asListList() = this.mapNotNull { it as? NbtList }
fun NbtList.asCompoundList() = this.mapNotNull { it as? NbtCompound }
fun <T> NbtList.asCompoundObjectList(map: (nbt: NbtCompound) -> T?) = this.mapNotNull { (it as? NbtCompound)?.let(map) }
fun <T> NbtList.asStringObjectList(map: (nbt: String) -> T?) = this.mapNotNull { it.asString()?.let(map) }
fun NbtList.asStringUuidList() = this.asStringObjectList(UUID::fromString)
fun NbtList.asTextList() = this.asStringObjectList(Text.Serializer::fromJson)

fun NbtList.addString(value: String, position: Int = size) = this.add(position, NbtString.of(value))
fun NbtList.addBoolean(value: Boolean, position: Int = size) = this.add(position, NbtByte.of(value))
fun NbtList.addByte(value: Byte, position: Int = size) = this.add(position, NbtByte.of(value))
fun NbtList.addShort(value: Short, position: Int = size) = this.add(position, NbtShort.of(value))
fun NbtList.addInt(value: Int, position: Int = size) = this.add(position, NbtInt.of(value))
fun NbtList.addLong(value: Long, position: Int = size) = this.add(position, NbtLong.of(value))
fun NbtList.addFloat(value: Float, position: Int = size) = this.add(position, NbtFloat.of(value))
fun NbtList.addDouble(value: Double, position: Int = size) = this.add(position, NbtDouble.of(value))
fun <T> NbtList.addCompoundObject(value: T, write: (obj: T) -> NbtCompound, position: Int = size) = this.add(position, write(value))
fun <T> NbtList.addStringObject(value: T, write: (obj: T) -> String, position: Int = size) = this.addString(write(value), position)
fun NbtList.addStringUuid(value: UUID, position: Int = size) = this.addStringObject(value, UUID::toString, position)
fun NbtList.addText(value: Text, position: Int = size) = this.addStringObject(value, Text.Serializer::toJson, position)

fun NbtList.addStrings(values: Iterable<String>) = this.addAll(values.map(NbtString::of))
fun NbtList.addBooleans(values: Iterable<Boolean>) = this.addAll(values.map(NbtByte::of))
fun NbtList.addBytes(values: Iterable<Byte>) = this.addAll(values.map(NbtByte::of))
fun NbtList.addShorts(values: Iterable<Short>) = this.addAll(values.map(NbtShort::of))
fun NbtList.addInts(values: Iterable<Int>) = this.addAll(values.map(NbtInt::of))
fun NbtList.addLongs(values: Iterable<Long>) = this.addAll(values.map(NbtLong::of))
fun NbtList.addFloats(values: Iterable<Float>) = this.addAll(values.map(NbtFloat::of))
fun NbtList.addDoubles(values: Iterable<Double>) = this.addAll(values.map(NbtDouble::of))
fun <T> NbtList.addCompoundObjects(values: Iterable<T>, map: (obj: T) -> NbtCompound?) = this.addAll(values.mapNotNull(map))
fun <T> NbtList.addStringObjects(values: Iterable<T>, map: (obj: T) -> String?) = this.addStrings(values.mapNotNull(map))
fun NbtList.addStringUuid(values: Iterable<UUID>) = this.addStringObjects(values, UUID::toString)
fun NbtList.addText(values: Iterable<Text>) = this.addStringObjects(values, Text.Serializer::toJson)

fun NbtCompound.modifyString(key: String, ref: String = this.getString(key), modify: String.() -> String): String {
    val new = modify(ref)
    this.putString(key, new)
    return new
}
fun NbtCompound.modifyBoolean(key: String, ref: Boolean = this.getBoolean(key), modify: Boolean.() -> Boolean): Boolean {
    val new = modify(ref)
    this.putBoolean(key, new)
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
fun NbtCompound.modifyUuid(key: String, ref: UUID = this.getUuid(key), modify: UUID.() -> Unit): UUID {
    modify(ref)
    this.putUuid(key, ref)
    return ref
}
fun <T> NbtCompound.modifyCompoundObject(key: String, read: (nbt: NbtCompound) -> T, write: (obj: T) -> NbtCompound, modify: T.() -> Unit): NbtCompound {
    val new = getCompoundObject(key, read)
    modify(new)
    return putCompoundObject(key, new, write)
}
fun <T> NbtCompound.modifyStringObject(key: String, read: (string: String) -> T, write: (obj: T) -> String, modify: T.() -> Unit): String {
    val new = getStringObject(key, read)
    modify(new)
    return putStringObject(key, new, write)
}
fun NbtCompound.modifyStringUuid(key: String, modify: UUID.() -> Unit) = modifyStringObject(key, UUID::fromString, UUID::toString, modify)
fun NbtCompound.modifyText(key: String, modify: Text.() -> Unit) = modifyStringObject(key, { Text.Serializer.fromJson(it)!! }, Text.Serializer::toJson, modify)

fun NbtCompound.getStringList(key: String, ref: NbtList = this.getList(key, NBT_STRING)) = ref.asStringList()
fun NbtCompound.getBooleanList(key: String, ref: NbtList = this.getList(key, NBT_BYTE)) = ref.asBooleanList()
fun NbtCompound.getByteList(key: String, ref: NbtList = this.getList(key, NBT_BYTE)) = ref.asByteList()
fun NbtCompound.getShortList(key: String, ref: NbtList = this.getList(key, NBT_SHORT)) = ref.asShortList()
fun NbtCompound.getIntList(key: String, ref: NbtList = this.getList(key, NBT_INT)) = ref.asIntList()
fun NbtCompound.getLongList(key: String, ref: NbtList = this.getList(key, NBT_LONG)) = ref.asLongList()
fun NbtCompound.getFloatList(key: String, ref: NbtList = this.getList(key, NBT_FLOAT)) = ref.asFloatList()
fun NbtCompound.getDoubleList(key: String, ref: NbtList = this.getList(key, NBT_DOUBLE)) = ref.asDoubleList()
fun NbtCompound.getListList(key: String, ref: NbtList = this.getList(key, NBT_LIST)) = ref.asListList()
fun NbtCompound.getCompoundList(key: String, ref: NbtList = this.getList(key, NBT_COMPOUND)) = ref.asCompoundList()
fun <T> NbtCompound.getCompoundObjectList(key: String, ref: NbtList = this.getList(key, NBT_COMPOUND), map: (nbt: NbtCompound) -> T?) = ref.asCompoundObjectList(map)
fun <T> NbtCompound.getStringObjectList(key: String, ref: NbtList = this.getList(key, NBT_STRING), map: (string: String) -> T?) = ref.asStringObjectList(map)
fun NbtCompound.getStringUuidList(key: String, ref: NbtList = this.getList(key, NBT_STRING)) = getStringObjectList(key, ref, UUID::fromString)
fun NbtCompound.getTextList(key: String, ref: NbtList = this.getList(key, NBT_STRING)) = getStringObjectList(key, ref, Text.Serializer::fromJson)

fun NbtCompound.putStringList(key: String, list: List<String>) = NbtList().apply { addAll(list.map(NbtString::of)); this@putStringList.put(key, this) }
fun NbtCompound.putBooleanList(key: String, list: List<Boolean>) = NbtList().apply { addAll(list.map(NbtByte::of)); this@putBooleanList.put(key, this) }
fun NbtCompound.putByteList(key: String, list: List<Byte>) = NbtList().apply { addAll(list.map(NbtByte::of)); this@putByteList.put(key, this) }
fun NbtCompound.putShortList(key: String, list: List<Short>) = NbtList().apply { addAll(list.map(NbtShort::of)); this@putShortList.put(key, this) }
fun NbtCompound.putIntList(key: String, list: List<Int>) = NbtList().apply { addAll(list.map(NbtInt::of)); this@putIntList.put(key, this) }
fun NbtCompound.putLongList(key: String, list: List<Long>) = NbtList().apply { addAll(list.map(NbtLong::of)); this@putLongList.put(key, this) }
fun NbtCompound.putFloatList(key: String, list: List<Float>) = NbtList().apply { addAll(list.map(NbtFloat::of)); this@putFloatList.put(key, this) }
fun NbtCompound.putDoubleList(key: String, list: List<Double>) = NbtList().apply { addAll(list.map(NbtDouble::of)); this@putDoubleList.put(key, this) }
fun NbtCompound.putListList(key: String, list: List<NbtList>) = NbtList().apply { addAll(list); this@putListList.put(key, this) }
fun NbtCompound.putCompoundList(key: String, list: List<NbtCompound>) = NbtList().apply { addAll(list); this@putCompoundList.put(key, this) }
fun <T> NbtCompound.putCompoundObjectList(key: String, list: List<T>, write: (obj: T) -> NbtCompound?) = NbtList().apply { addAll(list.mapNotNull(write)); this@putCompoundObjectList.put(key, this) }
fun <T> NbtCompound.putStringObjectList(key: String, list: List<T>, write: (obj: T) -> String?) = putStringList(key, list.mapNotNull(write))
fun NbtCompound.putStringUuidList(key: String, list: List<UUID>) = putStringObjectList(key, list, UUID::toString)
fun NbtCompound.putTextList(key: String, list: List<Text>) = putStringObjectList(key, list, Text.Serializer::toJson)

fun NbtCompound.modifyStringList(key: String, ref: NbtList = this.getList(key, NBT_STRING), modify: MutableList<String>.() -> List<String>?): NbtList {
    val objects = getStringList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putStringList(key, new)
}
fun NbtCompound.modifyBooleanList(key: String, ref: NbtList = this.getList(key, NBT_BYTE), modify: MutableList<Boolean>.() -> List<Boolean>?): NbtList {
    val objects = getBooleanList(key, ref).toMutableList()
    val new = modify(objects) ?: objects
    return putBooleanList(key, new)
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
fun <T> NbtCompound.modifyCompoundObjectList(key: String, read: (el: NbtCompound) -> T?, write: (el: T) -> NbtCompound?, ref: NbtList = this.getList(key, NBT_COMPOUND), modify: MutableList<T>.() -> List<T>?): NbtList {
    val objects: MutableList<T> = getCompoundObjectList(key, ref, read).toMutableList()
    val new = modify(objects) ?: objects
    return putCompoundObjectList(key, new, write)
}
fun <T> NbtCompound.modifyStringObjectList(key: String, read: (el: String) -> T?, write: (el: T) -> String?, ref: NbtList = this.getList(key, NBT_STRING), modify: MutableList<T>.() -> List<T>?): NbtList {
    val objects: MutableList<T> = getStringObjectList(key, ref, read).toMutableList()
    val new = modify(objects) ?: objects
    return putStringObjectList(key, new, write)
}
fun NbtCompound.modifyStringUuidList(key: String, ref: NbtList = this.getList(key, NBT_STRING), modify: MutableList<UUID>.() -> List<UUID>?) = modifyStringObjectList(key, UUID::fromString, UUID::toString, ref, modify)
fun NbtCompound.modifyTextList(key: String, ref: NbtList = this.getList(key, NBT_STRING), modify: MutableList<Text>.() -> List<Text>?) = modifyStringObjectList(key, Text.Serializer::fromJson, Text.Serializer::toJson, ref, modify)

fun NbtCompound.forEach(consumer: (key: String, value: NbtElement) -> Unit) = this.keys.forEach { consumer(it, this.get(it)!!) }
fun NbtCompound.forEachString(consumer: (key: String, value: String) -> Unit) = this.keys.forEach { consumer(it, this.getString(it)) }
fun NbtCompound.forEachBoolean(consumer: (key: String, value: Boolean) -> Unit) = this.keys.forEach { consumer(it, this.getBoolean(it)) }
fun NbtCompound.forEachByte(consumer: (key: String, value: Byte) -> Unit) = this.keys.forEach { consumer(it, this.getByte(it)) }
fun NbtCompound.forEachShort(consumer: (key: String, value: Short) -> Unit) = this.keys.forEach { consumer(it, this.getShort(it)) }
fun NbtCompound.forEachInt(consumer: (key: String, value: Int) -> Unit) = this.keys.forEach { consumer(it, this.getInt(it)) }
fun NbtCompound.forEachLong(consumer: (key: String, value: Long) -> Unit) = this.keys.forEach { consumer(it, this.getLong(it)) }
fun NbtCompound.forEachFloat(consumer: (key: String, value: Float) -> Unit) = this.keys.forEach { consumer(it, this.getFloat(it)) }
fun NbtCompound.forEachDouble(consumer: (key: String, value: Double) -> Unit) = this.keys.forEach { consumer(it, this.getDouble(it)) }
fun NbtCompound.forEachUuid(consumer: (key: String, value: UUID) -> Unit) = this.keys.forEach { consumer(it, this.getUuid(it)) }
fun NbtCompound.forEachList(type: Int, consumer: (key: String, value: NbtList) -> Unit) = this.keys.forEach { consumer(it, this.getList(it, type)) }
fun NbtCompound.forEachCompound(consumer: (key: String, value: NbtCompound) -> Unit) = this.keys.forEach { consumer(it, this.getCompound(it)) }
fun <T> NbtCompound.forEachCompoundObject(read: (el: NbtCompound) -> T?, consumer: (key: String, value: T) -> Unit) = this.keys.forEach { consumer(it, read(this.getCompound(it)) ?: return@forEach) }
fun <T> NbtCompound.forEachStringObject(read: (el: String) -> T?, consumer: (key: String, value: T) -> Unit) = this.keys.forEach { consumer(it, read(this.getString(it)) ?: return@forEach) }
fun NbtCompound.forEachStringUuid(consumer: (key: String, value: UUID) -> Unit) = forEachStringObject(UUID::fromString, consumer)
fun NbtCompound.forEachText(consumer: (key: String, value: Text) -> Unit) = forEachStringObject(Text.Serializer::fromJson, consumer)

fun NbtCompound.forEachStringList(consumer: (key: String, value: List<String>) -> Unit) = this.keys.forEach { consumer(it, this.getStringList(it)) }
fun NbtCompound.forEachBooleanList(consumer: (key: String, value: List<Boolean>) -> Unit) = this.keys.forEach { consumer(it, this.getBooleanList(it)) }
fun NbtCompound.forEachByteList(consumer: (key: String, value: List<Byte>) -> Unit) = this.keys.forEach { consumer(it, this.getByteList(it)) }
fun NbtCompound.forEachShortList(consumer: (key: String, value: List<Short>) -> Unit) = this.keys.forEach { consumer(it, this.getShortList(it)) }
fun NbtCompound.forEachIntList(consumer: (key: String, value: List<Int>) -> Unit) = this.keys.forEach { consumer(it, this.getIntList(it)) }
fun NbtCompound.forEachLongList(consumer: (key: String, value: List<Long>) -> Unit) = this.keys.forEach { consumer(it, this.getLongList(it)) }
fun NbtCompound.forEachFloatList(consumer: (key: String, value: List<Float>) -> Unit) = this.keys.forEach { consumer(it, this.getFloatList(it)) }
fun NbtCompound.forEachDoubleList(consumer: (key: String, value: List<Double>) -> Unit) = this.keys.forEach { consumer(it, this.getDoubleList(it)) }
fun NbtCompound.forEachListList(consumer: (key: String, value: List<NbtList>) -> Unit) = this.keys.forEach { consumer(it, this.getListList(it)) }
fun NbtCompound.forEachCompoundList(consumer: (key: String, value: List<NbtCompound>) -> Unit) = this.keys.forEach { consumer(it, this.getCompoundList(it)) }
fun <T> NbtCompound.forEachCompoundObjectList(read: (el: NbtCompound) -> T?, consumer: (key: String, value: List<T>) -> Unit) = this.keys.forEach { consumer(it, this.getCompoundObjectList(it, map = read)) }
fun <T> NbtCompound.forEachStringObjectList(read: (el: String) -> T?, consumer: (key: String, value: List<T>) -> Unit) = this.keys.forEach { consumer(it, this.getStringObjectList(it, map = read)) }
fun NbtCompound.forEachStringUuidList(consumer: (key: String, value: List<UUID>) -> Unit) = forEachStringObjectList(UUID::fromString, consumer)
fun NbtCompound.forEachTextList(consumer: (key: String, value: List<Text>) -> Unit) = forEachStringObjectList(Text.Serializer::fromJson, consumer)