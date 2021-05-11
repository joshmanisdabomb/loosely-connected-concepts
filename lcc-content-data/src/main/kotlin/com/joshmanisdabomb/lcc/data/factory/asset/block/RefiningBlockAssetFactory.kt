package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.math.Direction

object RefiningBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (entry !is RefiningBlock) return

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(entry.processes, HORIZONTAL_FACING).register { p, d ->
                BlockStateVariant.create().put(VariantSettings.MODEL, models.orientableBottom(texture = { idh.loc(LCCBlocks.machine_enclosure) }, textureFront = { idh.locSuffix(entry, "front").suffix(p.nullableName) }, textureTop = { idh.locSuffix(entry, "top").suffix(p.nullableName) }).create(data, entry) { idh.locSuffix(entry, p.nullableName) }).apply(ModelProvider.horizontalRotation(d))
            })
        }
    }

}
