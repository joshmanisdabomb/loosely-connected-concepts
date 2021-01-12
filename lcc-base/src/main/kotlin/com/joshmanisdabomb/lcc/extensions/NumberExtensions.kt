package com.joshmanisdabomb.lcc.extensions

import java.text.DecimalFormat

private val decimal = DecimalFormat("#.##")

val Number.formatted get() = decimal.format(this)