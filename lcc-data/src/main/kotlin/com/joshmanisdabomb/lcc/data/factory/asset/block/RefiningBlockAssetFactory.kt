package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.math.Direction

object RefiningBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (entry !is RefiningBlock) return
        val id = loc(entry)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(entry.processes, HORIZONTAL_FACING).register { p, d ->
                BlockStateVariant.create().put(VariantSettings.MODEL, modelOrientableBottom(data, entry, suffix(id, p.nullableName), texture = loc(LCCBlocks[LCCBlocks.machine_enclosure].id), textureFront = suffix(suffix(id, "front"), p.nullableName), textureTop = suffix(suffix(id, "top"), p.nullableName))).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![d]!!)
            })
        }
    }

}
