package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.block.enums.DoorHinge
import net.minecraft.block.enums.DoubleBlockHalf
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties

object DoorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture().put(TextureKey.TOP, loc(entry) { it.plus("_top") }).put(TextureKey.BOTTOM, loc(entry) { it.plus("_bottom") })
        val bottom = Models.DOOR_BOTTOM.upload(loc(entry) { it.plus("_bottom") }, texture, data.modelStates::addModel)
        val bottomHinge = Models.DOOR_BOTTOM_RH.upload(loc(entry) { it.plus("_bottom_hinge") }, texture, data.modelStates::addModel)
        val top = Models.DOOR_TOP.upload(loc(entry) { it.plus("_top") }, texture, data.modelStates::addModel)
        val topHinge = Models.DOOR_TOP_RH.upload(loc(entry) { it.plus("_top_hinge") }, texture, data.modelStates::addModel)
        stateVariant(data, entry) {
            val c = BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.DOOR_HINGE, Properties.OPEN, Properties.DOUBLE_BLOCK_HALF)
            Properties.HORIZONTAL_FACING.values.forEach { p1 ->
                Properties.DOOR_HINGE.values.forEach { p2 ->
                    Properties.OPEN.values.forEach { p3 ->
                        Properties.DOUBLE_BLOCK_HALF.values.forEach { p4 ->
                            var dir = p1.rotateYClockwise()
                            val rh = p2 == DoorHinge.RIGHT
                            if (p3) dir = dir.let { if (rh) dir.rotateYCounterclockwise() else dir.rotateYClockwise() }
                            c.register(p1, p2, p3, p4, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, if (p4 == DoubleBlockHalf.LOWER) rh.xor(p3).transform(bottomHinge, bottom) else rh.xor(p3).transform(topHinge, top))
                                .put(VariantSettings.Y, VariantSettings.Rotation.values()[dir.asRotation().toInt().div(90)])
                            )
                        }
                    }
                }
            }
            coordinate(c)
        }
    }

}
