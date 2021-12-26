package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.block.RefiningBlock.RefiningProcess.Companion.all
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.data.client.model.Texture
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class VisualStackItemAssetFactory(val model: (amount: Int) -> ModelProvider.ModelFactory<Item>, val maxStack: Int, val minStack: Int = 1) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val ids = (minStack..maxStack).associateWith(model).mapValues { (k, v) ->
            v.create(data, entry) { idh.locSuffix(it, if (k != minStack) k.toString() else null) }
        }

        val first = ids.values.first()
        val json = data.models[first]?.invoke() as? JsonObject
        if (json != null) {
            val overrides = JsonArray()
            (minStack+1..maxStack).forEach {
                val override = JsonObject()

                val predicate = JsonObject()
                predicate.addProperty("lcc:visual_stack", (it - minStack).toDouble().div(maxStack - minStack))
                override.add("predicate", predicate)
                override.addProperty("model", ids[it].toString())

                overrides.add(override)
            }
            json.add("overrides", overrides)
            data.models[first] = { json }
        }
    }

}
