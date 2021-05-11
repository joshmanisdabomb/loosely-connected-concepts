package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import net.minecraft.data.client.model.Texture
import net.minecraft.item.Item

object BouncePadItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val textures = Texture().put(LCCModelTextureKeys.t0, idh.locSuffix(entry, "base_h", folder = "block")).put(LCCModelTextureKeys.t1, idh.locSuffix(entry, "inner", folder = "block")).put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "setting", folder = "block")).put(LCCModelTextureKeys.t3, idh.locSuffix(entry, "base_v", folder = "block")).put(LCCModelTextureKeys.t4, idh.locSuffix(entry, "pad", folder = "block")).put(LCCModelTextureKeys.t5, idh.locSuffix(entry, "pad_side", folder = "block"))
        LCCModelTemplates.template_bounce_pad_item.upload(idh.loc(entry), textures, data.modelStates::addModel)
    }

}