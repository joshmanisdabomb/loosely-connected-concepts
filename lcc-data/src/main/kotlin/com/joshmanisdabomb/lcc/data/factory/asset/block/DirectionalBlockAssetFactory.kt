package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

open class DirectionalBlockAssetFactory(val default: Direction = Direction.UP, val model: ModelProvider.ModelFactory<Block> = ModelProvider.block.orientableVertical()) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariantModel(data, entry, model) { coordinate(BlockStateVariantMap.create(Properties.FACING).register {
            BlockStateVariant.create().apply(ModelProvider.directionalRotation(it))
        }) }
    }

    companion object : DirectionalBlockAssetFactory()

}