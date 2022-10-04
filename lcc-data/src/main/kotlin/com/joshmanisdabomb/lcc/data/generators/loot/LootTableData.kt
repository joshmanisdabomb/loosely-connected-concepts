package com.joshmanisdabomb.lcc.data.generators.loot

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LootTableBatch
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.loot.LootManager
import java.io.IOException

class LootTableData(val batch: LootTableBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        batch.getTables().forEach { (k, v) ->
            val outputPath = da.path.resolve("data/" + k.namespace + "/loot_tables/" + k.path + ".json")
            try {
                DataProvider.writeToPath(writer, LootManager.toJson(v), outputPath)
            } catch (e: IOException) {
                da.logger.error("Couldn't save loot table {}", outputPath, e)
            }
        }
    }

    override fun getName() = "${da.modid} Loot Table Data"

}