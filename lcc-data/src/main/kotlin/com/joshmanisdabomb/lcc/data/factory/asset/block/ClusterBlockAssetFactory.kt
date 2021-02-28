package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object ClusterBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        stateVariantModel(data, entry, { LCCModelTemplates.aligned_cross.upload(id, Texture.cross(id), data.modelStates::addModel) }) { coordinate(BlockStateVariantMap.create(Properties.FACING).register {
            BlockStateVariant.create().apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!)
        }) }
    }

}
