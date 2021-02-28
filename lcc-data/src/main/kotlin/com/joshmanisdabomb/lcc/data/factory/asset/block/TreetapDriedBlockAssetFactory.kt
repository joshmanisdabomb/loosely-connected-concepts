package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.AbstractTreetapBlock
import com.joshmanisdabomb.lcc.block.DriedTreetapBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.When
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object TreetapDriedBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateMultipart(data, entry) {
            horizontalDirections.forEach { f ->
                AbstractTreetapBlock.TreetapContainer.values().forEach { c ->
                    with(When.create().set(Properties.HORIZONTAL_FACING, f).set(DriedTreetapBlock.container, c), BlockStateVariant.create().put(VariantSettings.MODEL, loc(LCCBlocks.treetap) { it.plus("_${c.asString()}") }).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                    DriedTreetapBlock.liquid.values.forEach { l ->
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(DriedTreetapBlock.container, c).set(DriedTreetapBlock.liquid, l),
                            BlockStateVariant.create().put(VariantSettings.MODEL, loc(LCCBlocks.treetap) { it.plus("_${c.asString()}_${l.asString()}_dried") }).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                    }
                }
            }
        }
    }

}
