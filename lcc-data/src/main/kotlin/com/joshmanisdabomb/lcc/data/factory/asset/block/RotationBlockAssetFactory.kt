package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.VariantSettings
import net.minecraft.data.client.VariantsBlockStateSupplier

open class RotationBlockAssetFactory(val x: List<Int> = listOf(0), val y: List<Int> = (0..3).toList(), val model: ModelProvider.ModelFactory<Block> = ModelProvider.block.cubeAll()) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val variants = mutableListOf<BlockStateVariant>()
        val model = model.create(data, entry)
        for (i in x) {
            for (j in y) {
                variants.add(BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.X, VariantSettings.Rotation.values()[i]).put(VariantSettings.Y, VariantSettings.Rotation.values()[j]))
            }
        }
        stateVariant(data, entry, { VariantsBlockStateSupplier.create(it, *variants.toTypedArray()) }) {}
    }

    companion object : RotationBlockAssetFactory()

}