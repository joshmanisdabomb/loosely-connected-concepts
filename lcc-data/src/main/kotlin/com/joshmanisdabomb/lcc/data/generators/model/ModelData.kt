package com.joshmanisdabomb.lcc.data.generators.model

import com.joshmanisdabomb.lcc.data.DataLauncher
import com.joshmanisdabomb.lcc.data.batches.ModelBatch
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import java.io.IOException

class ModelData(val batch: ModelBatch, val da: DataLauncher) : DataProvider {

    override fun run(writer: DataWriter) {
        batch.getJson().forEach { (k, v) ->
            val outputPath = da.path.resolve("assets/" + k.namespace + "/models/" + k.path + ".json")
            try {
                DataProvider.writeToPath(writer, v(), outputPath)
            } catch (e: IOException) {
                da.logger.error("Couldn't save block state {}", outputPath, e)
            }
        }
    }

    override fun getName() = "${da.modid} Model Data"

}