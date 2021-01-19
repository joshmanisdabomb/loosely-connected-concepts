package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

class CustomBlockAssetFactory(val model: CustomBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { model(data, entry) }
    }

}