package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.CogBlock
import com.joshmanisdabomb.lcc.block.CogBlock.Companion.cog_states
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.math.Direction

object CogBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        val inactive = ModelTemplates.template_cog.upload(id, Texture().put(TextureKey.FRONT, id).put(TextureKey.BACK, id).put(TextureKey.PARTICLE, id), data.modelStates::addModel)
        val cw = ModelTemplates.template_cog.upload(suffix(id, "cw"), Texture().put(TextureKey.FRONT, suffix(id, "cw")).put(TextureKey.BACK, suffix(id, "ccw")).put(TextureKey.PARTICLE, id), data.modelStates::addModel)
        val ccw = ModelTemplates.template_cog.upload(suffix(id, "ccw"), Texture().put(TextureKey.FRONT, suffix(id, "ccw")).put(TextureKey.BACK, suffix(id, "cw")).put(TextureKey.PARTICLE, id), data.modelStates::addModel)

        stateMultipart(data, entry) {
            Direction.values().forEach {
                with(When.create().set(cog_states[it], CogBlock.CogState.INACTIVE), BlockStateVariant.create().put(VariantSettings.MODEL, inactive).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
                with(When.create().set(cog_states[it], CogBlock.CogState.CW), BlockStateVariant.create().put(VariantSettings.MODEL, cw).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
                with(When.create().set(cog_states[it], CogBlock.CogState.CCW), BlockStateVariant.create().put(VariantSettings.MODEL, ccw).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
            }
        }
    }

}