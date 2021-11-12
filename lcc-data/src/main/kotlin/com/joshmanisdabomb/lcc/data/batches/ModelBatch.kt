package com.joshmanisdabomb.lcc.data.batches

import com.google.gson.JsonElement
import net.minecraft.util.Identifier
import java.util.function.BiConsumer
import java.util.function.Supplier

class ModelBatch : BiConsumer<Identifier, Supplier<JsonElement>> {

    val map = mutableMapOf<Identifier, () -> JsonElement>()

    operator fun set(key: Identifier, json: () -> JsonElement) = map.put(key, json)

    override fun accept(key: Identifier, json: Supplier<JsonElement>) {
        set(key, json::get)
    }

    fun getJson() = map.toMap()

}
