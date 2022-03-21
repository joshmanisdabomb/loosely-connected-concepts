package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.item.ComputingItem
import net.minecraft.item.Item
import kotlin.math.log2

class ComputingLog2ItemAssetFactory(val levels: IntArray, val model: (key: Int) -> ModelProvider.ModelFactory<Item>) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val item = entry as? ComputingItem ?: return

        val ids = levels.mapIndexed { k, v -> log2(v.toFloat().div(item.initialLevel)).div(log2(item.maxLevel.toFloat().div(item.initialLevel))) to model(k+1).create(data, entry) { idh.locSuffix(it, if (k != 0) (k+1).toString() else null) } }

        val first = ids.first().second
        val json = data.models[first]?.invoke() as? JsonObject
        if (json != null) {
            val overrides = JsonArray()
            ids.drop(1).forEach { (k, v) ->
                val override = JsonObject()

                val predicate = JsonObject()
                predicate.addProperty("lcc:computing", k.toDouble())
                override.add("predicate", predicate)
                override.addProperty("model", v.toString())

                overrides.add(override)
            }
            json.add("overrides", overrides)
            data.models[first] = { json }
        }
    }

}
