package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.VariantsBlockStateSupplier
import net.minecraft.util.Identifier

class MultipleBlockAssetFactory(val y: List<Int> = listOf(0), val ids: MultipleBlockAssetFactory.(data: DataAccessor, entry: Block) -> List<Identifier>) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
       stateVariant(data, entry, {
           VariantsBlockStateSupplier.create(entry, *ids(data, entry).flatMap { y.map { y -> BlockStateVariant.create().put(VariantSettings.MODEL, it).put(VariantSettings.Y, VariantSettings.Rotation.values()[y]) } }.toTypedArray())
       }) {}
    }

}
