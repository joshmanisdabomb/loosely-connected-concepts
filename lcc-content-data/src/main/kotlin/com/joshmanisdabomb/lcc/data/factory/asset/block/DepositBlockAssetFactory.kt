package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.Texture

object DepositBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry, model = { d, t, i -> LCCModelTemplates.template_deposit.upload(i(t) ?: idh.loc(t), Texture.texture(idh.loc(t)), d.models) })
    }

}
