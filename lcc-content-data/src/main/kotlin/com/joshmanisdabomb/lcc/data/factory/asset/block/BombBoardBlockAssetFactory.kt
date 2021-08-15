package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object BombBoardBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.AXIS, BombBoardBlock.mine_state).register { f, i ->
            val factory = if (f.isHorizontal) models::pillarHorizontal else models::pillar
            BlockStateVariant.create()
                .put(VariantSettings.MODEL, when (i) {
                    BombBoardBlock.empty -> factory({ null }, { idh.locSuffix(it, "side") }, { idh.loc(it) }).create(data, entry) { idh.locSuffix(it, f.isHorizontal.transform("horizontal", null)) }
                    BombBoardBlock.mine -> factory({ null }, { idh.locSuffix(it, "mine") }, { idh.loc(it) }).create(data, entry) { idh.locSuffix(it, "mine").suffix(f.isHorizontal.transform("horizontal", null)) }
                    else -> factory({ null }, { idh.locSuffix(it, "side") }, { idh.locSuffix(it, i.toString()) }).create(data, entry) { idh.locSuffix(it, i.toString()).suffix(f.isHorizontal.transform("horizontal", null)) }
                })
                .put(VariantSettings.X, if (f.isHorizontal) VariantSettings.Rotation.R90 else VariantSettings.Rotation.R0)
                .put(VariantSettings.Y, if (f == Direction.Axis.X) VariantSettings.Rotation.R90 else VariantSettings.Rotation.R0)
        }) }
    }

}
