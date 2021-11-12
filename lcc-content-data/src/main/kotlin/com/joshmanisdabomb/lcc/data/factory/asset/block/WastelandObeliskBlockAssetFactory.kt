package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.WastelandObeliskBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties

object WastelandObeliskBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(WastelandObeliskBlock.charge, Properties.BOTTOM).register { charge, bottom ->
                val texture = Texture().put(TextureKey.TEXTURE, idh.loc(LCCBlocks.cracked_mud)).put(TextureKey.LANTERN, idh.locSuffix(entry, charge.toString())).put(TextureKey.INSIDE, idh.loc(entry)).put(TextureKey.PARTICLE, idh.loc(LCCBlocks.cracked_mud))
                if (bottom) BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_obelisk_bottom.upload(idh.locSuffix(entry, "bottom").suffix(charge.toString()), texture, data.models))
                else BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_obelisk_top.upload(idh.locSuffix(entry, "top").suffix(charge.toString()), texture, data.models))
            })
        }
    }

}
