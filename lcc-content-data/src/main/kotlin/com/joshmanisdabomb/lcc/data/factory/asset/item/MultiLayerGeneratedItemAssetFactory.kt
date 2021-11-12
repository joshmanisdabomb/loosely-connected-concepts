package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class MultiLayerGeneratedItemAssetFactory(vararg val layers: (entry: Item) -> Identifier) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val texture = Texture()
        layers.forEachIndexed { k, v ->
            texture.put(keys[k], v(entry))
        }
        Companion.models[layers.size-1].upload(idh.loc(entry), texture, data.models)
    }

    companion object {
        val models = listOf(Models.GENERATED, LCCModelTemplates.generated1, LCCModelTemplates.generated2)
        val keys = listOf(TextureKey.LAYER0, LCCModelTextureKeys.layer1, LCCModelTextureKeys.layer2)
    }

}