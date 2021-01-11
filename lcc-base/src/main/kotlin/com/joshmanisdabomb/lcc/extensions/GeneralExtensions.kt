package com.joshmanisdabomb.lcc.extensions

fun <T, V> Iterable<T>.setThrough(value: V, function: V.(item: T) -> V): V {
    var obj = value
    this.forEach { obj = function(obj, it) }
    return obj
}

fun <T, V> Array<T>.setThrough(value: V, function: V.(item: T) -> V): V {
    var obj = value
    this.forEach { obj = function(obj, it) }
    return obj
}