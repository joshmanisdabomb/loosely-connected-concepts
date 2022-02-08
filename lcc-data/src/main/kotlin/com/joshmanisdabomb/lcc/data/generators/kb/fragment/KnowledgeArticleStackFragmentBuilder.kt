package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.utils.ItemStackUtils
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

//TODO
class KnowledgeArticleStackFragmentBuilder(vararg stacks: ItemStack, val alter: (stack: JsonObject) -> Unit = {}) : KnowledgeArticleFragmentBuilder() {

    override val type = "stack"
    val stacks = stacks.filter { !it.isEmpty }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()

        val stacks = JsonArray()
        this.stacks.forEach {
            val stack = ItemStackUtils.toJson(it)
            stack.add("item", stack.remove("id"))
            stack.add("name", Text.Serializer.toJsonTree(it.name))
            alter(stack)
            stacks.add(stack)
        }
        json.add("stacks", stacks)

        return json
    }

}