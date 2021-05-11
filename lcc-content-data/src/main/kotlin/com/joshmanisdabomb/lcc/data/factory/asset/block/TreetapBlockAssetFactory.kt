package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.AbstractTreetapBlock
import com.joshmanisdabomb.lcc.block.TreetapBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.When
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object TreetapBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val tap = LCCModelTemplates.template_treetap.upload(idh.loc(entry), Texture().put(LCCModelTextureKeys.t0, idh.loc(entry)), data.modelStates::addModel)
        val overflows = AbstractTreetapBlock.TreetapLiquid.values().map { l -> l to LCCModelTemplates.template_treetap_overflow.upload(idh.locSuffix(entry, "overflow_${l.asString()}"), Texture().put(LCCModelTextureKeys.t2, LCC.block(l.asString())), data.modelStates::addModel) }.toMap()
        stateMultipart(data, entry) {
            TreetapBlock.TreetapState.values().forEach { t ->
                horizontalDirections.forEach { f ->
                    with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapBlock.tap, t), BlockStateVariant.create().put(VariantSettings.MODEL, t.container?.run { idh.locSuffix(entry, "_${this.asString()}") } ?: tap).apply(ModelProvider.horizontalRotation(f)))

                    t.liquid?.also {
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapBlock.tap, t), BlockStateVariant.create().put(VariantSettings.MODEL, overflows[it]).apply(ModelProvider.horizontalRotation(f)))
                    }
                }
            }
        }
    }

}
