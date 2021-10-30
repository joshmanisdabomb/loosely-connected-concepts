package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.BlockHalf
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

open class TrapdoorBlockAssetFactory(val orientable: Boolean) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture.texture(entry)
        val bottom = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(idh.loc(entry), texture, data.models)
        val top = Models.TEMPLATE_TRAPDOOR_TOP.upload(idh.locSuffix(entry, "top"), texture, data.models)
        val open = Models.TEMPLATE_TRAPDOOR_OPEN.upload(idh.locSuffix(entry, "open"), texture, data.models)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.BLOCK_HALF).register { f, o, h ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, if (o) open else if (h == BlockHalf.TOP) top else bottom)
                    .put(VariantSettings.X, if (orientable && o && h == BlockHalf.TOP) VariantSettings.Rotation.R180 else VariantSettings.Rotation.R0)
                    .put(VariantSettings.Y, VariantSettings.Rotation.values()[(if (!orientable && !o) Direction.NORTH else if (orientable && o && h == BlockHalf.TOP) f else f.opposite).asRotation().toInt().div(90)])
            })
        }
    }

    companion object : TrapdoorBlockAssetFactory(true)

}
