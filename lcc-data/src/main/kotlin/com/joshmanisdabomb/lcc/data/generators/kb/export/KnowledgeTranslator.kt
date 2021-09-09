package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

open class KnowledgeTranslator(val defaultLocale: String = "en_us") {

    private val sources = mutableListOf<(String) -> Pair<String, String>?>()

    fun addSource(source: (key: String) -> Pair<String, String>?) : KnowledgeTranslator {
        sources.add(source)
        return this
    }

    fun addI18nSource(locale: String = "en_us") = addSource { if (I18n.hasTranslation(it)) locale to I18n.translate(it) else null }

    fun addLangDataSource(lang: LangData) = addSource { lang[it]?.let { lang.locale to it } }

    open fun getTranslations(key: String) = sources.mapNotNull { it(key) }.reversed().toMap()

    open fun getTranslation(key: String, locale: String = defaultLocale) = getTranslations(key).toList().firstOrNull { (k, _) -> k == locale }?.second ?: error("No translation could be found for key $key.")

    open fun textSerialize(text: Text): JsonObject {
        val json = Text.Serializer.toJsonTree(text).asJsonObject
        json.remove("with")
        when (text) {
            is IncludedTranslatableText -> {
                val locales = JsonObject()
                val translations = text.translations
                translations.forEach { (k, v) -> locales.addProperty(k, v) }
                json.add("translations", locales)
            }
            is TranslatableText -> {
                val locales = JsonObject()
                val translations = getTranslations(text.key)
                translations.forEach { (k, v) -> locales.addProperty(k, v) }
                json.add("translations", locales)
            }
        }
        return json
    }

    open fun textResolve(text: Text, locale: String = defaultLocale) = when (text) {
        is IncludedTranslatableText -> String.format(text.translations[locale] ?: getTranslation(text.key, locale), *text.args)
        is TranslatableText -> String.format(getTranslation(text.key, locale), *text.args)
        else -> text.asString()
    }

}