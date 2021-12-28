package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BooleanCableBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.math.Direction

object BooleanCable4BlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val cable = entry as? BooleanCableBlock ?: return
        val center = LCCModelTemplates.cable4_center.upload(idh.loc(entry), Texture().put(TextureKey.END, idh.locSuffix(entry, "end")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
        val connection = LCCModelTemplates.cable4_connection.upload(idh.locSuffix(entry, "connection"), Texture().put(TextureKey.SIDE, idh.loc(entry)).put(TextureKey.END, idh.locSuffix(entry, "end")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)

        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, center))
            Direction.values().forEach {
                with(When.create().set(cable.properties[it], true), BlockStateVariant().put(VariantSettings.MODEL, connection).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(it)))
            }
        }
    }

}
