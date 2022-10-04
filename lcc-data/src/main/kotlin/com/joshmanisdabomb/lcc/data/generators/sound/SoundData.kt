package com.joshmanisdabomb.lcc.data.generators.sound

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.SoundBatch
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter

class SoundData(val batch: SoundBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        val json = da.gson.toJsonTree(batch.getSounds().mapValues { (k, v) -> v.serialise(JsonObject()) }.toMap())
        val path = da.path.resolve("assets/${da.modid}/sounds.json")
        DataProvider.writeToPath(writer, json, path)
    }

    override fun getName() = "${da.modid} Sound Data"

}