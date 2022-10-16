package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.data.client.*

object HeartCondenserBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val base = LCCModelTemplates.template_heart_condenser.upload(idh.loc(entry), TextureMap().put(TextureKey.PARTICLE, idh.loc(LCCBlocks.polished_sapphire_altar_brick)).put(LCCModelTextureKeys.t0, idh.loc(LCCBlocks.polished_sapphire_altar_brick)).put(LCCModelTextureKeys.t1, idh.locSuffix(LCCBlocks.sapphire_altar, "gems")), data.models)
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, base))
            HeartCondenserBlock.HeartState.values().filter { it != HeartCondenserBlock.HeartState.NONE }.forEach {
                val texture = idh.locSuffix(LCCBlocks.heart_condenser, it.asString())
                val fill = LCCModelTemplates.template_heart_condenser_fill.upload(idh.locSuffix(entry, it.asString()), TextureMap().put(LCCModelTextureKeys.t0, texture).put(TextureKey.PARTICLE, texture), data.models)
                with(When.create().set(HeartCondenserBlock.type, it), BlockStateVariant().put(VariantSettings.MODEL, fill).put(VariantSettings.UVLOCK, true))
            }
        }
    }

}
