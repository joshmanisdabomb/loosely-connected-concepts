package com.joshmanisdabomb.lcc.data.generators.kb

import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

object KnowledgeArticleUtils {

    fun customTranslate(text: Text, translator: (key: String) -> String) = when (text) {
        is TranslatableText -> String.format(translator(text.key), *text.args)
        else -> text.asString()
    }

}