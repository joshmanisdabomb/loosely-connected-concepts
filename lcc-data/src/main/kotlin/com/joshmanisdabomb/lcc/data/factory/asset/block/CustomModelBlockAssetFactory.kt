package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class CustomModelBlockAssetFactory(val id: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { id ?: id(assetPath(entry), data.modid) }
    }

    companion object : CustomModelBlockAssetFactory()

}