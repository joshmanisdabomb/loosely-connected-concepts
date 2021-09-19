package com.joshmanisdabomb.lcc.data.generators.sound

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class SoundProperties(val name: String, val entries: Array<SoundEntry>, val subtitle: String?) {

    constructor(category: String, modid: String, name: String, entries: Array<SoundEntry>, subtitle: String? = "subtitles.$modid.$category.$name") : this("$category.$modid.$name", entries, subtitle)

    data class SoundEntry(val name: String, val volume: Float = 1f, val pitch: Float = 1f, val weight: Int = 1, val stream: Boolean = false, val attenuation: Int = 16, val preload: Boolean = false) {

        fun asString() = volume == 1f && pitch == 1f && weight == 1 && !stream && attenuation == 16 && !preload

        fun serialise() = JsonObject().also {
            it.addProperty("name", name)
            if (volume != 1f) it.addProperty("volume", volume)
            if (pitch != 1f) it.addProperty("pitch", pitch)
            if (weight != 1) it.addProperty("weight", weight)
            if (stream) it.addProperty("stream", true)
            if (attenuation != 16) it.addProperty("attenuation_distance", attenuation)
            if (preload) it.addProperty("preload", true)
        }

    }

    fun serialise(json: JsonObject): JsonObject {
        json.add("sounds", JsonArray().apply {
            entries.forEach {
                if (it.asString()) this.add(it.name)
                else this.add(it.serialise())
            }
        })
        subtitle?.also { json.addProperty("subtitle", it) }
        return json
    }

}