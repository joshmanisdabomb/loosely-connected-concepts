package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.TextureMap
import net.minecraft.state.property.Properties

object ClusterBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, { d, t, i -> LCCModelTemplates.aligned_cross.upload(i(t) ?: idh.loc(t), TextureMap.cross(idh.loc(t)), data.models) }) { coordinate(BlockStateVariantMap.create(Properties.FACING).register {
            BlockStateVariant.create().apply(ModelProvider.directionalRotation(it))
        }) }
    }

}
