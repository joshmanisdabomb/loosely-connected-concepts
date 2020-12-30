package com.joshmanisdabomb.lcc.data.factory.translation

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.LangData

open class BritishTranslationFactory(val from: String = LangData.defaultLocale, val to: String = "en_gb") : TranslationFactory {

    override fun translate(data: DataAccessor, key: String, path: String, locale: String): String? {
        if (locale != to) return null

        val original = data.lang[from]!![key] ?: return null
        var str = original

        str = str.replace(Regex("(?i)color")) { it.value.replace("or", "our") }
        str = str.replace(Regex("(?i)gray")) { it.value.replace("ay", "ey") }

        if (str == original) return null
        return str
    }

    companion object : BritishTranslationFactory()

}