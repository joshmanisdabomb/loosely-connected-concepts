package com.joshmanisdabomb.lcc.data.knowledge

import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.client.resource.language.I18n
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import java.io.InputStreamReader


object KnowledgePluralisations {

    val converts = mutableMapOf<String, String>()

    fun injectTranslations(lang: LangBatch) {
        Registry.BLOCK.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            converts[v.translationKey] = lang[v.translationKey] ?: I18n.translate(v.translationKey)
        }
        Registry.ITEM.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            converts[v.translationKey] = lang[v.translationKey] ?: I18n.translate(v.translationKey)
        }
        Registry.ENTITY_TYPE.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            converts[v.translationKey] = lang[v.translationKey] ?: I18n.translate(v.translationKey)
        }
        Registry.ENCHANTMENT.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            converts[v.translationKey] = lang[v.translationKey] ?: I18n.translate(v.translationKey)
        }
        Registry.STATUS_EFFECT.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            converts[v.translationKey] = lang[v.translationKey] ?: I18n.translate(v.translationKey)
        }
        BuiltinRegistries.BIOME.entries.filter { (k, v) -> k.value.namespace == "lcc" || k.value.namespace == "minecraft" }.forEach { (k, v) ->
            val key = "biome.${k.value.toString().replace(":", ".")}"
            converts[key] = lang[key] ?: I18n.translate(key)
        }

        val post = HttpPost("https://lcc.jidb.net/api/pluralise")
        post.addHeader("Content-Type", "application/json")
        post.addHeader("Accept", "application/json")
        post.entity = EntityBuilder.create()
            .setText(LCCData.gson.toJson(converts))
            .build()

        val client = HttpClientBuilder.create().build()
        val response = client.execute(post)
        if (response.statusLine.statusCode == 200) {
            val read = JsonParser.parseReader(InputStreamReader(response.entity.content)).asJsonObject
            read.entrySet().forEach {
                lang["knowledge.lcc.meta.plural." + it.key] = it.value.asString
            }
        } else {
            println("Failed reading pluralisation API: " + response.statusLine.reasonPhrase)
        }
    }

}
