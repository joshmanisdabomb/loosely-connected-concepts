package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class SpecialBlockAssetFactory(val id: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = { d, t, i -> id ?: idh.loc(entry) })
    }

    companion object : SpecialBlockAssetFactory()

}