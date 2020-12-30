package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class HorizontalBlockAssetFactory(val model: HorizontalBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier = { d, b -> modelOrientable(d, b) }, val default: Direction = Direction.NORTH) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, { model(data, entry) }) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(defaultDirections[default]!![it]!!)
        }) }
    }

    companion object : HorizontalBlockAssetFactory() {
        val defaultDirections: Map<Direction, Map<Direction, BlockStateVariant.() -> Unit>> = mapOf(
            Direction.NORTH to mapOf(
                Direction.NORTH to {},
                Direction.EAST to { put(VariantSettings.Y, VariantSettings.Rotation.R90) },
                Direction.SOUTH to { put(VariantSettings.Y, VariantSettings.Rotation.R180) },
                Direction.WEST to { put(VariantSettings.Y, VariantSettings.Rotation.R270) },
            )
        )
    }

}