package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.NetherReactorBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings

object NetherReactorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(NetherReactorBlock.reactor_state).register {
                val id = idh.locSuffix(entry, it.asString())
                BlockStateVariant.create().put(VariantSettings.MODEL, models.cubeAll { id }.create(data, entry) { id })
            })
        }
    }

}
