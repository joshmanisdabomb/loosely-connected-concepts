package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.identifierLoc
import net.minecraft.block.Block
import net.minecraft.block.FlowerPotBlock
import net.minecraft.util.Identifier

open class PottedPlantBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = models.pottedCross { texture ?: (entry as? FlowerPotBlock)?.content?.identifierLoc() })
    }

    companion object : PottedPlantBlockAssetFactory()

}