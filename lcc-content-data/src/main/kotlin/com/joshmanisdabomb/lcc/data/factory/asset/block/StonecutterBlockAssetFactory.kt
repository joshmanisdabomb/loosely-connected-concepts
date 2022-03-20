package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

object StonecutterBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val model = models.cube(
            textureUp = { idh.locSuffix(entry, "top") },
            textureDown = { Identifier("minecraft", "block/furnace_top") },
            textureNorth = { idh.locSuffix(entry, "side") },
            textureSouth = { idh.locSuffix(entry, "side") },
            textureEast = { Identifier("minecraft", "block/furnace_side") },
            textureWest = { Identifier("minecraft", "block/furnace_side") },
            textureParticle = { idh.locSuffix(entry, "side") }
        ).create(data, entry)
        stateVariantModel(data, entry, { d, t, i -> model }) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(ModelProvider.horizontalRotation(it))
        }) }
    }

}
