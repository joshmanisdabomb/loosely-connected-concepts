package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class PressurePlateBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val plate = Models.PRESSURE_PLATE_UP.upload(loc(entry), Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        val plateDown = Models.PRESSURE_PLATE_DOWN.upload(loc(entry) { it.plus("_down") }, Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.POWERED)
                .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, plate))
                .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, plateDown)))
        }
    }

}
