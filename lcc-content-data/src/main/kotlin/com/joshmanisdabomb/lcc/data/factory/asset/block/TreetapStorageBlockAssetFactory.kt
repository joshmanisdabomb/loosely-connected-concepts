package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.AbstractTreetapBlock
import com.joshmanisdabomb.lcc.block.TreetapStorageBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

class TreetapStorageBlockAssetFactory(val container: AbstractTreetapBlock.TreetapContainer, vararg val liquids: Model?, val model: ModelProvider.ModelFactory<Block>) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (entry !is TreetapStorageBlock) error("The given entry is not a treetap storage.")
        val model = model.create(data, entry)
        stateMultipart(data, entry) {
            horizontalDirections.forEach { f ->
                with(When.create().set(Properties.HORIZONTAL_FACING, f), BlockStateVariant.create().put(VariantSettings.MODEL, model).apply(ModelProvider.horizontalRotation(f)))
                AbstractTreetapBlock.TreetapLiquid.values().forEach { l ->
                    val texture = Texture().put(LCCModelTextureKeys.t2, LCC.block(l.asString()))
                    val liquids = liquids.dropLast(1).filterNotNull()
                    val overflow = LCCModelTemplates.template_treetap_overflow.upload(idh.suffix(model, l.asString() + "_" + liquids.size.plus(1).toString()), texture, data.modelStates::addModel)
                    this@TreetapStorageBlockAssetFactory.liquids.last()?.upload(idh.suffix(model, l.asString() + "_dried"), Texture().put(LCCModelTextureKeys.t2, LCC.block(l.asString()).suffix("dry")), data.modelStates::addModel)
                    liquids.forEachIndexed { k, v ->
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, k.plus(1)),
                            BlockStateVariant.create().put(VariantSettings.MODEL, v.upload(idh.suffix(model, l.asString() + "_" + k.plus(1).toString()), texture, data.modelStates::addModel)).apply(ModelProvider.horizontalRotation(f)))
                    }
                    (entry.container.amount+1..entry.max).forEach {
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, it), BlockStateVariant.create().put(VariantSettings.MODEL, idh.suffix(model, l.asString() + "_" + liquids.size.toString())).apply(ModelProvider.horizontalRotation(f)))
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, it), BlockStateVariant.create().put(VariantSettings.MODEL, overflow).apply(ModelProvider.horizontalRotation(f)))
                    }
                }
            }
        }
    }

}
