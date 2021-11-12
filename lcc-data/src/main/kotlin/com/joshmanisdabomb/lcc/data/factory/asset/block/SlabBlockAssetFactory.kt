package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.SlabType
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

open class SlabBlockAssetFactory(val texture: Identifier, val textureTop: Identifier? = texture, val textureSide: Identifier? = texture, val textureBottom: Identifier? = texture, val full: Identifier) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture().put(TextureKey.TOP, textureTop ?: idh.loc(entry)).put(TextureKey.SIDE, textureSide ?: idh.loc(entry)).put(TextureKey.BOTTOM, textureBottom ?: idh.loc(entry))
        val slab = Models.SLAB.upload(idh.loc(entry), texture, data.models)
        val slabTop = Models.SLAB_TOP.upload(idh.locSuffix(entry, "top"), texture, data.models)
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.SLAB_TYPE).register {
            BlockStateVariant.create().put(VariantSettings.MODEL, when (it) {
                SlabType.TOP -> slabTop
                SlabType.BOTTOM -> slab
                else -> full
            })
        }) }
    }

}
