package com.joshmanisdabomb.lcc.data.generators.advancement

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.AdvancementBatch
import net.minecraft.advancement.Advancement
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import java.io.IOException

class AdvancementData(val batch: AdvancementBatch, val da: DataAccessor) : DataProvider {

    override fun run(writer: DataWriter) {
        val consumer = fun(a: Advancement) {
            val path = da.path.resolve("data/" + a.id.namespace + "/advancements/" + a.id.path + ".json")
            try {
                DataProvider.writeToPath(writer, a.createTask().toJson(), path)
            } catch (e: IOException) {
                da.logger.error("Couldn't save advancement {}", path, e)
            }
        }
        batch.getAdvancements().forEach { (k, v) ->
            v.createTask().apply { findParent { if (it.namespace == k.namespace) batch.getAdvancement(it.path) else null } }.build(consumer, k.toString())
        }
    }

    override fun getName() = "${da.modid} Advancements"

}