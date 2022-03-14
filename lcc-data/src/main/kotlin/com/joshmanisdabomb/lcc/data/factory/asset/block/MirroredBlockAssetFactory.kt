package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.VariantSettings
import net.minecraft.data.client.VariantsBlockStateSupplier
import net.minecraft.util.Identifier

open class MirroredBlockAssetFactory(val x: List<Int> = listOf(0), val y: List<Int> = listOf(0, 2), val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = idh.loc(entry)
        val variants = mutableListOf<BlockStateVariant>()
        for (i in x) {
            for (j in y) {
                variants.add(BlockStateVariant.create().put(VariantSettings.MODEL, models.cubeAll { texture }.create(data, entry)).put(VariantSettings.X, VariantSettings.Rotation.values()[i]).put(VariantSettings.Y, VariantSettings.Rotation.values()[j]))
                variants.add(BlockStateVariant.create().put(VariantSettings.MODEL, models.cubeMirrored { texture }.create(data, entry) { id.suffix("mirrored") }).put(VariantSettings.X, VariantSettings.Rotation.values()[i]).put(VariantSettings.Y, VariantSettings.Rotation.values()[j]))
            }
        }
        stateVariant(data, entry, { VariantsBlockStateSupplier.create(it, *variants.toTypedArray()) }) {}
    }

    companion object : MirroredBlockAssetFactory()

}