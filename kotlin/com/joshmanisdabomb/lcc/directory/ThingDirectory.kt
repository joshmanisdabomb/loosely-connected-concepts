package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.StringIdentifiable
import kotlin.reflect.KProperty

abstract class ThingDirectory<V, P> {

    private val delegates = mutableMapOf<String, ThingDelegate<out V, *>>()

    fun things(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }): Map<String, V> {
        return delegates.filter { (k, v) -> v.getAllProperties(k).forEach { (k2, v2) -> if (predicate(k2, v2)) return@filter true }; false }.map { (k, v) -> val p = v.getAllProperties(k); v.getAll(k).filter { (k2, v2) -> predicate(k2, p[k2] ?: error("Property key map null error.")) } }.flatMap { it.toList() }.toMap()
    }

    private fun properties(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }): Map<String, P> {
        return delegates.map { (k, v) -> v.getAllProperties(k) }.flatMap { it.toList() }.toMap().filter { (k, v) -> predicate(k, v) }
    }

    open fun init(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }) {
        loadAll(predicate)
        registerAll(things(predicate), properties(predicate))
    }

    private fun loadAll(predicate: (name: String, properties: P) -> Boolean = { s, p -> true }) {
        delegates.filter { (k, v) -> v.getAllProperties(k).forEach { (k2, v2) -> if (predicate(k2, v2)) return@filter true }; false }.forEach { (k, v) -> v.load() }
    }

    protected open fun registerAll(things: Map<String, V>, properties: Map<String, P>) {

    }

    protected open fun getDefaultProperty(): P = Unit as P

    protected fun <R : V> create(properties: P = getDefaultProperty(), supplier: (properties: P) -> R): ThingOneDelegate<R> = ThingOneDelegate({ _, p -> supplier(p) }, properties)

    protected fun <R : V> createWithNameProperties(properties: P = getDefaultProperty(), supplier: (name: String, properties: P) -> R): ThingOneDelegate<R> = ThingOneDelegate(supplier, properties)

    protected fun <R : V> createWithName(supplier: (name: String) -> R): ThingOneDelegate<R> = ThingOneDelegate({ n, _ -> supplier(n) }, getDefaultProperty())

    protected fun <R : V, K> createMap(vararg keys: K, keyToString: (name: String, key: K) -> String = ::defaultKeyStringMap, propertySupplier: (key: K) -> P = { getDefaultProperty() }, supplier: (key: K, name: String, properties: P) -> R): ThingMapDelegate<out K, R> = ThingMapDelegate(keys, keyToString, supplier, propertySupplier)

    val all by lazy { things() }
    val allProperties by lazy { properties() }

    operator fun get(key: String) = all[key]

    operator fun get(thing: V) = all.filterValues { it == thing }.keys.firstOrNull()

    fun getProperties(key: String) = allProperties[key]

    fun getProperties(thing: V): P? {
        return allProperties[this[thing] ?: return null]
    }

    abstract inner class ThingDelegate<R : V, S> internal constructor(val sortOrder: Int = _sortOrder++) {
        protected lateinit var name: String
        private var store: S? = null

        operator fun provideDelegate(dir: ThingDirectory<in R, P>, property: KProperty<*>): ThingDelegate<R, S> {
            name = property.name
            delegates[name] = this
            return this
        }

        operator fun getValue(dir: ThingDirectory<in R, P>, property: KProperty<*>): S {
            return load()
        }

        fun load(): S {
            store = store ?: supply()
            return store!!
        }

        protected abstract fun supply(): S

        fun getAll(name: String) = getAll(name, store!!)

        protected abstract fun getAll(name: String, body: S): Map<String, R>

        abstract fun getAllProperties(name: String): Map<String, P>
    }

    inner class ThingOneDelegate<R : V> internal constructor(private val supplier: (name: String, properties: P) -> R, val properties: P = getDefaultProperty()) : ThingDelegate<R, R>() {
        override fun supply() = supplier(name, properties)
        override fun getAll(name: String, body: R) = mapOf(name to body)

        override fun getAllProperties(name: String) = mapOf(name to properties)
    }

    inner class ThingMapDelegate<K, R : V> internal constructor(private val keys: Array<K>, private val keyToString: (name: String, key: K) -> String = ::defaultKeyStringMap, private val supplier: (key: K, name: String, properties: P) -> R, propertySupplier: (key: K) -> P = { getDefaultProperty() }) : ThingDelegate<R, Map<K, R>>() {
        private val allProperties = keys.map { it to propertySupplier(it) }.toMap()

        override fun supply() = keys.map { it to supplier(it, name, allProperties[it] ?: error("Property key map null error.")) }.toMap()
        override fun getAll(name: String, body: Map<K, R>) = body.mapKeys { (k, _) -> keyToString(name, k) }

        override fun getAllProperties(name: String) = keys.map { keyToString(name, it) to (allProperties[it] ?: error("Property key map null error.")) }.toMap()
    }

    companion object {
        private fun <K> defaultKeyStringMap(name: String, key: K) = if (key == null) name else "${(key as? StringIdentifiable)?.asString() ?: key.toString().toLowerCase()}_$name"

        private var _sortOrder = 0
    }

}