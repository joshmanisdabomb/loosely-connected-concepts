package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

object ClassicGrassBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.SNOWY).register { snowy ->
                val id = loc(entry) { suffix(it, if (snowy) "snowy" else null) }
                BlockStateVariant.create().put(VariantSettings.MODEL, modelCubeBottomTop(data, entry, id, textureSide = if (snowy) id else loc(entry) { suffix(it, "side") }, textureBottom = Identifier("minecraft", "block/dirt")))
            })
        }
    }

}
