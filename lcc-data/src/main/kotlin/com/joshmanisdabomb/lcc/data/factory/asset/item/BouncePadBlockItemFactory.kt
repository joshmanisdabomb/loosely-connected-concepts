package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import com.joshmanisdabomb.lcc.data.directory.ModelTextureKeys
import net.minecraft.data.client.model.Texture
import net.minecraft.item.Item

object BouncePadBlockItemFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val id = loc(entry)
        val textures = Texture().put(ModelTextureKeys.t0, suffix(id, "base_h")).put(ModelTextureKeys.t1, suffix(id, "inner")).put(ModelTextureKeys.t2, suffix(id, "setting")).put(ModelTextureKeys.t3, suffix(id, "base_v")).put(ModelTextureKeys.t4, suffix(id, "pad")).put(ModelTextureKeys.t5, suffix(id, "pad_side"))
        ModelTemplates.template_bounce_pad_item.upload(id, textures, data.modelStates::addModel)
    }

}
