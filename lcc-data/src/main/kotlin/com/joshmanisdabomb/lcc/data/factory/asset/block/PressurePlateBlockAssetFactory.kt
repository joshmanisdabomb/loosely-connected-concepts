package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class PressurePlateBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val plate = Models.PRESSURE_PLATE_UP.upload(idh.loc(entry), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        val plateDown = Models.PRESSURE_PLATE_DOWN.upload(idh.locSuffix(entry, "down"), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.POWERED)
                .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, plate))
                .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, plateDown)))
        }
    }

}
