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

private fun <E> List<E>.head(): E = this.first()
private fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
private fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)
fun <E> List<List<E>>.transpose(): List<List<E>> {
    this.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append(ys.map { it.tail() }.transpose())
            else -> emptyList()
        }
    }
}

fun <E> List<E>.onlyOrNull(): E? = if (this.count() == 1) this.first() else null
fun <E> List<E>.only(): E = this.onlyOrNull() ?: throw NoSuchElementException("List size != 1")