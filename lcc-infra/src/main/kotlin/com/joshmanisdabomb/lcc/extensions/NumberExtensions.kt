package com.joshmanisdabomb.lcc.extensions

import java.text.DecimalFormat

private val decimals = mutableMapOf<Int, DecimalFormat>()

fun Number.decimalFormat(places: Int = 2, force: Boolean = false): String {
    if (force) return String.format("%.${places}f", this)
    else return decimals.computeIfAbsent(places) { DecimalFormat("#.".plus("#".repeat(it))) }.format(this)
}

fun Int.exp(exponent: Int): Int {
    var self = this
    repeat(exponent - 1) { self *= this }
    return self
}
fun Int.square(): Int = this.exp(2)
fun Int.sqrt(): Int = kotlin.math.sqrt(this.toFloat()).toInt()

fun Float.exp(exponent: Int): Float {
    var self = this
    repeat(exponent - 1) { self *= this }
    return self
}
fun Float.square(): Float = this.exp(2)
fun Float.sqrt(): Float = kotlin.math.sqrt(this)

fun Double.exp(exponent: Int): Double {
    var self = this
    repeat(exponent - 1) { self *= this }
    return self
}
fun Double.square(): Double = this.exp(2)
fun Double.sqrt(): Double = kotlin.math.sqrt(this)