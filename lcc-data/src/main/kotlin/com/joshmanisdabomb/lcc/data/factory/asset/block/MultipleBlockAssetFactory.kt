package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.VariantSettings
import net.minecraft.data.client.VariantsBlockStateSupplier

class MultipleBlockAssetFactory(val y: List<Int> = listOf(0), val ids: List<ModelProvider.ModelFactory<Block>>) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
       stateVariant(data, entry, {
           VariantsBlockStateSupplier.create(entry, *ids.flatMapIndexed { k, v -> y.map { y -> BlockStateVariant.create().put(VariantSettings.MODEL, v.create(data, entry) { idh.locSuffix(it, if (k == 0) null else k.plus(1).toString()) }).put(VariantSettings.Y, VariantSettings.Rotation.values()[y]) } }.toTypedArray())
       }) {}
    }

}
