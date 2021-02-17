package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.StringIdentifiable
import kotlin.reflect.KProperty

abstract class AdvancedDirectory<I, O, P, C> {

    private val delegates = mutableMapOf<String, DirectoryDelegate<out I, out O, *>>()
    private var delegateNumber = 0

    private val _entries = mutableMapOf<String, DirectoryEntry<out I, out O>>()
    val entries by lazy { _entries.toMap() }
    private var entryNumber = 0

    private val keyMap by lazy { entries.mapNotNull { (it.value.entryOrNull ?: return@mapNotNull null) to it.key }.toMap() }
    val all by lazy { keyMap.map { it.value to it.key }.toMap() }

    abstract fun defaultProperties(name: String): P
    abstract fun defaultContext(name: String): C

    fun init() = init { true }
    fun init(filter: (context: DirectoryContext<P, C>) -> Boolean) {
        val entries = _entries.values.filter { filter(it.context) }
        entries.forEach { it.initialise(); afterInit(it.entry, it) }
        afterInitAll(entries, filter)
    }

    open fun <V : O> afterInit(initialised: V, entry: DirectoryEntry<out I, out V>) { }

    open fun afterInitAll(initialised: List<DirectoryEntry<out I, out O>>, filter: (context: DirectoryContext<P, C>) -> Boolean) { }

    operator fun get(value: O) = entries[keyMap[value]] ?: error("This entry is not initialised in this directory.")
    operator fun get(name: String) = all[name] ?: error("There is no entry with this name initialised in this directory.")

    protected fun <J : I, V : O> entry(initialiser: (input: J, context: DirectoryContext<P, C>) -> V, input: DirectoryContext<P, C>.() -> J) = InstanceDirectoryDelegate(initialiser, input)

    protected fun <K, J : I, V : O> entryMap(initialiser: (input: J, context: DirectoryContext<P, C>) -> V, vararg keys: K, inputSupplier: DirectoryContext<P, C>.(key: K) -> J) = MapDirectoryDelegate(initialiser, *keys, inputSupplier = inputSupplier)

    abstract inner class DirectoryDelegate<J : I, V : O, R> internal constructor(val initialiser: (input: J, context: DirectoryContext<P, C>) -> V) {
        protected lateinit var name: String
        protected val entries by lazy { createEntries(name) }
        protected val tags = mutableListOf<String>()

        operator fun provideDelegate(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): DirectoryDelegate<J, V, R> {
            name = property.name
            delegates[name] = this
            this@AdvancedDirectory._entries += entries
            delegateNumber++
            return this
        }

        internal abstract fun createEntries(name: String): Map<String, DirectoryEntry<J, V>>

        abstract operator fun getValue(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): R

        open fun addTags(vararg tags: String): DirectoryDelegate<J, V, R> {
            this.tags.addAll(tags)
            return this
        }
    }

    open inner class InstanceDirectoryDelegate<J : I, V : O> internal constructor(initialiser: (input: J, context: DirectoryContext<P, C>) -> V, val input: DirectoryContext<P, C>.() -> J) : DirectoryDelegate<J, V, V>(initialiser) {

        private var properties: (name: String) -> P = ::defaultProperties

        override fun createEntries(name: String): Map<String, DirectoryEntry<J, V>> {
            val dc = DirectoryContext(name, name, properties(name), defaultContext(name), tags.toTypedArray(), delegateNumber, entryNumber++)
            return mapOf(name to DirectoryEntry(initialiser, dc) { input(dc) })
        }

        override fun getValue(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): V = entries.values.first().entry

        fun setProperties(properties: (name: String) -> P) = this.apply { this.properties = properties }
        fun setProperties(properties: P) = this.apply { this.properties = { properties } }

        override fun addTags(vararg tags: String): InstanceDirectoryDelegate<J, V> = this.also { super.addTags(*tags) }

    }

    open inner class MapDirectoryDelegate<K, J : I, V : O> internal constructor(initialiser: (input: J, context: DirectoryContext<P, C>) -> V, vararg val keys: K, val inputSupplier: DirectoryContext<P, C>.(key: K) -> J) : AdvancedDirectory<I, O, P, C>.DirectoryDelegate<J, V, Map<K, O>>(initialiser) {

        private val map = mutableMapOf<K, DirectoryEntry<J, V>>()

        private var propertySupplier: (key: K, name: String) -> P = { key, name -> defaultProperties(name) }
        private var instanceNameSupplier: (name: String, key: K) -> String = ::defaultInstanceNameFromKey

        override fun createEntries(name: String): Map<String, DirectoryEntry<J, V>> {
            return keys.map {
                val instanceName = instanceNameSupplier(name, it)
                val dc = DirectoryContext(instanceName, name, propertySupplier(it, instanceName), defaultContext(instanceName), tags.toTypedArray(), delegateNumber, entryNumber++)
                val entry = DirectoryEntry(initialiser, dc) { inputSupplier(dc, it) }
                map[it] = entry
                instanceName to entry
            }.toMap()
        }

        override fun getValue(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): Map<K, V> {
            return map.mapValues { (_, v) -> v.entry }
        }

        fun setPropertySupplierWithName(propertySupplier: (key: K, name: String) -> P) = this.apply { this.propertySupplier = propertySupplier }
        fun setPropertySupplier(propertySupplier: (key: K) -> P) = this.setPropertySupplierWithName { k, n -> propertySupplier(k) }

        fun setInstanceNameSupplier(instanceNameSupplier: (delegateName: String, key : K) -> String) = this.apply { this.instanceNameSupplier = instanceNameSupplier }

        override fun addTags(vararg tags: String): MapDirectoryDelegate<K, J, V> = this.also { super.addTags(*tags) }

    }

    class DirectoryContext<P, C> internal constructor(val name: String, val delegateName: String, val properties: P, val context: C, val tags: Array<String>, val delegatePosition: Int, val entryPosition: Int) {
        val id by lazy {(this as? RegistryDirectory2<*, *, *>)?.id(name) ?: error("This directory must implement RegistryDirectory to get Identifiers.") }
    }

    inner class DirectoryEntry<J : I, V : O> internal constructor(private val initialiser: (input: J, context: DirectoryContext<P, C>) -> V, val context: DirectoryContext<P, C>, private val supplier: () -> J) {
        private var _input: J? = null
        private var _inputInit: Boolean = false
        val input get() = if (_inputInit) this._input as J else error("Final input for $name not stored yet. Has this entry been initialised?")
        val inputOrNull get() = this._input

        private var _entry: V? = null
        private var _entryInit: Boolean = false
        val entry get() = if (_entryInit) this._entry as V else error("Final value for $name not stored yet. Has this entry been initialised?")
        val entryOrNull get() = this._entry

        val name get() = context.name
        val delegateName get() = context.delegateName
        val properties get() = context.properties
        val tags get() = context.tags
        val delegatePosition get() = context.delegatePosition
        val entryPosition get() = context.entryPosition

        fun initialise() {
            _input = supplier().also { _inputInit = true }
            _entry = initialiser(input, context).also { _entryInit = true }
        }
    }

    companion object {
        private fun <K> defaultInstanceNameFromKey(name: String, key: K) = "${(key as? StringIdentifiable)?.asString() ?: key.toString().toLowerCase()}_$name"
    }

}