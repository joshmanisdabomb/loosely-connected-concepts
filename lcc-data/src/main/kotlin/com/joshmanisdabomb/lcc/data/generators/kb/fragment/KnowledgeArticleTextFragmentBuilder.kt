package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.Item
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.world.biome.Biome

class KnowledgeArticleTextFragmentBuilder(content: (defaultKey: String) -> Text) : KnowledgeArticleFragmentBuilder() {

    constructor(content: Text) : this({ content })
    constructor(content: String, locale: String = "en_us") : this(locale to content)
    constructor(vararg translations: Pair<String, String>) : this({ TranslatableText(it) }) {
        _translations += translations
    }

    constructor(block: Block) : this(block.name)
    constructor(item: Item) : this(item.name)
    constructor(entity: EntityType<*>) : this(entity.name)
    constructor(enchantment: Enchantment) : this(TranslatableText(enchantment.translationKey))
    constructor(effect: StatusEffect) : this(effect.name)
    constructor(biome: Biome) : this(TranslatableText("biome.${BuiltinRegistries.BIOME.getId(biome).toString().replace(":", ".")}"))

    private val _translations: MutableMap<String, String> = mutableMapOf()
    val translations by lazy { _translations.toMap() }

    override val type = "text"
    val content by lazy { content(defaultTranslationKey) }

    fun addTranslation(content: String, locale: String = "en_us"): KnowledgeArticleTextFragmentBuilder {
        _translations[locale] = content
        return this
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        json.add("content", Text.Serializer.toJsonTree(content))

        return json
    }

}
