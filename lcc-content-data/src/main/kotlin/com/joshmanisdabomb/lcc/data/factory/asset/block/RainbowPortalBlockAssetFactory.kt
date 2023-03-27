package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RainbowPortalBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object RainbowPortalBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_AXIS, RainbowPortalBlock.y, RainbowPortalBlock.middle).register { axis, y, middle ->
                val particle = idh.locSuffix(entry, y.toString())
                val location = idh.locSuffix(entry, y.toString())
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, if (middle) models.particle { particle }.create(data, entry) { location.suffix("middle") } else LCCModelTemplates.template_rainbow_portal.upload(location, TextureMap().put(TextureKey.TEXTURE, idh.locSuffix(entry, y.plus(1).toString())).put(TextureKey.PARTICLE, particle), data.models))
                    .put(VariantSettings.Y, if (axis == Direction.Axis.Z) VariantSettings.Rotation.R90 else VariantSettings.Rotation.R0)
            })
        }
    }

}
