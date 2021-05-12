package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object ClusterBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, { d, t, i -> LCCModelTemplates.aligned_cross.upload(i(t) ?: idh.loc(t), Texture.cross(idh.loc(t)), data.modelStates::addModel) }) { coordinate(BlockStateVariantMap.create(Properties.FACING).register {
            BlockStateVariant.create().apply(ModelProvider.directionalRotation(it))
        }) }
    }

}
