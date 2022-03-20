package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.data.client.TextureMap
import net.minecraft.data.client.TextureKey
import net.minecraft.item.Item

object Cable4ItemAssetFactory : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        LCCModelTemplates.cable4_item.upload(idh.loc(entry), TextureMap().put(TextureKey.SIDE, idh.loc(entry, folder = "block")).put(TextureKey.END, idh.locSuffix(entry, "end", folder = "block")), data.models)
    }

}