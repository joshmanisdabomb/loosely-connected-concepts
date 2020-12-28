package com.joshmanisdabomb.lcc.extensions

fun <T> Boolean.to(`true`: T, `false`: T) = if (this) `true` else `false`

fun Boolean.toInt(`true`: Int = 1, `false`: Int = 0) = to(`true`, `false`)