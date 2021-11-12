package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.asset.AssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.MultipartBlockStateSupplier
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.VariantsBlockStateSupplier

interface BlockAssetFactory : BlockDataFactory, AssetFactory<Block> {

    override val models get() = ModelProvider.block

    fun stateOne(data: DataAccessor, entry: Block, supplier: (entry: Block) -> VariantsBlockStateSupplier = VariantsBlockStateSupplier::create, consumer: VariantsBlockStateSupplier.() -> Unit = {}, model: ModelProvider.ModelFactory<Block>) = stateVariantModel(data, entry, model, consumer)

    fun stateVariant(data: DataAccessor, entry: Block, supplier: (entry: Block) -> VariantsBlockStateSupplier = VariantsBlockStateSupplier::create, consumer: VariantsBlockStateSupplier.() -> Unit) = data.states.accept(entry.identifier, supplier(entry).apply(consumer))

    fun stateVariantModel(data: DataAccessor, entry: Block, model: ModelProvider.ModelFactory<Block>, consumer: VariantsBlockStateSupplier.() -> Unit) = stateVariant(data, entry, { VariantsBlockStateSupplier.create(entry, BlockStateVariant.create().put(VariantSettings.MODEL, model.create(data, entry))) }, consumer)

    fun stateMultipart(data: DataAccessor, entry: Block, supplier: (entry: Block) -> MultipartBlockStateSupplier = MultipartBlockStateSupplier::create, consumer: MultipartBlockStateSupplier.() -> Unit) = data.states.accept(entry.identifier, supplier(entry).apply(consumer))

}