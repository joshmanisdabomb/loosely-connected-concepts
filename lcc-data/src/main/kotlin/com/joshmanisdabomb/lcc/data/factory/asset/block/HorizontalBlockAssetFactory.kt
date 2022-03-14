package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

open class HorizontalBlockAssetFactory(val model: ModelProvider.ModelFactory<Block> = ModelProvider.block.orientable(), val default: Direction = Direction.NORTH) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, model) { coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register {
            BlockStateVariant.create().apply(ModelProvider.horizontalRotation(it))
        }) }
    }

    companion object : HorizontalBlockAssetFactory()

}