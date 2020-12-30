package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings

object RoadBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(RoadBlock.SHAPE, RoadBlock.MARKINGS, RoadBlock.INNER).register { shape, markings, inner ->
            BlockStateVariant.create().put(VariantSettings.MODEL, id(assetPath(entry) + "_${shape.asString()}${if (inner) "_inner" else ""}${markings.suffix()}", data.modid))
        }) }
    }

}
