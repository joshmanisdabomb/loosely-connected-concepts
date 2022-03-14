package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.modify
import com.joshmanisdabomb.lcc.extensions.prefix
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.block.enums.PistonType
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class PistonHeadBlockAssetFactory(val textureTopSticky: Identifier? = null, val textureTop: Identifier? = null, val textureSide: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val top = textureTop ?: idh.locSuffix(entry, "top").modify { it.replace("_head", "") }
        val texture = TextureMap()
            .put(TextureKey.SIDE, textureSide ?: idh.locSuffix(entry, "side").modify { it.replace("_head", "") })
            .put(TextureKey.UNSTICKY, top)
        val head = Models.TEMPLATE_PISTON_HEAD.upload(idh.loc(entry), texture.copyAndAdd(TextureKey.PLATFORM, top), data.models)
        val headShort = Models.TEMPLATE_PISTON_HEAD_SHORT.upload(idh.locSuffix(entry, "short"), texture.copyAndAdd(TextureKey.PLATFORM, top), data.models)
        val headSticky = Models.TEMPLATE_PISTON_HEAD.upload(idh.locSuffix(entry, "sticky"), texture.copyAndAdd(TextureKey.PLATFORM, textureTopSticky ?: top.prefix("sticky")), data.models)
        val headStickyShort = Models.TEMPLATE_PISTON_HEAD_SHORT.upload(idh.locSuffix(entry, "short").suffix("sticky"), texture.copyAndAdd(TextureKey.PLATFORM, textureTopSticky ?: top.prefix("sticky")), data.models)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.SHORT, Properties.PISTON_TYPE, Properties.FACING).register { short, type, dir ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, when {
                        short && type == PistonType.STICKY -> headStickyShort
                        short -> headShort
                        type == PistonType.STICKY -> headSticky
                        else -> head
                    })
                    .apply(ModelProvider.directionalRotation(dir, Direction.NORTH))
            })
        }
    }

    companion object : PistonHeadBlockAssetFactory()

}
