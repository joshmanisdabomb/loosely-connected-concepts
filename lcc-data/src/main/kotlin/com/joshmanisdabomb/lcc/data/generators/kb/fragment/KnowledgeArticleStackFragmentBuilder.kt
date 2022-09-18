package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import com.joshmanisdabomb.lcc.utils.ItemStackUtils
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.text.Text

class KnowledgeArticleStackFragmentBuilder(vararg stacks: ItemStack, val alter: (stack: JsonObject) -> Unit = {}) : KnowledgeArticleFragmentBuilder() {

    override val type = "stack"

    private val stacks: MutableList<ItemStack> = mutableListOf(*stacks)
    private val itemTags: MutableList<TagKey<Item>> = mutableListOf()
    private val blockTags: MutableList<TagKey<Block>> = mutableListOf()

    fun addStacks(vararg stacks: ItemStack): KnowledgeArticleStackFragmentBuilder {
        this.stacks += stacks
        return this
    }

    fun addItemTags(vararg tags: TagKey<Item>): KnowledgeArticleStackFragmentBuilder {
        this.itemTags += tags
        return this
    }

    fun addBlockTags(vararg tags: TagKey<Block>): KnowledgeArticleStackFragmentBuilder {
        this.blockTags += tags
        return this
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()

        val sjson = JsonArray()
        val stacks = (this.stacks + this.itemTags.flatMap { exporter.da.recipes.getItemsInTag(it.id).map(ItemConvertible::stack) } + this.blockTags.flatMap { exporter.da.recipes.getItemsInTag(it.id).map(ItemConvertible::stack) }).filter { !it.isEmpty }
        stacks.forEach {
            val stack = ItemStackUtils.toJson(it)
            stack.add("item", stack.remove("id"))
            stack.add("name", Text.Serializer.toJsonTree(it.name))
            stack.addProperty("link", KnowledgeArticleIdentifier.ofItemConvertible(it.item).toString())
            alter(stack)
            sjson.add(stack)
        }
        json.add("stacks", sjson)

        return json
    }

}