package com.joshmanisdabomb.lcc.directory

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

abstract class ThingDirectory<V, P> {

    private val delegates by lazy { (this::class as KClass<ThingDirectory<V, P>>).declaredMemberProperties.filter{ it.isAccessible = true; it.getDelegate(this) is ThingDirectory<*, *>.ThingDelegate<*> && !it.name.startsWith("_") }.map { it.name to it.getDelegate(this) }.filterIsInstance<Pair<String, ThingDelegate<V>>>().toMap() }

    private fun things(predicate: (name: String, properties: P) -> Boolean): Map<String, V> {
        return delegates.filter { (k, v) -> predicate(k, v.properties) }.mapValues { (k, _) -> (this::class as KClass<ThingDirectory<V, P>>).declaredMemberProperties.first { it.name == k }.get(this) as V }
    }

    private fun properties(predicate: (name: String, properties: P) -> Boolean): Map<String, P> {
        return delegates.filter { (k, v) -> predicate(k, v.properties) }.mapValues { (k, v) -> v.properties }
    }

    protected fun <R : V> create(properties: P = Unit as P, supplier: (properties: P) -> R): ThingDelegate<R> = ThingDelegate({ _, p -> supplier(p) }, properties)

    protected fun <R : V> createNP(properties: P = Unit as P, supplier: (name: String, properties: P) -> R): ThingDelegate<R> = ThingDelegate(supplier, properties)

    protected fun <R : V> createN(supplier: (name: String) -> R): ThingDelegate<R> = ThingDelegate({ n, _ -> supplier(n) }, Unit as P)

    fun init(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }) {
        registerAll(things(predicate), properties(predicate))
    }

    protected open fun registerAll(things: Map<String, V>, properties: Map<String, P>) {

    }

    inner class ThingDelegate<R : V> internal constructor(private val supplier: (name: String, properties: P) -> R, val properties: P = Unit as P) {
        private var store: R? = null

        operator fun getValue(dir: ThingDirectory<in R, P>, property: KProperty<*>): R {
            store = store ?: supplier(property.name, properties)
            return store!!
        }
    }

}