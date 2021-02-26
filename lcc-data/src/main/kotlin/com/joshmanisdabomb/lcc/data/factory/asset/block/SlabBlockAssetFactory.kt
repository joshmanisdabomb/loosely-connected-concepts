package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.SlabType
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

open class SlabBlockAssetFactory(val texture: Identifier, val textureTop: Identifier? = texture, val textureSide: Identifier? = texture, val textureBottom: Identifier? = texture, val full: SlabBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture().put(TextureKey.TOP, textureTop ?: loc(entry)).put(TextureKey.SIDE, textureSide ?: loc(entry)).put(TextureKey.BOTTOM, textureBottom ?: loc(entry))
        val slab = Models.SLAB.upload(loc(entry), texture, data.modelStates::addModel)
        val slabTop = Models.SLAB_TOP.upload(loc(entry) { it.plus("_top") }, texture, data.modelStates::addModel)
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.SLAB_TYPE).register {
            BlockStateVariant.create().put(VariantSettings.MODEL, when (it) {
                SlabType.TOP -> slabTop
                SlabType.BOTTOM -> slab
                else -> full(data, entry)
            })
        }) }
    }

}
