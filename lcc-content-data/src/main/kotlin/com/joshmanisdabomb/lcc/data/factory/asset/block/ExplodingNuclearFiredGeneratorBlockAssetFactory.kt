package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.Texture
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object ExplodingNuclearFiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = idh.loc(LCCBlocks.nuclear_generator)
        val top = LCC.block("generator")
        val machine = idh.loc(LCCBlocks.machine_enclosure)
        val model = LCCModelTemplates.template_nuclear_generator_full.upload(idh.loc(entry), Texture.particle(machine.suffix("side"))
            .put(LCCModelTextureKeys.t0, top.suffix("on"))
            .put(LCCModelTextureKeys.t1, id.suffix("bottom_side"))
            .put(LCCModelTextureKeys.t2, id.suffix("top_side"))
            .put(LCCModelTextureKeys.t3, machine.suffix("bottom"))
            .put(LCCModelTextureKeys.t4, machine.suffix("top"))
            .put(LCCModelTextureKeys.t5, id.suffix("redstone_on"))
            .put(LCCModelTextureKeys.t6, id.suffix("bottom"))
            .put(LCCModelTextureKeys.t7, id.suffix("meltdown"))
            .put(LCCModelTextureKeys.t8, id.suffix("meltdown"))
            .put(LCCModelTextureKeys.t9, idh.loc(LCCBlocks.heavy_uranium_shielding))
        , data.modelStates::addModel)
        stateVariantModel(data, entry, { d, t, i -> model }) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(ModelProvider.horizontalRotation(it))
        }) }
    }

}
