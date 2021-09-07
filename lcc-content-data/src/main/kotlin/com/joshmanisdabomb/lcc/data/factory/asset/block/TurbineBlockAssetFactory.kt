package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.TurbineBlock
import com.joshmanisdabomb.lcc.block.TurbineBlock.Companion.turbine_state
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.data.client.model.*

object TurbineBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(turbine_state).register { p ->
                val id = idh.locSuffix(entry, if (p != TurbineBlock.TurbineState.UNPOWERED) "on" else null)
                BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_turbine.upload(id, Texture().put(TextureKey.TOP, idh.locSuffix(LCCBlocks.solar_panel, "bottom")).put(TextureKey.SIDE, idh.locSuffix(LCCBlocks.solar_panel, "side")).put(TextureKey.BOTTOM, id).put(TextureKey.PARTICLE, idh.loc(entry)), data.modelStates::addModel))
            })
        }
    }

}
