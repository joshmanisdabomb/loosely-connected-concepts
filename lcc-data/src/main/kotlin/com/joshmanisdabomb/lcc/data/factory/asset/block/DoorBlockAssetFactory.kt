package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.*

object DoorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap().put(TextureKey.TOP, idh.locSuffix(entry, "top")).put(TextureKey.BOTTOM, idh.locSuffix(entry, "bottom"))
        val bottomLeft = Models.DOOR_BOTTOM_LEFT.upload(idh.locSuffix(entry, "bottom_left"), texture, data.models)
        val bottomLeftOpen = Models.DOOR_BOTTOM_LEFT_OPEN.upload(idh.locSuffix(entry, "bottom_left_open"), texture, data.models)
        val bottomRight = Models.DOOR_BOTTOM_RIGHT.upload(idh.locSuffix(entry, "bottom_right"), texture, data.models)
        val bottomRightOpen = Models.DOOR_BOTTOM_RIGHT_OPEN.upload(idh.locSuffix(entry, "bottom_right_open"), texture, data.models)
        val topLeft = Models.DOOR_TOP_LEFT.upload(idh.locSuffix(entry, "top_left"), texture, data.models)
        val topLeftOpen = Models.DOOR_TOP_LEFT_OPEN.upload(idh.locSuffix(entry, "top_left_open"), texture, data.models)
        val topRight = Models.DOOR_TOP_RIGHT.upload(idh.locSuffix(entry, "top_right"), texture, data.models)
        val topRightOpen = Models.DOOR_TOP_RIGHT_OPEN.upload(idh.locSuffix(entry, "top_right_open"), texture, data.models)
        stateVariant(data, entry, {
            BlockStateModelGenerator.createDoorBlockState(entry, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen) as VariantsBlockStateSupplier
        }) {}
    }

}
