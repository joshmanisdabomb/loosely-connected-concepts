package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

open class FurnaceBlockAssetFactory(val texture: Identifier? = null, val textureOff: Identifier? = null, val textureOn: Identifier? = null, val textureSide: Identifier? = null, val textureTop: Identifier? = null, val textureBottom: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                BlockStateVariant.create().put(VariantSettings.MODEL, models.orientableBottom({ texture }, { textureTop }, { textureSide }, { textureBottom }, { if (l) textureOn ?: (texture ?: idh.loc(it)).suffix("on") else textureOff ?: (texture ?: idh.loc(it)) }).create(data, entry) { if (l) idh.locSuffix(it, "on") else null }).apply(ModelProvider.horizontalRotation(d))
            })
        }
    }

    companion object : FurnaceBlockAssetFactory()

}
