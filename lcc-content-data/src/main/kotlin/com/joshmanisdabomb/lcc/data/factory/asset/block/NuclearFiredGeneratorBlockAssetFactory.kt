package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.TextureMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.state.property.Properties

object NuclearFiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val top = LCC.block("generator")
        val machine = idh.loc(LCCBlocks.machine_enclosure)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT).register { d, l ->
                val texture = TextureMap.particle(machine.suffix("side"))
                    .put(LCCModelTextureKeys.t0, top.suffix(if (l) "on" else null))
                    .put(LCCModelTextureKeys.t1, idh.locSuffix(entry, "bottom_side"))
                    .put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "top_side"))
                    .put(LCCModelTextureKeys.t3, machine.suffix("bottom"))
                    .put(LCCModelTextureKeys.t4, machine.suffix("top"))
                    .put(LCCModelTextureKeys.t5, idh.locSuffix(entry, "redstone").suffix(if (l) "on" else null))
                    .put(LCCModelTextureKeys.t6, idh.locSuffix(entry, "bottom"))
                    .put(LCCModelTextureKeys.t7, idh.loc(LCCBlocks.heavy_uranium_shielding))
                BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_nuclear_generator.upload(idh.locSuffix(entry, if (l) "on" else null), texture, data.models)).apply(ModelProvider.horizontalRotation(d))
            })
        }
    }

}
