package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item

object QuiverItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val filled = (1..3).map { level -> idh.locSuffix(entry, "filled_$level") }
        data.modelStates.addItemModel(entry) { data.parser.parse("""{
    "parent": "item/generated",
    "textures": {
        "layer0": "${idh.loc(entry)}"
    },
    "overrides": [
        { "predicate": { "lcc:filled": 0.0000001 }, "model": "${filled[0]}" },
        { "predicate": { "lcc:filled": 0.3333334 }, "model": "${filled[1]}" },
        { "predicate": { "lcc:filled": 0.6666668 }, "model": "${filled[2]}" }
    ]
}""".trimMargin()) }

        filled.forEach { i -> models.generated { i }.create(data, entry) { i } }
    }

}