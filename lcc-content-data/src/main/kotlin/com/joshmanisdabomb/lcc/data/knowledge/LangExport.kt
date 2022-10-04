package com.joshmanisdabomb.lcc.data.knowledge

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.WaitingDataProvider
import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.client.MinecraftClient
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.util.Identifier
import java.io.File
import java.io.InputStreamReader

class LangExport(val folder: File, val resolver: (root: File, namespace: String, locale: String) -> File = { r, n, l -> r.resolve("$n/$l.json") }) : WaitingDataProvider {

    private val sources = mutableMapOf<String, MutableMap<String, () -> JsonObject>>()

    override var done = false

    fun addSource(namespace: String, locale: String, translationProvider: () -> JsonObject): LangExport {
        sources.getOrPut(namespace) { mutableMapOf() }.put(locale, translationProvider)
        return this
    }

    fun addFromResources(namespace: String, locale: String): LangExport {
        return addSource(namespace, locale) {
            val write = JsonObject()
            val resources = MinecraftClient.getInstance().resourceManager.getAllResources(Identifier(namespace, "lang/${locale}.json"))
            resources.forEach { r ->
                val read = JsonParser.parseReader(InputStreamReader(r.inputStream)).asJsonObject
                read.keySet().forEach {
                    write.addProperty(it, read.getAsJsonPrimitive(it).asString)
                }
            }
            write
        }
    }

    fun addFromLangBatch(namespace: String, locale: String, batch: LangBatch): LangExport {
        return addSource(namespace, locale) {
            val write = JsonObject()
            batch.getTranslationsIn(locale).forEach { (k, v) ->
                write.addProperty(k, v)
            }
            write
        }
    }

    override fun run(writer: DataWriter) {
        sources.forEach { namespace, v ->
            v.forEach { locale, provider ->
                val loc = resolver(folder, namespace, locale).toPath()
                val json = LCCData.gson.toJsonTree(provider())
                DataProvider.writeToPath(writer, json, loc)
            }
        }
        done = true
    }

    override fun getName() = "LCC Wiki Language Export"

}