package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.item.Item

object Cable4ItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val id = loc(entry, folder = "block")
        ModelTemplates.template_cable4_item.upload(loc(entry), Texture().put(TextureKey.SIDE, id).put(TextureKey.END, suffix(id, "end")), data.modelStates::addModel)
    }

}
