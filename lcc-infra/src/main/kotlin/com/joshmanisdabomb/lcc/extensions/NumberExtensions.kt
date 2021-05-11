package com.joshmanisdabomb.lcc.extensions

import java.text.DecimalFormat

private val decimals = mutableMapOf<Int, DecimalFormat>()

fun Number.decimalFormat(places: Int = 2, force: Boolean = false): String {
    if (force) return String.format("%.${places}f", this)
    else return decimals.computeIfAbsent(places) { DecimalFormat("#.".plus("#".repeat(it))) }.format(this)
}