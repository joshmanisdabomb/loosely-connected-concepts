package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object BombBoardBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) { coordinate(BlockStateVariantMap.create(Properties.AXIS, BombBoardBlock.mine_state).register { f, i ->
            val model = if (f.isHorizontal) LCCModelTemplates.cube_column_horizontal_with_particle else LCCModelTemplates.cube_column_with_particle
            BlockStateVariant.create()
                .put(VariantSettings.MODEL, when (i) {
                    BombBoardBlock.mystery -> model.upload(idh.locSuffix(entry, f.isHorizontal.transform("horizontal", null)), TextureMap().put(TextureKey.SIDE, idh.locSuffix(entry, "side")).put(TextureKey.END, idh.loc(entry)).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
                    BombBoardBlock.mine -> model.upload(idh.locSuffix(entry, "mine").suffix(f.isHorizontal.transform("horizontal", null)), TextureMap().put(TextureKey.SIDE, idh.locSuffix(entry, "mine")).put(TextureKey.END, idh.loc(entry)).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
                    else -> model.upload(idh.locSuffix(entry, i.toString()).suffix(f.isHorizontal.transform("horizontal", null)), TextureMap().put(TextureKey.SIDE, idh.locSuffix(entry, "side")).put(TextureKey.END, idh.locSuffix(entry, i.toString())).put(TextureKey.PARTICLE, idh.locSuffix(entry, "0")), data.models)
                })
                .put(VariantSettings.X, if (f.isHorizontal) VariantSettings.Rotation.R90 else VariantSettings.Rotation.R0)
                .put(VariantSettings.Y, if (f == Direction.Axis.X) VariantSettings.Rotation.R90 else VariantSettings.Rotation.R0)
        }) }
    }

}
