package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class FenceGateBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val gate = Models.TEMPLATE_FENCE_GATE.upload(idh.loc(entry), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        val gateOpen = Models.TEMPLATE_FENCE_GATE_OPEN.upload(idh.locSuffix(entry, "open"), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        val gateWall = Models.TEMPLATE_FENCE_GATE_WALL.upload(idh.locSuffix(entry, "wall"), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        val gateWallOpen = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(idh.locSuffix(entry, "wall_open"), TextureMap.texture(texture ?: idh.loc(entry)), data.models)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.IN_WALL, Properties.OPEN).register { f, w, o ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, when (w) {
                        true -> if (o) gateWallOpen else gateWall
                        false -> if (o) gateOpen else gate
                    })
                    .put(VariantSettings.Y, VariantSettings.Rotation.values()[f.asRotation().toInt().div(90)])
                    .put(VariantSettings.UVLOCK, true)
            })
        }
    }

}
