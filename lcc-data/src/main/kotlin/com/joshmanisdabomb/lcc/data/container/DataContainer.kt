package com.joshmanisdabomb.lcc.data.container

abstract class DataContainer<T, U> {

    val affects = mutableListOf<T>()
    val factories = mutableListOf<U>()

    open fun affects(entry: T) = affects.add(entry).let { this }

    open fun affects(entries: List<T>) = affects.addAll(entries).let { this }

    open fun add(factory: U) = factories.add(factory).let { this }

    abstract fun apply(factory: U, entry: T)

    fun init(path: String, match: T?) {
        if (affects.isEmpty() && match != null) affects(match)
        factories.forEach { f -> affects.forEach { a -> apply(f, a) } }
    }

}