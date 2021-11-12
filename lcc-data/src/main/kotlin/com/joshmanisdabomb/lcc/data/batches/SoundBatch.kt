package com.joshmanisdabomb.lcc.data.batches

import com.joshmanisdabomb.lcc.data.generators.sound.SoundProperties

class SoundBatch {

    private val map = mutableMapOf<String, SoundProperties>()

    operator fun get(key: String) = map[key]
    operator fun get(value: SoundProperties) = map.filterValues { it === value }.keys.firstOrNull()

    operator fun set(key: String, value: SoundProperties) = map.put(key, value)

    fun getSounds() = map.toMap()

}