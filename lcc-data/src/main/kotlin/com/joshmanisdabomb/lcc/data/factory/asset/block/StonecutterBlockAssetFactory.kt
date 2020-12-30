package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

object StonecutterBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = loc(entry)
        stateVariantModel(data, entry, { modelCube(data, entry,
            textureUp = suffix(texture, "top"),
            textureDown = Identifier("minecraft", "block/furnace_top"),
            textureNorth = suffix(texture, "side"),
            textureSouth = suffix(texture, "side"),
            textureEast = Identifier("minecraft", "block/furnace_side"),
            textureWest = Identifier("minecraft", "block/furnace_side"),
            textureParticle = suffix(texture, "side")
        ) }) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![it]!!)
        }) }
    }

}
