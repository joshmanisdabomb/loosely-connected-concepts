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
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object ExplodingNuclearFiredGeneratorBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(LCCBlocks.nuclear_generator)
        val top = loc(LCC.id("generator"))
        val machine = loc(LCC.id("machine_enclosure"))
        val model = LCCModelTemplates.template_nuclear_generator_full.upload(loc(entry), Texture.particle(suffix(machine, "side"))
            .put(LCCModelTextureKeys.t0, suffix(top, "on"))
            .put(LCCModelTextureKeys.t1, suffix(id, "bottom_side"))
            .put(LCCModelTextureKeys.t2, suffix(id, "top_side"))
            .put(LCCModelTextureKeys.t3, suffix(machine, "bottom"))
            .put(LCCModelTextureKeys.t4, suffix(machine, "top"))
            .put(LCCModelTextureKeys.t5, suffix(id, "redstone_on"))
            .put(LCCModelTextureKeys.t6, suffix(id, "bottom"))
            .put(LCCModelTextureKeys.t7, suffix(id, "meltdown"))
            .put(LCCModelTextureKeys.t8, suffix(id, "meltdown"))
            .put(LCCModelTextureKeys.t9, loc(LCCBlocks.heavy_uranium_shielding))
        , data.modelStates::addModel)
        stateVariantModel(data, entry, { model }) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![it]!!)
        }) }
    }

}
