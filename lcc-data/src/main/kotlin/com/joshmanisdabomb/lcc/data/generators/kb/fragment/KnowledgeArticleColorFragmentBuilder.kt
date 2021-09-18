package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter

class KnowledgeArticleColorFragmentBuilder(val intColor: Int) : KnowledgeArticleFragmentBuilder() {

    override val type = "color"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        json.addProperty("int", intColor)
        json.addProperty("hex", "#" + intColor.toString(16).padStart(6, '0'))
        json.addProperty("red", intColor and 16711680 shr 16)
        json.addProperty("green", intColor and '\uff00'.toInt() shr 8)
        json.addProperty("blue", intColor and 255)
        return json
    }

}