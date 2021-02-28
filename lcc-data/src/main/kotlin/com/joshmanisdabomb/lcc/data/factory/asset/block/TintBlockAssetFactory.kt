package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture
import net.minecraft.util.Identifier

open class TintBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { Models.LEAVES.upload(loc(entry), Texture.all(texture ?: loc(entry)), data.modelStates::addModel) }
    }

    companion object : TintBlockAssetFactory()

}