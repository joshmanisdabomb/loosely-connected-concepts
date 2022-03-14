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
        Registry.BLOCK.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            converts[it.value().translationKey] = lang[it.value().translationKey] ?: I18n.translate(it.value().translationKey)
        }
        Registry.ITEM.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            converts[it.value().translationKey] = lang[it.value().translationKey] ?: I18n.translate(it.value().translationKey)
        }
        Registry.ENTITY_TYPE.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            converts[it.value().translationKey] = lang[it.value().translationKey] ?: I18n.translate(it.value().translationKey)
        }
        Registry.ENCHANTMENT.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            converts[it.value().translationKey] = lang[it.value().translationKey] ?: I18n.translate(it.value().translationKey)
        }
        Registry.STATUS_EFFECT.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            converts[it.value().translationKey] = lang[it.value().translationKey] ?: I18n.translate(it.value().translationKey)
        }
        BuiltinRegistries.BIOME.indexedEntries.filter { it.key.get().value.namespace == "lcc" || it.key.get().value.namespace == "minecraft" }.forEach {
            val key = "biome.${it.key.get().value.toString().replace(":", ".")}"
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
