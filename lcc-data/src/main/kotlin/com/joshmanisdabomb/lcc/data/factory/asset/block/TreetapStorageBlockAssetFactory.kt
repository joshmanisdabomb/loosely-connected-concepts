package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.AbstractTreetapBlock
import com.joshmanisdabomb.lcc.block.TreetapStorageBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

class TreetapStorageBlockAssetFactory(val container: AbstractTreetapBlock.TreetapContainer, vararg val liquids: Model?, val model: TreetapStorageBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (entry !is TreetapStorageBlock) error("The given entry is not a treetap storage.")
        val model = model(data, entry)
        stateMultipart(data, entry) {
            horizontalDirections.forEach { f ->
                with(When.create().set(Properties.HORIZONTAL_FACING, f), BlockStateVariant.create().put(VariantSettings.MODEL, model).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                AbstractTreetapBlock.TreetapLiquid.values().forEach { l ->
                    val texture = Texture().put(LCCModelTextureKeys.t2, loc(LCC.id(l.asString())))
                    val liquids = liquids.dropLast(1).filterNotNull()
                    val overflow = LCCModelTemplates.template_treetap_overflow.upload(suffix(model, l.asString() + "_" + liquids.size.plus(1).toString()), texture, data.modelStates::addModel)
                    this@TreetapStorageBlockAssetFactory.liquids.last()?.upload(suffix(model, l.asString() + "_dried"), Texture().put(LCCModelTextureKeys.t2, loc(LCC.id(l.asString())) { it.plus("_dry") }), data.modelStates::addModel)
                    liquids.forEachIndexed { k, v ->
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, k.plus(1)),
                            BlockStateVariant.create().put(VariantSettings.MODEL, v.upload(suffix(model, l.asString() + "_" + k.plus(1).toString()), texture, data.modelStates::addModel)).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                    }
                    (entry.container.amount+1..entry.max).forEach {
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, it), BlockStateVariant.create().put(VariantSettings.MODEL, suffix(model, l.asString() + "_" + liquids.size.toString())).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                        with(When.create().set(Properties.HORIZONTAL_FACING, f).set(TreetapStorageBlock.liquid, l).set(entry.progress, it), BlockStateVariant.create().put(VariantSettings.MODEL, overflow).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![f]!!))
                    }
                }
            }
        }
    }

}
