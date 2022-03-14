package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings

object RoadBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(RoadBlock.SHAPE, RoadBlock.MARKINGS, RoadBlock.INNER).register { shape, markings, inner ->
            BlockStateVariant.create().put(VariantSettings.MODEL, idh.locSuffix(entry, "${shape.asString()}${if (inner) "_inner" else ""}${markings.suffix()}"))
        }) }
    }

}
