package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class SideBottomTopBlockAssetFactory(val texture: Identifier? = null, val textureTop: Identifier? = null, val textureSide: Identifier? = null, val textureBottom: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = models.cubeBottomTop({ texture }, { textureTop }, { textureSide }, { textureBottom }))
    }

    companion object : SideBottomTopBlockAssetFactory()

}
