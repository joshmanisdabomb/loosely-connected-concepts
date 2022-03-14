package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.CogBlock
import com.joshmanisdabomb.lcc.block.CogBlock.Companion.cog_states
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.util.math.Direction

object CogBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val inactive = LCCModelTemplates.template_cog.upload(idh.loc(entry), TextureMap().put(TextureKey.FRONT, idh.loc(entry)).put(TextureKey.BACK, idh.loc(entry)).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
        val cw = LCCModelTemplates.template_cog.upload(idh.locSuffix(entry, "cw"), TextureMap().put(TextureKey.FRONT, idh.locSuffix(entry, "cw")).put(TextureKey.BACK, idh.locSuffix(entry, "ccw")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
        val ccw = LCCModelTemplates.template_cog.upload(idh.locSuffix(entry, "ccw"), TextureMap().put(TextureKey.FRONT, idh.locSuffix(entry, "ccw")).put(TextureKey.BACK, idh.locSuffix(entry, "cw")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)

        stateMultipart(data, entry) {
            Direction.values().forEach {
                with(When.create().set(cog_states[it], CogBlock.CogState.INACTIVE), BlockStateVariant.create().put(VariantSettings.MODEL, inactive).apply(ModelProvider.directionalRotation(it)))
                with(When.create().set(cog_states[it], CogBlock.CogState.CW), BlockStateVariant.create().put(VariantSettings.MODEL, cw).apply(ModelProvider.directionalRotation(it)))
                with(When.create().set(cog_states[it], CogBlock.CogState.CCW), BlockStateVariant.create().put(VariantSettings.MODEL, ccw).apply(ModelProvider.directionalRotation(it)))
            }
        }
    }

}