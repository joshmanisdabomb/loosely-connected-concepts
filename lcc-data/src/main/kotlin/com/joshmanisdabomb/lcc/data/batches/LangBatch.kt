package com.joshmanisdabomb.lcc.data.batches

import com.google.common.collect.HashBasedTable

class LangBatch(vararg val locales: String = arrayOf(defaultLocale)) {

    private val table = HashBasedTable.create<String, String, String>()

    operator fun set(key: String, value: String) = set(defaultLocale, key, value)
    operator fun set(locale: String, key: String, value: String) = table.put(locale, key, value)

    operator fun get(locale: String, key: String) = table.get(locale, key)
    operator fun get(key: String) = get(defaultLocale, key)

    fun getFirst(locale: String, key: String, vararg fallbacks: String = arrayOf(defaultLocale)) = get(locale, key) ?: fallbacks.mapNotNull { get(it, key) }.firstOrNull()

    fun getLocales() = table.rowKeySet().plus(locales)
    fun getKeys() = table.columnKeySet()

    fun getTranslationsByLocale() = locales.associateWith { emptyMap<String, String>() }.plus(table.rowMap())
    fun getTranslationsByKey() = table.columnMap()

    fun getTranslationsIn(locale: String = defaultLocale) = table.row(locale)
    fun getTranslationsFor(key: String) = table.column(key)

    companion object {
        val defaultLocale = "en_us"
    }

}
