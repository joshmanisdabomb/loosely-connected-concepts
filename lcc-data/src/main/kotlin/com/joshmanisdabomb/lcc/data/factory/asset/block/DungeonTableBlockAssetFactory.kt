package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.DungeonTableBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties

object DungeonTableBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(DungeonTableBlock.ENTITY, Properties.BOTTOM).register { entity, bottom ->
                if (bottom) BlockStateVariant.create().put(VariantSettings.MODEL, modelCubeBottomTop(data, entry, loc(entry) { it.plus("_${entity.asString()}") }, textureSide = loc(entry) { it.plus("_${entity.asString()}") }))
                else BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_spawner_table.upload(loc(entry), Texture.sideTopBottom(entry).put(TextureKey.PARTICLE, loc(entry) { it.plus("_bottom") }), data.modelStates::addModel))
            })
        }
    }

}
