package com.joshmanisdabomb.lcc.data.container

abstract class DataContainer<T, U> {

    private val affects = mutableListOf<T>()
    private val factories = mutableListOf<U>()

    fun affects(entry: T) { affects.add(entry) }

    open fun add(factory: U) = factories.add(factory).let { this }

    abstract fun apply(factory: U, entry: T)

    fun init(path: String, match: T?) {
        if (affects.isEmpty() && match != null) affects(match)
        factories.forEach { f -> affects.forEach { a -> apply(f, a) } }
    }

}