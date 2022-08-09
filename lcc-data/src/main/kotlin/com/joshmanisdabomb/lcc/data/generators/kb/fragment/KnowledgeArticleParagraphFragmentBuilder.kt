package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.Item
import net.minecraft.text.Text
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.world.biome.Biome

class KnowledgeArticleParagraphFragmentBuilder() : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    override val type = "paragraph"

    override val section get() = container.section

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()

    fun addFragment(content: KnowledgeArticleFragmentBuilder) : KnowledgeArticleParagraphFragmentBuilder {
        list.add(content)
        content.container = this
        return this
    }

    fun addText(content: Text) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(content))
    fun addText(content: String, locale: String = "en_us") : KnowledgeArticleParagraphFragmentBuilder = addText(locale to content)
    fun addText(vararg translations: Pair<String, String>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(*translations))
    fun addText(block: Block) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(block))
    fun addText(item: Item) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(item))
    fun addText(entity: EntityType<*>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(entity))
    fun addText(enchantment: Enchantment) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(enchantment))
    fun addText(effect: StatusEffect) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(effect))
    fun addText(biome: Biome) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(biome))

    fun addFormatText(master: String, vararg replacements: KnowledgeArticleParagraphFragmentBuilder.() -> Unit, delimiter: String = "%s", locale: String = "en_us"): KnowledgeArticleParagraphFragmentBuilder {
        val parts = master.split(delimiter)
        if (replacements.size != parts.size-1) error("Incorrect amount of arguments given to format text paragraph builder.")
        parts.forEachIndexed { k, v ->
            replacements.getOrNull(k-1)?.invoke(this)
            addText(v, locale)
        }
        return this
    }

    fun addPluralisedText(block: Block) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${block.translationKey}")))
    fun addPluralisedText(item: Item) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${item.translationKey}")))
    fun addPluralisedText(entity: EntityType<*>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${entity.translationKey}")))
    fun addPluralisedText(enchantment: Enchantment) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${enchantment.translationKey}")))
    fun addPluralisedText(effect: StatusEffect) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${effect.translationKey}")))
    fun addPluralisedText(biome: Biome) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.biome.${BuiltinRegistries.BIOME.getId(biome).toString().replace(":", ".")}")))

    fun addLink(to: KnowledgeArticleIdentifier, content: Text) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(to).addFragment(KnowledgeArticleTextFragmentBuilder(content)))
    fun addLink(to: KnowledgeArticleIdentifier, content: String, locale: String = "en_us") : KnowledgeArticleParagraphFragmentBuilder = addLink(to, locale to content)
    fun addLink(to: KnowledgeArticleIdentifier, vararg translations: Pair<String, String>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(to).addFragment(KnowledgeArticleTextFragmentBuilder(*translations)))
    fun addLink(block: Block) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBlock(block)).addFragment(KnowledgeArticleTextFragmentBuilder(block)))
    fun addLink(item: Item) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofItem(item)).addFragment(KnowledgeArticleTextFragmentBuilder(item)))
    fun addLink(entity: EntityType<*>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(entity)).addFragment(KnowledgeArticleTextFragmentBuilder(entity)))
    fun addLink(enchantment: Enchantment) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEnchant(enchantment)).addFragment(KnowledgeArticleTextFragmentBuilder(enchantment)))
    fun addLink(effect: StatusEffect) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEffect(effect)).addFragment(KnowledgeArticleTextFragmentBuilder(effect)))
    fun addLink(biome: Biome) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBiome(biome)).addFragment(KnowledgeArticleTextFragmentBuilder(biome)))

    fun addPluralisedLink(block: Block) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBlock(block)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${block.translationKey}"))))
    fun addPluralisedLink(item: Item) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofItem(item)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${item.translationKey}"))))
    fun addPluralisedLink(entity: EntityType<*>) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEntity(entity)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${entity.translationKey}"))))
    fun addPluralisedLink(enchantment: Enchantment) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEnchant(enchantment)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${enchantment.translationKey}"))))
    fun addPluralisedLink(effect: StatusEffect) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofEffect(effect)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.${effect.translationKey}"))))
    fun addPluralisedLink(biome: Biome) : KnowledgeArticleParagraphFragmentBuilder = addFragment(KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBiome(biome)).addFragment(KnowledgeArticleTextFragmentBuilder(Text.translatable("knowledge.lcc.meta.plural.biome.${BuiltinRegistries.BIOME.getId(biome).toString().replace(":", ".")}"))))

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = list.indexOf(fragment).toString()

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + list.flatMap { it.exporterWalked(exporter) }

    override fun shouldInclude(exporter: KnowledgeExporter) = list.any { it.shouldInclude(exporter) }

    override fun afterInit() = list.forEach { it.afterInit() }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val fjson = JsonArray()
        list.forEach {
            fjson.add(it.toJsonFinal(exporter))
        }
        val json = JsonObject()
        json.add("fragments", fjson)
        return json
    }

}
