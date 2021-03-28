package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object NuclearFiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        val top = loc(LCC.id("generator"))
        val machine = loc(LCC.id("machine_enclosure"))

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                val id2 = suffix(id, if (l) "on" else null)
                val texture = Texture.particle(suffix(machine, "side"))
                    .put(LCCModelTextureKeys.t0, suffix(top, if (l) "on" else null))
                    .put(LCCModelTextureKeys.t1, suffix(id, "bottom_side"))
                    .put(LCCModelTextureKeys.t2, suffix(id, "top_side"))
                    .put(LCCModelTextureKeys.t3, suffix(machine, "bottom"))
                    .put(LCCModelTextureKeys.t4, suffix(machine, "top"))
                    .put(LCCModelTextureKeys.t5, suffix(suffix(id, "redstone"), if (l) "on" else null))
                    .put(LCCModelTextureKeys.t6, suffix(id, "bottom"))
                    .put(LCCModelTextureKeys.t7, loc(LCCBlocks.heavy_uranium_shielding))
                BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_nuclear_generator.upload(id2, texture, data.modelStates::addModel)).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![d]!!)
            })
        }
    }

}
