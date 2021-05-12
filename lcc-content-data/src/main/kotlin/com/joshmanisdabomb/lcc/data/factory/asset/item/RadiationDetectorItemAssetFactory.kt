package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import net.minecraft.data.client.model.Texture
import net.minecraft.item.Item

object RadiationDetectorItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val alts = (1..5).map { level -> idh.locSuffix(entry, "winter_$level") }
        data.modelStates.addItemModel(entry) { data.parser.parse("""{
    "parent": "lcc:item/template_radiation_detector",
    "textures": {
        "0": "${idh.loc(entry)}",
        "1": "${idh.locSuffix(entry, "winter_0")}"
    },
    "overrides": [
        { "predicate": { "lcc:winter": 1.0 }, "model": "${alts[0]}" },
        { "predicate": { "lcc:winter": 2.0 }, "model": "${alts[1]}" },
        { "predicate": { "lcc:winter": 3.0 }, "model": "${alts[2]}" },
        { "predicate": { "lcc:winter": 4.0 }, "model": "${alts[3]}" },
        { "predicate": { "lcc:winter": 5.0 }, "model": "${alts[4]}" }
    ]
}""".trimMargin()) }

        alts.forEach { LCCModelTemplates.template_radiation_detector.upload(it, Texture().put(LCCModelTextureKeys.t0, idh.loc(entry)).put(LCCModelTextureKeys.t1, it), data.modelStates::addModel) }
    }

}