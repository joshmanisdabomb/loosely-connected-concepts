package com.joshmanisdabomb.lcc.knowledge

interface KnowledgeRecipeTranslationHandler {

    @JvmDefault
    fun getExtraTranslations(): Map<String, String> = emptyMap()

}