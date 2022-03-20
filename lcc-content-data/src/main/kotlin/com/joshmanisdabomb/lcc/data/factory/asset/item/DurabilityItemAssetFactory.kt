package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.item.Item

class DurabilityItemAssetFactory(val model: (amount: Int) -> ModelProvider.ModelFactory<Item>, val stages: DoubleArray) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val ids = stages.mapIndexed { k, v -> v to model(k+1).create(data, entry) { idh.locSuffix(it, if (k != 0) (k+1).toString() else null) } }

        val first = ids.first().second
        val json = data.models[first]?.invoke() as? JsonObject
        if (json != null) {
            val overrides = JsonArray()
            ids.drop(1).forEach { (k, v) ->
                val override = JsonObject()

                val predicate = JsonObject()
                predicate.addProperty("minecraft:damage", k)
                predicate.addProperty("minecraft:damaged", 1)
                override.add("predicate", predicate)
                override.addProperty("model", v.toString())

                overrides.add(override)
            }
            json.add("overrides", overrides)
            data.models[first] = { json }
        }
    }

}
