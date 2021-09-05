package com.joshmanisdabomb.lcc.data.generators.advancement

import com.google.common.collect.Sets
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.advancement.Advancement
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import net.minecraft.util.Identifier
import java.io.IOException

class AdvancementData(val da: DataAccessor) : DataProvider {

    val list = mutableMapOf<String, Advancement>()

    override fun run(cache: DataCache) {
        val path = da.dg.output
        val set: MutableSet<Identifier> = Sets.newHashSet()
        val consumer = { a: Advancement ->
            check(set.add(a.id)) { "Duplicate advancement " + a.id }
            val path1 = path.resolve("data/" + a.id.namespace + "/advancements/" + a.id.path + ".json")
            try {
                DataProvider.writeToPath(da.gson, cache, a.createTask().toJson(), path1)
            } catch (ioexception: IOException) {
                da.logger.error("Couldn't save advancement {}", path1, ioexception)
            }
        }
        list.forEach { (k, v) ->
            v.createTask().apply { findParent { if (it.namespace == "lcc") list.toList().firstOrNull { (k2, v2) -> it.path == k2 }?.second else null } }.build(consumer, Identifier(da.modid, k).toString())
        }
    }

    override fun getName() = "${da.modid} Advancements"

}