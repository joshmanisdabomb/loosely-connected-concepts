package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.FlowerPotBlock
import net.minecraft.util.Identifier

open class PottedPlantBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { modelPottedCross(data, entry, texture = texture ?: loc((entry as FlowerPotBlock).content)) }
    }

    companion object : PottedPlantBlockAssetFactory()

}