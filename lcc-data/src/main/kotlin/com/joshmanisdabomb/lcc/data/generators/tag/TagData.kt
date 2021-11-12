package com.joshmanisdabomb.lcc.data.generators.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.TagBatch
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import java.io.IOException
import java.nio.file.Files
import java.util.*

class TagData(val batch: TagBatch, val da: DataAccessor) : DataProvider {

    override fun run(cache: DataCache) {
        batch.getBuilders().forEach { c ->
            val jsonObject = c.value.toJson()
            val path = da.path.resolve("data/" + c.columnKey.namespace + "/tags/" + c.rowKey.asString() + "/" + c.columnKey.path + ".json");
            try {
                val string = da.gson.toJson(jsonObject)
                val string2 = DataProvider.SHA1.hashUnencodedChars(string).toString()
                if (!Objects.equals(cache.getOldSha1(path), string2) || !Files.exists(path)) {
                    Files.createDirectories(path.parent)
                    Files.newBufferedWriter(path).use { it.write(string) }
                }
                cache.updateSha1(path, string2)
            } catch (e: IOException) {
                da.logger.error("Couldn't save tags {}", path, e)
            }
        }
    }

    override fun getName() = "${da.modid} Tag Data"



}