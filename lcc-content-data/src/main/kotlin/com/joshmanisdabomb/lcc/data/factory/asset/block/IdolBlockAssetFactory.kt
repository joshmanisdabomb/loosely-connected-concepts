package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.IdolBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

object IdolBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = TextureMap().put(TextureKey.TEXTURE, idh.loc(entry))
        val texturePedestal = TextureMap().put(TextureKey.TEXTURE, idh.loc(entry)).put(TextureKey.PLATFORM, idh.locSuffix(LCCBlocks.bifrost, "side"))
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.ROTATION, IdolBlock.pedestal).register { r, p ->
                val facing = horizontalDirections[r.plus(17).mod(16).div(4)]
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, when (r.mod(4)) {
                        1 -> p.transform(LCCModelTemplates.template_idol_225_pedestal, LCCModelTemplates.template_idol_225)
                        2 -> p.transform(LCCModelTemplates.template_idol_45_pedestal, LCCModelTemplates.template_idol_45)
                        3 -> p.transform(LCCModelTemplates.template_idol_n225_pedestal, LCCModelTemplates.template_idol_n225)
                        else -> p.transform(LCCModelTemplates.template_idol_pedestal, LCCModelTemplates.template_idol)
                    }.upload(idh.loc(entry).suffix("rot_" + r.mod(4)).suffix(p.transform("pedestal", null)), p.transform(texturePedestal, texture), data.models))
                    .apply(ModelProvider.horizontalRotation(facing))
            })
        }
    }

}
