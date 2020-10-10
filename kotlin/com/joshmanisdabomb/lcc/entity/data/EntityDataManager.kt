package com.joshmanisdabomb.lcc.entity.data

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.entity.Entity
import net.minecraft.entity.data.TrackedData
import net.minecraft.nbt.CompoundTag

class EntityDataManager<T>(val name: String, val tracker: TrackedData<T>, val defaultValue: T, val tagGet: Function2<CompoundTag, String, T>, val tagPut: Function3<CompoundTag, String, T, Unit>) {

    fun fromTag(tag: CompoundTag) = this.tagGet(tag, "${LCC.modid}_$name")

    fun toTag(tag: CompoundTag, value: T) = tag.also { this.tagPut(tag, "${LCC.modid}_$name", value) }

    fun modifyTag(tag: CompoundTag, modify: (T) -> T) = toTag(tag, modify(copy(fromTag(tag))))

    fun startTracker(entity: Entity) = entity.dataTracker.startTracking(tracker, copy(defaultValue))

    fun fromTracker(entity: Entity) = entity.dataTracker.get(tracker)

    fun toTracker(entity: Entity, value: T) = entity.dataTracker.set(tracker, value)

    fun modifyTracker(entity: Entity, modify: (T) -> T) = toTracker(entity, modify(copy(fromTracker(entity))))

    fun resetTracker(entity: Entity) = toTracker(entity, copy(defaultValue))

    fun read(entity: Entity, tag: CompoundTag) = toTracker(entity, fromTag(tag))

    fun write(entity: Entity, tag: CompoundTag) = toTag(tag, fromTracker(entity))

    private fun copy(value: T) = if (value is CompoundTag) (value.copy() as T) else value

    companion object {
        operator fun invoke(name: String, tracker: TrackedData<Boolean>, defaultValue: Boolean = false) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getBoolean, CompoundTag::putBoolean)

        operator fun invoke(name: String, tracker: TrackedData<Int>, defaultValue: Int = 0) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getInt, CompoundTag::putInt)
        operator fun invoke(name: String, tracker: TrackedData<Long>, defaultValue: Long = 0) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getLong, CompoundTag::putLong)
        operator fun invoke(name: String, tracker: TrackedData<Short>, defaultValue: Short = 0) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getShort, CompoundTag::putShort)
        operator fun invoke(name: String, tracker: TrackedData<Byte>, defaultValue: Byte = 0) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getByte, CompoundTag::putByte)

        operator fun invoke(name: String, tracker: TrackedData<Float>, defaultValue: Float = 0f) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getFloat, CompoundTag::putFloat)

        operator fun invoke(name: String, tracker: TrackedData<Double>, defaultValue: Double = 0.0) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getDouble, CompoundTag::putDouble)

        operator fun invoke(name: String, tracker: TrackedData<CompoundTag>, defaultValue: CompoundTag = CompoundTag()) = EntityDataManager(name, tracker, defaultValue, CompoundTag::getCompound, { t, s, v -> t.put(s, v) })
    }

}