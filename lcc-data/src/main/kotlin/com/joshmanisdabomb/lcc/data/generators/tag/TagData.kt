package com.joshmanisdabomb.lcc.data.generators.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.TagBatch
import com.mojang.serialization.JsonOps
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.tag.TagFile

class TagData(val batch: TagBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        batch.getBuilders().forEach { c ->
            val list = c.value.build()
            val jsonObject = TagFile.CODEC.encodeStart(JsonOps.INSTANCE, TagFile(list, false)).getOrThrow(false) { s -> da.logger.error(s) }
            val path = da.path.resolve("data/" + c.columnKey.namespace + "/tags/" + c.rowKey.asString() + "/" + c.columnKey.path + ".json")
            DataProvider.writeToPath(writer, jsonObject, path)
        }
    }

    override fun getName() = "${da.modid} Tag Data"



}