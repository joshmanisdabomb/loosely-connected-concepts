package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.block.enums.BlockHalf
import net.minecraft.block.enums.StairShape
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

open class StairsBlockAssetFactory(val texture: Identifier, val textureTop: Identifier? = texture, val textureSide: Identifier? = texture, val textureBottom: Identifier? = texture) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture().put(TextureKey.TOP, textureTop ?: loc(entry)).put(TextureKey.SIDE, textureSide ?: loc(entry)).put(TextureKey.BOTTOM, textureBottom ?: loc(entry))
        val stairs = Models.STAIRS.upload(loc(entry), texture, data.modelStates::addModel)
        val innerStairs = Models.INNER_STAIRS.upload(loc(entry) { it.plus("_inner") }, texture, data.modelStates::addModel)
        val outerStairs = Models.OUTER_STAIRS.upload(loc(entry) { it.plus("_outer") }, texture, data.modelStates::addModel)
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.BLOCK_HALF, Properties.STAIR_SHAPE).register { f, h, s ->
            var dir = f.rotateYClockwise()
            if (s == StairShape.INNER_LEFT || s == StairShape.OUTER_LEFT) dir = dir.rotateYCounterclockwise()
            if (s != StairShape.STRAIGHT && h == BlockHalf.TOP) dir = dir.rotateYClockwise()
            val y = dir.asRotation().toInt()
            BlockStateVariant.create()
                .put(VariantSettings.MODEL, when (s) {
                    StairShape.INNER_LEFT, StairShape.INNER_RIGHT -> innerStairs
                    StairShape.OUTER_LEFT, StairShape.OUTER_RIGHT -> outerStairs
                    else -> stairs
                })
                .put(VariantSettings.X, (h == BlockHalf.TOP).transform(VariantSettings.Rotation.R180, VariantSettings.Rotation.R0))
                .put(VariantSettings.Y, VariantSettings.Rotation.values()[y.div(90)])
                .put(VariantSettings.UVLOCK, y != 0 || h == BlockHalf.TOP)
        }) }
    }

}
