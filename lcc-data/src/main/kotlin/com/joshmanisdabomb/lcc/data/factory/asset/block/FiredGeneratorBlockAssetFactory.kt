package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object FiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        val top = loc(LCC.id("generator"))
        val refiner = loc(LCC.id("refiner"))

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                val id2 = suffix(id, if (l) "on" else null)
                val top2 = suffix(top, if (l) "on" else null)
                BlockStateVariant.create().put(VariantSettings.MODEL, modelOrientableBottom(data, entry, id2, texture = refiner, textureFront = id2, textureTop = top2)).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![d]!!)
            })
        }
    }

}
