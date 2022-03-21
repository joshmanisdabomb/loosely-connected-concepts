package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.data.client.TextureKey
import net.minecraft.data.client.TextureMap
import net.minecraft.item.Item

class ComputingBlockItemAssetFactory(val whiteLayer: Boolean) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        variations.forEach {
            LCCModelTemplates.template_computing.upload(idh.locSuffix(entry, it, folder = "block"), TextureMap()
                .put(TextureKey.TOP, idh.locSuffix(LCCItems.computer_casing, "top", folder = "block"))
                .put(TextureKey.SIDE, idh.locSuffix(LCCItems.computer_casing, it ?: "side", folder = "block"))
                .put(TextureKey.FRONT, idh.loc(entry, folder = "block"))
                .put(LCCModelTextureKeys.white, idh.locSuffix(whiteLayer.transform(entry, LCCItems.computer_casing), "white", folder = "block"))
            , data.models)
            LCCModelTemplates.template_computing_top.upload(idh.locSuffix(entry, "top", folder = "block").suffix(it), TextureMap()
                .put(TextureKey.TOP, idh.locSuffix(LCCItems.computer_casing, "top", folder = "block"))
                .put(TextureKey.SIDE, idh.locSuffix(LCCItems.computer_casing, it ?: "side", folder = "block"))
                .put(TextureKey.FRONT, idh.loc(entry, folder = "block"))
                .put(LCCModelTextureKeys.white, idh.locSuffix(whiteLayer.transform(entry, LCCItems.computer_casing), "white", folder = "block"))
            , data.models)
        }
        models.parent(idh.loc(entry, "block")).create(data, entry)
    }

    companion object {
        val variations = listOf(null, "up", "down", "both")
    }

}
