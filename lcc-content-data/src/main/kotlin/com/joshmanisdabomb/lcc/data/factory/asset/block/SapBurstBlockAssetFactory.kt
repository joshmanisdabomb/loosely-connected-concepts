package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.SapBurstBlock.Companion.sap
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.util.Identifier

class SapBurstBlockAssetFactory(val texture: Identifier? = null, val textureSide: Identifier? = null, val textureEnd: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val t = texture ?: idh.loc(entry)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(sap).register { sap ->
                BlockStateVariant.create().put(VariantSettings.MODEL, models.cubeBottomTop(texture = { t.suffix(sap.toString()) }, textureSide = { t.suffix(sap.toString()) }, textureTop = { textureEnd ?: t.suffix("top") }, textureBottom = { textureEnd ?: t.suffix("top") }).create(data, entry) { idh.locSuffix(entry, if (sap == 7) null else sap.toString()) })
            })
        }
    }

}
