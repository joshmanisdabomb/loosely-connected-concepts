package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class PlantBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = models.cross { texture })
    }

    companion object : PlantBlockAssetFactory()

}