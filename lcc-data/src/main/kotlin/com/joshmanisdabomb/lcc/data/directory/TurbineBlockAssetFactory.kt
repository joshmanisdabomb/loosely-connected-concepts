package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.TurbineBlock
import com.joshmanisdabomb.lcc.block.TurbineBlock.Companion.turbine_state
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.block.BlockAssetFactory
import net.minecraft.block.Block
import net.minecraft.data.client.model.*

object TurbineBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(turbine_state).register { p ->
                val id = loc(entry) { if (p != TurbineBlock.TurbineState.UNPOWERED) it.plus("_on") else it }
                BlockStateVariant.create().put(VariantSettings.MODEL, ModelTemplates.template_turbine.upload(id, Texture().put(TextureKey.TOP, loc(LCC.id("solar_panel")) { it.plus("_bottom") }).put(TextureKey.SIDE, loc(LCC.id("solar_panel")) { it.plus("_side") }).put(TextureKey.BOTTOM, id).put(TextureKey.PARTICLE, loc(entry)), data.modelStates::addModel))
            })
        }
    }

}
