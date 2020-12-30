package com.joshmanisdabomb.lcc.data.factory.translation

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.LangData

class TransformTranslationFactory(vararg val locales: String = arrayOf(LangData.defaultLocale), val translator: (original: String) -> String) : TranslationFactory {

    override fun translate(data: DataAccessor, key: String, path: String, locale: String): String? {
        if (!locales.contains(locale)) return null
        val str = data.lang[locale]!![key] ?: return null
        return translator(str)
    }

}