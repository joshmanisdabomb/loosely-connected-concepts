package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.HardLightBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.modify
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

class HardLightBlockAssetFactory(val model: (Boolean, Boolean) -> Model) : BlockAssetFactory {

    constructor(model: Model) : this({ l, r ->  model })

    override fun apply(data: DataAccessor, entry: Block) {
        val id = idh.loc(entry)
        val tid = id.modify { it.replace("hard_light_block", "hard_light") }
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.UP, HardLightBlock.left, Properties.DOWN, HardLightBlock.right).register { facing, u, l, d, r ->
                val vsuffix = (u.transform("u", "") + d.transform("d", "")).takeUnless(String::isEmpty)
                val hsuffix = (l.transform("", "l") + r.transform("", "r")).takeUnless(String::isEmpty)

                val texture = TextureMap()
                    .put(TextureKey.TEXTURE, tid.suffix(hsuffix).suffix(vsuffix))
                    .put(TextureKey.SIDE, tid.suffix("lr").suffix(vsuffix))
                    .put(TextureKey.PARTICLE, tid)

                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, model(l, r).upload(id.suffix(hsuffix).suffix(vsuffix), texture, data.models))
                    .apply(ModelProvider.horizontalRotation(facing))
            })
        }
    }

}
