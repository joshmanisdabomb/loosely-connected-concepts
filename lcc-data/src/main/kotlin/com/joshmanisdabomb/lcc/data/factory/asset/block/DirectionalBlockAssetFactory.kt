package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class DirectionalBlockAssetFactory(val default: Direction = Direction.UP, val model: DirectionalBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier = { d, b -> modelOrientableVertical(d, b) }) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, { model(data, entry) }) { coordinate(BlockStateVariantMap.create(Properties.FACING).register {
            BlockStateVariant.create().apply(defaultDirections[default]!![it]!!)
        }) }
    }

    companion object : DirectionalBlockAssetFactory() {
        val defaultDirections: Map<Direction, Map<Direction, BlockStateVariant.() -> Unit>> = mapOf(
            Direction.UP to mapOf(
                Direction.UP to {},
                Direction.NORTH to { put(VariantSettings.X, VariantSettings.Rotation.R90) },
                Direction.EAST to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90) },
                Direction.SOUTH to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180) },
                Direction.WEST to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270) },
                Direction.DOWN to { put(VariantSettings.X, VariantSettings.Rotation.R180) }
            )
        )
    }

}