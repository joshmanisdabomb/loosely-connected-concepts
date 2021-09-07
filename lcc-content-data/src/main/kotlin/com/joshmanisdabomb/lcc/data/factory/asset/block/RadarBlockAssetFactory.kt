package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

object RadarBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.TRIGGERED, Properties.HORIZONTAL_FACING).register { t, f ->
                BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_radar.upload(if (t) idh.locSuffix(entry, "on") else idh.loc(entry), if (t) texture(entry, "on", "active") else texture(entry, null, "inactive"), data.modelStates::addModel)).apply(ModelProvider.horizontalRotation(f))
            })
        }
    }

    private fun texture(entry: Block, suffix: String?, tendril: String) = Texture().put(LCCModelTextureKeys.t0, Identifier("block/sculk_sensor_bottom")).put(LCCModelTextureKeys.t1, Identifier("block/sculk_sensor_side")).put(LCCModelTextureKeys.t2, Identifier("block/sculk_sensor_top")).put(LCCModelTextureKeys.t3, Identifier("block/sculk_sensor_tendril_$tendril")).put(LCCModelTextureKeys.t4, idh.locSuffix(entry, suffix))

}
