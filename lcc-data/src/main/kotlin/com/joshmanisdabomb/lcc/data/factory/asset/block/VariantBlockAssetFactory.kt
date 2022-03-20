package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.state.property.Property
import net.minecraft.util.StringIdentifiable

class VariantBlockAssetFactory<T : Comparable<T>>(val property: Property<T>, val provider: (T) -> ModelProvider.ModelFactory<Block>, val suffix: (T) -> String? = { (it == property.values.first()).transform(null, if (it is StringIdentifiable) it.asString() else it.toString()) }) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(property).register { t ->
                BlockStateVariant.create().put(VariantSettings.MODEL, provider(t).create(data, entry) { idh.locSuffix(entry, suffix(t)) })
            })
        }
    }

}
