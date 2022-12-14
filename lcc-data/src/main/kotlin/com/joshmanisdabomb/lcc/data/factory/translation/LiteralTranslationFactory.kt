package com.joshmanisdabomb.lcc.data.factory.translation;

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LangBatch

class LiteralTranslationFactory(val translation: String, vararg val locales: String = arrayOf(LangBatch.defaultLocale)) : TranslationFactory {

    override fun translate(data: DataAccessor, key: String, path: String, locale: String): String? {
        if (!locales.contains(locale)) return null
        return translation
    }

}
