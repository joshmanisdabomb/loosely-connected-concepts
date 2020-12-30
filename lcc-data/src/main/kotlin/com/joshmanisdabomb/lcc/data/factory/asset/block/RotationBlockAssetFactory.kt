package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.VariantsBlockStateSupplier
import net.minecraft.util.Identifier

open class RotationBlockAssetFactory(val model: RotationBlockAssetFactory.(data: DataAccessor, entry: Block) -> Identifier = { d, b -> modelCubeAll(d, b) }, val x: IntRange = 0..0, val y: IntRange = 0..3) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val variants = mutableListOf<BlockStateVariant>()
        for (i in x) {
            for (j in y) {
                variants.add(BlockStateVariant.create().put(VariantSettings.MODEL, model(data, entry)).put(VariantSettings.X, VariantSettings.Rotation.values()[i]).put(VariantSettings.Y, VariantSettings.Rotation.values()[j]))
            }
        }
        stateVariant(data, entry, { VariantsBlockStateSupplier.create(it, *variants.toTypedArray()) }) {}
    }

    companion object : RotationBlockAssetFactory()

}