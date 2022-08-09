package com.joshmanisdabomb.lcc.data.generators.lang

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter

class LangData(val batch: LangBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        batch.getTranslationsByLocale().forEach { (l, t) ->
            val json = da.gson.toJsonTree(t)
            val path = da.path.resolve("assets/${da.modid}/lang/$l.json")
            DataProvider.writeToPath(writer, json, path)
        }
    }

    override fun getName() = "${da.modid} Language Data"

}