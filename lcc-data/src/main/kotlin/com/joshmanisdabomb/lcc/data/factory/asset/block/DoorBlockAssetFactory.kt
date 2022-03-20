package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.DoorHinge
import net.minecraft.block.enums.DoubleBlockHalf
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

object DoorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap().put(TextureKey.TOP, idh.locSuffix(entry, "top")).put(TextureKey.BOTTOM, idh.locSuffix(entry, "bottom"))
        val bottom = Models.DOOR_BOTTOM.upload(idh.locSuffix(entry, "bottom"), texture, data.models)
        val bottomHinge = Models.DOOR_BOTTOM_RH.upload(idh.locSuffix(entry, "bottom_hinge"), texture, data.models)
        val top = Models.DOOR_TOP.upload(idh.locSuffix(entry, "top"), texture, data.models)
        val topHinge = Models.DOOR_TOP_RH.upload(idh.locSuffix(entry, "top_hinge"), texture, data.models)
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
                                .put(VariantSettings.MODEL, if (p4 == DoubleBlockHalf.LOWER) (if (rh.xor(p3)) bottomHinge else bottom) else (if (rh.xor(p3)) topHinge else top))
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
