package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class ColumnBlockAssetFactory(val texture: Identifier? = null, val textureSide: Identifier? = null, val textureEnd: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val column = models.pillar({ texture }, { textureSide }, { textureEnd }).create(data, entry)
        val columnHorizontal = models.pillarHorizontal({ texture }, { textureSide }, { textureEnd }).create(data, entry)
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.AXIS)
            .register(Direction.Axis.Y, BlockStateVariant.create().put(VariantSettings.MODEL, column))
            .register(Direction.Axis.Z, BlockStateVariant.create().put(VariantSettings.MODEL, columnHorizontal).put(VariantSettings.X, VariantSettings.Rotation.R90))
            .register(Direction.Axis.X, BlockStateVariant.create().put(VariantSettings.MODEL, columnHorizontal).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        ) }
    }

    companion object : ColumnBlockAssetFactory()

}
