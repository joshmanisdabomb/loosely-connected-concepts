package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class PottedPlantBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { modelPottedCross(data, entry, texture ?: loc(entry)) }
    }

    companion object : PottedPlantBlockAssetFactory()

}