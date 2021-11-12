package com.joshmanisdabomb.lcc.data.factory.translation

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LangBatch

open class BasicTranslationFactory(vararg val replacements: Pair<String, String>) : TranslationFactory {

    override fun translate(data: DataAccessor, key: String, path: String, locale: String): String? {
        if (locale != LangBatch.defaultLocale) return null
        var str = path.split('_').joinToString(" ") { it.capitalize() }
        replacements.forEach { str = str.replace(it.first, it.second) }
        return str
    }

    companion object : BasicTranslationFactory()

}