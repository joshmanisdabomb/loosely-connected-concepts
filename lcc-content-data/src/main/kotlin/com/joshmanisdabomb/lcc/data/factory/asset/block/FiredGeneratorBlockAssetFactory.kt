package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object FiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val top = LCC.block("generator")
        val machine = idh.loc(LCCBlocks.machine_enclosure)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                val id2 = idh.locSuffix(entry, if (l) "on" else null)
                val top2 = top.suffix(if (l) "on" else null)
                BlockStateVariant.create().put(VariantSettings.MODEL, models.orientableBottom(texture = { machine }, textureFront = { id2 }, textureTop = { top2 }).create(data, entry) { id2 }).apply(ModelProvider.horizontalRotation(d))
            })
        }
    }

}
