package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class FurnaceBlockAssetFactory(val texture: Identifier? = null, val textureOff: Identifier? = null, val textureOn: Identifier? = null, val textureSide: Identifier? = null, val textureTop: Identifier? = null, val textureBottom: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = texture ?: loc(entry)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                BlockStateVariant.create().put(VariantSettings.MODEL, modelOrientableBottom(data, entry, suffix(loc(entry), if (l) "on" else null), texture, textureTop ?: suffix(texture, "top"), textureSide ?: suffix(texture, "side"), textureBottom ?: suffix(texture, "bottom"), if (l) textureOn ?: suffix(texture, "on") else textureOff ?: texture)).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![d]!!)
            })
        }
    }

    companion object : FurnaceBlockAssetFactory()

}
