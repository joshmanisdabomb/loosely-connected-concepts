package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.block.Block
import net.minecraft.block.enums.WireConnection
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.When
import net.minecraft.util.math.Direction
import net.minecraft.block.RedstoneWireBlock.DIRECTION_TO_WIRE_CONNECTION_PROPERTY as directionProperties

open class WireBlockAssetFactory(val dot: ModelProvider.ModelFactory<Block>, val side: (alt: Boolean, texture: Int) -> ModelProvider.ModelFactory<Block>, val up: ModelProvider.ModelFactory<Block>, val alwaysDot: Boolean = false) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val up = up.create(data, entry) { idh.locSuffix(entry, "up") }

        stateMultipart(data, entry) {
            if (alwaysDot) with(BlockStateVariant.create().put(VariantSettings.MODEL, dot.create(data, entry)))
            else with(When.anyOf(When.create().apply { directionProperties.values.forEach { this.set(it, WireConnection.NONE) } }, *listOf(Direction.NORTH, Direction.SOUTH).flatMap { d1 -> listOf(Direction.EAST, Direction.WEST).map { d2 -> When.create().set(directionProperties[d1], WireConnection.SIDE, WireConnection.UP).set(directionProperties[d2], WireConnection.SIDE, WireConnection.UP) } }.toTypedArray()), BlockStateVariant.create().put(VariantSettings.MODEL, dot.create(data, entry)))

            Direction.Type.HORIZONTAL.forEach { d ->
                val x = d.axis == Direction.Axis.X
                val texture = x.transformInt()
                with(When.create().set(directionProperties[d], WireConnection.SIDE, WireConnection.UP), BlockStateVariant.create().put(VariantSettings.MODEL, side(d.isAlt, texture).create(data, entry) { idh.locSuffix(entry, "side").suffix(d.isAlt.transform("alt", null)).suffix(texture.toString(), "") }).put(VariantSettings.Y, x.transform(VariantSettings.Rotation.R270, VariantSettings.Rotation.R0)))
                with(When.create().set(directionProperties[d], WireConnection.UP), BlockStateVariant.create().put(VariantSettings.MODEL, up).apply(ModelProvider.horizontalRotation(d)))
            }
        }
    }

    private val Direction.isAlt get() = when (this) { Direction.SOUTH, Direction.EAST -> true else -> false }

    //companion object : WireBlockAssetFactory()

}