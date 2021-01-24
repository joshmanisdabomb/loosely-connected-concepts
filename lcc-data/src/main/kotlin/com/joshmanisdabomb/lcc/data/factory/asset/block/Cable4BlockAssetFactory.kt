package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.CableBlock.Companion.cable_states
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.math.Direction

object Cable4BlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        val center = ModelTemplates.cable4_center.upload(id, Texture().put(TextureKey.END, suffix(id, "end")).put(TextureKey.PARTICLE, id), data.modelStates::addModel)
        val connection = ModelTemplates.cable4_connection.upload(suffix(id, "connection"), Texture().put(TextureKey.SIDE, id).put(TextureKey.END, suffix(id, "end")).put(TextureKey.PARTICLE, id), data.modelStates::addModel)

        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, center))
            Direction.values().forEach {
                with(When.create().set(cable_states[it], true), BlockStateVariant().put(VariantSettings.MODEL, connection).put(VariantSettings.UVLOCK, true).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
            }
        }
    }

}
