package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class FenceBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val post = Models.FENCE_POST.upload(idh.loc(entry), Texture.texture(texture ?: idh.loc(entry)), data.models)
        val side = Models.FENCE_SIDE.upload(idh.locSuffix(entry, "side"), Texture.texture(texture ?: idh.loc(entry)), data.models)
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
            .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.UVLOCK, true))
            .with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
            .with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
            .with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
        }
    }

}
