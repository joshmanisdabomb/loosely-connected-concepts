package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.lang.LangData
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.lib.recipe.LCCRecipe
import net.minecraft.client.resource.language.I18n
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootTable
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

    fun withTranslationJson(translations: Map<String, String>, json: JsonObject = JsonObject()): JsonObject {
        for ((k, v) in translations) json.addProperty(k, v)
        return json
    }

    fun withTranslationJson(key: String, json: JsonObject = JsonObject()) = withTranslationJson(getTranslations(key), json)

    open fun textSerialize(text: Text): JsonObject {
        val json = Text.Serializer.toJsonTree(text).asJsonObject
        json.remove("with")
        when (text) {
            is IncludedTranslatableText -> {
                json.add("translations", withTranslationJson(text.translations))
            }
            is TranslatableText -> {
                json.add("translations", withTranslationJson(text.key))
            }
        }
        return json
    }

    open fun textResolve(text: Text, locale: String = defaultLocale) = when (text) {
        is IncludedTranslatableText -> String.format(text.translations[locale] ?: getTranslation(text.key, locale), *text.args)
        is TranslatableText -> String.format(getTranslation(text.key, locale), *text.args)
        else -> text.asString()
    }

    open fun itemTranslationsJson(vararg items: Item) : JsonObject {
        val tjson = JsonObject()
        items.forEach {
            tjson.add(it.identifier.toString(), withTranslationJson(it.translationKey))
        }
        return tjson
    }

    open fun stackTranslationsJson(vararg stacks: ItemStack) = itemTranslationsJson(*stacks.map { it.item }.distinct().toTypedArray())

    open fun recipeTranslationsJson(recipe: RecipeJsonProvider, vararg items: Item): JsonObject {
        val json = itemTranslationsJson(*items)
        val r = recipe.serializer.read(recipe.recipeId, recipe.toJson())
        if (r is LCCRecipe) {
            r.getExtraTranslations().forEach { (k, v) ->
                json.add(k, withTranslationJson(v))
            }
        }
        return json
    }

    open fun lootTranslationsJson(loot: LootTable.Builder, vararg items: Item): JsonObject {
        return itemTranslationsJson(*items)
    }

}