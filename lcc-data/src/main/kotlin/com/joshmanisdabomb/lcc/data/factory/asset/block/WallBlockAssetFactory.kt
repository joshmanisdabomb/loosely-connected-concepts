package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.WallShape
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class WallBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap().put(TextureKey.WALL, texture ?: idh.loc(entry))
        val post = Models.TEMPLATE_WALL_POST.upload(idh.loc(entry), texture, data.models)
        val low = Models.TEMPLATE_WALL_SIDE.upload(idh.locSuffix(entry, "side"), texture, data.models)
        val tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(idh.locSuffix(entry, "side_tall"), texture, data.models)
        stateMultipart(data, entry) {
            with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, post))
            .with(When.create().set(Properties.NORTH_WALL_SHAPE, WallShape.LOW), BlockStateVariant.create().put(VariantSettings.MODEL, low).put(VariantSettings.UVLOCK, true))
            .with(When.create().set(Properties.EAST_WALL_SHAPE, WallShape.LOW), BlockStateVariant.create().put(VariantSettings.MODEL, low).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
            .with(When.create().set(Properties.SOUTH_WALL_SHAPE, WallShape.LOW), BlockStateVariant.create().put(VariantSettings.MODEL, low).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
            .with(When.create().set(Properties.WEST_WALL_SHAPE, WallShape.LOW), BlockStateVariant.create().put(VariantSettings.MODEL, low).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
            .with(When.create().set(Properties.NORTH_WALL_SHAPE, WallShape.TALL), BlockStateVariant.create().put(VariantSettings.MODEL, tall).put(VariantSettings.UVLOCK, true))
            .with(When.create().set(Properties.EAST_WALL_SHAPE, WallShape.TALL), BlockStateVariant.create().put(VariantSettings.MODEL, tall).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
            .with(When.create().set(Properties.SOUTH_WALL_SHAPE, WallShape.TALL), BlockStateVariant.create().put(VariantSettings.MODEL, tall).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
            .with(When.create().set(Properties.WEST_WALL_SHAPE, WallShape.TALL), BlockStateVariant.create().put(VariantSettings.MODEL, tall).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
        }
    }

}
