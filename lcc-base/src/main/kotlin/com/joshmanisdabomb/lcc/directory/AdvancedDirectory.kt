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
    abstract fun defaultContext(): C

    open fun id(name: String) = (this as? RegistryDirectory<*, *, *>)?.regId(name) ?: error("This directory must override the id function or implement RegistryDirectory to get Identifiers.")

    fun init(parameters: C = defaultContext(), filter: (context: DirectoryContext<P>) -> Boolean = { true }) {
        val entries = _entries.values.filter { filter(it.context) }
        entries.forEach { it.initialise(parameters); afterInit(it.entry, it, parameters); it.runListeners(parameters) }
        afterInitAll(entries, filter)
    }

    open fun <V : O> afterInit(initialised: V, entry: DirectoryEntry<out I, out V>, parameters: C) { }

    open fun afterInitAll(initialised: List<DirectoryEntry<out I, out O>>, filter: (context: DirectoryContext<P>) -> Boolean) { }

    operator fun get(value: O) = entries[keyMap[value]] ?: error("This entry is not initialised in this directory.")
    operator fun get(name: String) = all[name] ?: error("There is no entry with this name initialised in this directory.")
    operator fun <V : O> get(property: KProperty<V>) = entries.values.filter { it.delegateName == property.name }

    fun getOrNull(value: O) = entries[keyMap[value]]
    fun getOrNull(name: String) = all[name]

    protected fun <J : I, V : O> entry(initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V, input: DirectoryContext<P>.() -> J) = InstanceDirectoryDelegate(initialiser, input)

    protected fun <K, J : I, V : O> entryMap(initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V, vararg keys: K, inputSupplier: DirectoryContext<P>.(key: K) -> J) = MapDirectoryDelegate(initialiser, *keys, inputSupplier = inputSupplier)

    abstract inner class DirectoryDelegate<J : I, V : O, R> internal constructor(val initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V) {
        protected lateinit var name: String
        protected lateinit var property: KProperty<*>
        protected val entries by lazy { createEntries(name) }
        protected val tags = mutableListOf<String>()
        protected val listeners = mutableListOf<(context: DirectoryEntry<J, V>, parameters: C) -> Unit>()

        operator fun provideDelegate(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): DirectoryDelegate<J, V, R> {
            this.property = property
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

        open fun addInitListener(listener: (context: DirectoryEntry<J, V>, parameters: C) -> Unit): DirectoryDelegate<J, V, R> {
            listeners += listener
            return this
        }
    }

    open inner class InstanceDirectoryDelegate<J : I, V : O> internal constructor(initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V, val input: DirectoryContext<P>.() -> J) : DirectoryDelegate<J, V, V>(initialiser) {

        private var properties: (name: String) -> P = ::defaultProperties

        override fun createEntries(name: String): Map<String, DirectoryEntry<J, V>> {
            val dc = DirectoryContext(name, name, properties(name), tags.toTypedArray(), delegateNumber, entryNumber++)
            return mapOf(name to DirectoryEntry(initialiser, dc, listeners, property) { input(dc) })
        }

        override fun getValue(directory: AdvancedDirectory<I, O, P, C>, property: KProperty<*>): V = entries.values.first().entry

        fun setPropertiesWithName(properties: (name: String) -> P) = this.apply { this.properties = properties }
        fun setProperties(properties: P) = this.apply { this.properties = { properties } }

        override fun addTags(vararg tags: String): InstanceDirectoryDelegate<J, V> = this.also { super.addTags(*tags) }
        override fun addInitListener(listener: (context: DirectoryEntry<J, V>, parameters: C) -> Unit) = this.also { super.addInitListener(listener) }

    }

    open inner class MapDirectoryDelegate<K, J : I, V : O> internal constructor(initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V, vararg val keys: K, val inputSupplier: DirectoryContext<P>.(key: K) -> J) : AdvancedDirectory<I, O, P, C>.DirectoryDelegate<J, V, Map<K, O>>(initialiser) {

        private val map = mutableMapOf<K, DirectoryEntry<J, V>>()

        private var propertySupplier: (key: K, name: String) -> P = { key, name -> defaultProperties(name) }
        private var instanceNameSupplier: (name: String, key: K) -> String = ::defaultInstanceNameFromKey

        override fun createEntries(name: String): Map<String, DirectoryEntry<J, V>> {
            return keys.map {
                val instanceName = instanceNameSupplier(name, it)
                val dc = DirectoryContext(instanceName, name, propertySupplier(it, instanceName), tags.toTypedArray(), delegateNumber, entryNumber++)
                val entry = DirectoryEntry(initialiser, dc, listeners, property) { inputSupplier(dc, it) }
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
        override fun addInitListener(listener: (context: DirectoryEntry<J, V>, parameters: C) -> Unit) = this.also { super.addInitListener(listener) }

    }

    inner class DirectoryContext<P> internal constructor(val name: String, val delegateName: String, val properties: P, val tags: Array<String>, val delegatePosition: Int, val entryPosition: Int) {
        val id by lazy { id(name) }
        val tag get() = tags.firstOrNull()
    }

    inner class DirectoryEntry<J : I, V : O> internal constructor(private val initialiser: (input: J, context: DirectoryContext<P>, parameters: C) -> V, val context: DirectoryContext<P>, val listeners: List<(context: DirectoryEntry<J, V>, parameters: C) -> Unit>, internal val property: KProperty<*>, private val supplier: () -> J) {
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
        val tag get() = context.tag
        val delegatePosition get() = context.delegatePosition
        val entryPosition get() = context.entryPosition
        val id get() = context.id

        fun initialise(parameters: C) {
            _input = supplier().also { _inputInit = true }
            _entry = initialiser(input, this.context, parameters).also { _entryInit = true }
        }

        fun runListeners(parameters: C) = listeners.forEach { it(this, parameters) }
    }

    companion object {
        private fun <K> defaultInstanceNameFromKey(name: String, key: K) = "${(key as? StringIdentifiable)?.asString() ?: key.toString().toLowerCase()}_$name"
    }

}