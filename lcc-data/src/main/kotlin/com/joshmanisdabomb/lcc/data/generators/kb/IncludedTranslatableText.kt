package com.joshmanisdabomb.lcc.data.generators.kb

import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.text.TranslatableText

class IncludedTranslatableText(key: String, vararg args: Any) : TranslatableText(key, args) {
    
    val translations = mutableMapOf<String, String>()

    fun translation(content: String, locale: String = "en_us") : IncludedTranslatableText {
        translations[locale] = content
        return this
    }

    fun onExport(exporter: KnowledgeExporter) {
        translations.forEach { (k, v) -> exporter.da.lang[k]?.set(key, v) }
    }

}