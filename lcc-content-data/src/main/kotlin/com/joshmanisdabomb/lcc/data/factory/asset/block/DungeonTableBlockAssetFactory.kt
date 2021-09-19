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
                if (bottom) BlockStateVariant.create().put(VariantSettings.MODEL, models.cubeBottomTop(textureSide = { idh.locSuffix(entry, entity.asString()) }).create(data, entry) { idh.locSuffix(entry, entity.asString()) })
                else BlockStateVariant.create().put(VariantSettings.MODEL, LCCModelTemplates.template_spawner_table.upload(idh.loc(entry), Texture.sideTopBottom(entry).put(TextureKey.PARTICLE, idh.locSuffix(entry, "bottom")), data.modelStates::addModel))
            })
        }
    }

}
