package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.Models
import net.minecraft.data.client.TextureMap
import net.minecraft.util.Identifier

open class TintBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = { d, t, i -> Models.LEAVES.upload(idh.loc(entry), TextureMap.all(texture ?: idh.loc(entry)), data.models) })
    }

    companion object : TintBlockAssetFactory()

}