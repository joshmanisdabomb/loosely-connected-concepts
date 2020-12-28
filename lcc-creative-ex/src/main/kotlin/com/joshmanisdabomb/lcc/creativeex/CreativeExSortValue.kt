package com.joshmanisdabomb.lcc.creativeex

class CreativeExSortValue {

    private val values = mutableMapOf<CreativeExCategory, Int>()

    operator fun get(category: CreativeExCategory) = values.getOrPut(category) { 0 }

    operator fun set(category: CreativeExCategory, value: Int) = values.put(category, value)

    fun increment(category: CreativeExCategory, offset: Int = 1): Int {
        val value = this[category]
        this[category] = value + offset
        return value
    }

}
