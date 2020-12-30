package com.joshmanisdabomb.lcc.data.factory.translation

import com.joshmanisdabomb.lcc.data.DataAccessor

object StorageTranslationFactory : TranslationFactory {

    override fun translate(data: DataAccessor, key: String, path: String, locale: String): String? {
        val str = data.lang[locale]!![key] ?: return null
        return "Block of ${str.replace(" Block", "")}"
    }

}
