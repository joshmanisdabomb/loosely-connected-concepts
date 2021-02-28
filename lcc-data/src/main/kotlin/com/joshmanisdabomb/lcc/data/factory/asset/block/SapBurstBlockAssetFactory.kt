package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.SapBurstBlock.Companion.sap
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.util.Identifier

class SapBurstBlockAssetFactory(val texture: Identifier? = null, val textureSide: Identifier? = null, val textureEnd: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val t = texture ?: loc(entry)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(sap).register { sap ->
                BlockStateVariant.create().put(VariantSettings.MODEL, modelCubeBottomTop(data, entry, loc(entry) { if (sap == 7) it else it.plus("_$sap") }, texture = suffix(t, sap.toString()), textureSide = suffix(t, sap.toString()), textureTop = textureEnd ?: suffix(t, "top"), textureBottom = textureEnd ?: suffix(t, "top")))
            })
        }
    }

}
