package com.joshmanisdabomb.lcc.utils

import kotlin.reflect.KMutableProperty0

class DecimalTransport(private val property: KMutableProperty0<Float>, val places: Int = 2) {

    val magnitude by lazy { magnitude(places) }

    var value: Long
        get() = property.get().times(magnitude).toLong()
        set(v) = property.set(v.toFloat().div(magnitude))

    var first: Int
        get() = value.and(0xfff.toLong()).toInt()
        set(v) { value.also { from(v, second, third, fourth, places) } }

    var second: Int
        get() = value.and(0xfff000.toLong()).shr(12).toInt()
        set(v) { value.also { from(first, v, third, fourth, places) } }

    var third: Int
        get() = value.and(0xfff000000).shr(24).toInt()
        set(v) { value.also { from(first, second, v, fourth, places) } }

    var fourth: Int
        get() = value.and(0xfff000000000).shr(36).toInt()
        set(v) { value.also { from(first, second, third, v, places) } }

    companion object {
        private fun magnitude(places: Int) = "1".plus("0".repeat(places)).toInt()

        fun from(first: Int, second: Int, third: Int = 0, fourth: Int = 0, places: Int = 2) = (fourth.shl(36) or third.shl(24) or second.shl(12) or first).toFloat().div(magnitude(places).toFloat())
    }

}