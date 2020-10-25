package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.StringIdentifiable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

abstract class ThingDirectory<V, P> {

    private val delegates by lazy {
        (this::class as KClass<ThingDirectory<V, P>>).declaredMemberProperties.filter{ it.isAccessible = true; it.getDelegate(this) is ThingDirectory<*, *>.ThingDelegate<*, *> && !it.name.startsWith("_") }.map { it.name to it.getDelegate(this) }.filterIsInstance<Pair<String, ThingDelegate<V, *>>>().toMap()
    }

    private fun things(predicate: (name: String, properties: P) -> Boolean): Map<String, V> {
        return delegates.filter { (k, v) -> predicate(k, v.properties) }.map { (k, v) -> v.getAll(k) }.flatMap { it.toList() }.toMap()
    }

    private fun properties(predicate: (name: String, properties: P) -> Boolean): Map<String, P> {
        return delegates.filter { (k, v) -> predicate(k, v.properties) }.map { (k, v) -> v.getAll(k) to v.properties }.flatMap { it.first.keys.map { it2 -> it2 to it.second } }.toMap()
    }

    fun init(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }) {
        loadAll(predicate)
        registerAll(things(predicate), properties(predicate))
    }

    private fun loadAll(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }) {
        val kprops = (this::class as KClass<ThingDirectory<V, P>>).declaredMemberProperties
        delegates.filter { (k, v) -> predicate(k, v.properties) }.forEach { (k, v) -> v.getValue(this, kprops.first { it.name == k }) }
    }

    protected open fun registerAll(things: Map<String, V>, properties: Map<String, P>) {

    }

    protected fun <R : V> create(properties: P = Unit as P, supplier: (properties: P) -> R): ThingOneDelegate<R> = ThingOneDelegate({ _, p -> supplier(p) }, properties)

    protected fun <R : V> createNP(properties: P = Unit as P, supplier: (name: String, properties: P) -> R): ThingOneDelegate<R> = ThingOneDelegate(supplier, properties)

    protected fun <R : V> createN(supplier: (name: String) -> R): ThingOneDelegate<R> = ThingOneDelegate({ n, _ -> supplier(n) }, Unit as P)

    protected fun <R : V, K> createMap(vararg keys: K, keyToString: (name: String, key: K) -> String = ::defaultKeyStringMap, properties: P = Unit as P, supplier: (key: K, name: String, properties: P) -> R): ThingMapDelegate<out K, R> = ThingMapDelegate(keys, keyToString, supplier, properties)

    inner abstract class ThingDelegate<R : V, S> internal constructor(val properties: P = Unit as P) {
        private var store: S? = null

        operator fun getValue(dir: ThingDirectory<in R, P>, property: KProperty<*>): S {
            store = store ?: supply(dir, property)
            return store!!
        }

        protected abstract fun supply(dir: ThingDirectory<in R, P>, property: KProperty<*>): S

        fun getAll(name: String) = getAll(name, store!!)

        protected abstract fun getAll(name: String, body: S): Map<String, R>
    }

    inner class ThingOneDelegate<R : V> internal constructor(private val supplier: (name: String, properties: P) -> R, properties: P = Unit as P) : ThingDelegate<R, R>(properties) {
        override fun supply(dir: ThingDirectory<in R, P>, property: KProperty<*>) = supplier(property.name, properties)
        override fun getAll(name: String, body: R) = mapOf(name to body)
    }

    inner class ThingMapDelegate<K, R : V> internal constructor(private val keys: Array<K>, private val keyToString: (name: String, key: K) -> String = ::defaultKeyStringMap, private val supplier: (key: K, name: String, properties: P) -> R, properties: P = Unit as P) : ThingDelegate<R, Map<K, R>>(properties) {
        override fun supply(dir: ThingDirectory<in R, P>, property: KProperty<*>) = keys.map { it to supplier(it, property.name, properties) }.toMap()
        override fun getAll(name: String, body: Map<K, R>) = body.mapKeys { (k, _) -> keyToString(name, k) }
    }

    companion object {
        private fun <K> defaultKeyStringMap(name: String, key: K) = if (key == null) name else "${(key as? StringIdentifiable)?.asString() ?: key.toString().toLowerCase()}_$name"
    }

}