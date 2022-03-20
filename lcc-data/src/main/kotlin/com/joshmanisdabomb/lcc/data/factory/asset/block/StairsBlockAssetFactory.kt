package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.BlockHalf
import net.minecraft.block.enums.StairShape
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

open class StairsBlockAssetFactory(val texture: Identifier, val textureTop: Identifier? = texture, val textureSide: Identifier? = texture, val textureBottom: Identifier? = texture) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap().put(TextureKey.TOP, textureTop ?: idh.loc(entry)).put(TextureKey.SIDE, textureSide ?: idh.loc(entry)).put(TextureKey.BOTTOM, textureBottom ?: idh.loc(entry))
        val stairs = Models.STAIRS.upload(idh.loc(entry), texture, data.models)
        val innerStairs = Models.INNER_STAIRS.upload(idh.locSuffix(entry, "inner"), texture, data.models)
        val outerStairs = Models.OUTER_STAIRS.upload(idh.locSuffix(entry, "outer"), texture, data.models)
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
                .put(VariantSettings.X, if (h == BlockHalf.TOP) VariantSettings.Rotation.R180 else VariantSettings.Rotation.R0)
                .put(VariantSettings.Y, VariantSettings.Rotation.values()[y.div(90)])
                .put(VariantSettings.UVLOCK, y != 0 || h == BlockHalf.TOP)
        }) }
    }

}
