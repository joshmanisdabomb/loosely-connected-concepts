package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class SideBottomTopBlockAssetFactory(val texture: Identifier? = null, val textureTop: Identifier? = if (texture == null) null else suffix(texture, "top"), val textureSide: Identifier? = if (texture == null) null else suffix(texture, "side"), val textureBottom: Identifier? = if (texture == null) null else suffix(texture, "bottom")) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = texture ?: loc(entry)
        stateOne(data, entry) { modelCubeBottomTop(data, entry, loc(entry), texture, textureTop ?: suffix(texture, "top"), textureSide ?: suffix(texture, "side"), textureBottom ?: suffix(texture, "bottom")) }
    }

    companion object : SideBottomTopBlockAssetFactory()

}
