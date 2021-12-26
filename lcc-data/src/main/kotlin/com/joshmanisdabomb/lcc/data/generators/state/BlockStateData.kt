package com.joshmanisdabomb.lcc.data.generators.state

import com.joshmanisdabomb.lcc.data.DataLauncher
import com.joshmanisdabomb.lcc.data.batches.BlockStateBatch
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import java.io.IOException

class BlockStateData(val batch: BlockStateBatch, val da: DataLauncher) : DataProvider {

    override fun run(cache: DataCache) {
        batch.getJson().forEach { (k, v) ->
            val outputPath = da.path.resolve("assets/" + k.namespace + "/blockstates/" + k.path + ".json")
            try {
                DataProvider.writeToPath(da.gson, cache, v(), outputPath)
            } catch (e: IOException) {
                da.logger.error("Couldn't save block state {}", outputPath, e)
            }
        }
    }

    override fun getName() = "${da.modid} Block State Data"

}