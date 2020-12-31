package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import net.minecraft.data.client.model.Texture
import net.minecraft.item.Item

object BouncePadItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val id = loc(entry)
        val textures = Texture().put(LCCModelTextureKeys.t0, suffix(id, "base_h")).put(LCCModelTextureKeys.t1, suffix(id, "inner")).put(LCCModelTextureKeys.t2, suffix(id, "setting")).put(LCCModelTextureKeys.t3, suffix(id, "base_v")).put(LCCModelTextureKeys.t4, suffix(id, "pad")).put(LCCModelTextureKeys.t5, suffix(id, "pad_side"))
        ModelTemplates.template_bounce_pad_item.upload(id, textures, data.modelStates::addModel)
    }

}