package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class FenceGateBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val gate = Models.TEMPLATE_FENCE_GATE.upload(loc(entry), Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        val gateOpen = Models.TEMPLATE_FENCE_GATE_OPEN.upload(loc(entry) { it.plus("_open") }, Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        val gateWall = Models.TEMPLATE_FENCE_GATE_WALL.upload(loc(entry) { it.plus("_wall") }, Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        val gateWallOpen = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(loc(entry) { it.plus("_wall_open") }, Texture.texture(texture ?: loc(entry)), data.modelStates::addModel)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.IN_WALL, Properties.OPEN).register { f, w, o ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, when (w) {
                        true -> o.transform(gateWallOpen, gateWall)
                        false -> o.transform(gateOpen, gate)
                    })
                    .put(VariantSettings.Y, VariantSettings.Rotation.values()[f.asRotation().toInt().div(90)])
                    .put(VariantSettings.UVLOCK, true)
            })
        }
    }

}
