package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.modify
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class PistonBlockAssetFactory(val base: Identifier = Identifier("block/piston_base"), val textureBottom: Identifier? = null, val textureSide: Identifier? = null, val textureTop: Identifier? = null, val textureTop2: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap()
            .put(TextureKey.BOTTOM, textureBottom ?: Identifier("block/piston_bottom"))
            .put(TextureKey.SIDE, textureSide ?: idh.locSuffix(entry, "side").modify { it.replace("sticky_", "") })
            .put(TextureKey.PLATFORM, textureTop ?: idh.locSuffix(entry, "top"))
        val piston = Models.TEMPLATE_PISTON.upload(idh.loc(entry), texture, data.models)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.EXTENDED, Properties.FACING).register { extended, dir ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, extended.transform(base, piston))
                    .apply(ModelProvider.directionalRotation(dir, Direction.NORTH))
            })
        }
    }

    companion object : PistonBlockAssetFactory()

}
