package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.asset.AssetFactory
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.MultipartBlockStateSupplier
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.VariantsBlockStateSupplier
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface BlockAssetFactory : BlockDataFactory, AssetFactory<Block> {

    override val defaultFolder get() = "block"

    override fun registry(obj: Block) = Registry.BLOCK.getId(obj)

    fun stateOne(data: DataAccessor, entry: Block, model: () -> Identifier) = stateVariantModel(data, entry, model) {}

    fun stateVariant(data: DataAccessor, entry: Block, supplier: (entry: Block) -> VariantsBlockStateSupplier = VariantsBlockStateSupplier::create, consumer: VariantsBlockStateSupplier.() -> Unit) = data.modelStates.addState(entry, supplier(entry).apply(consumer))

    fun stateVariantModel(data: DataAccessor, entry: Block, model: () -> Identifier, consumer: VariantsBlockStateSupplier.() -> Unit) = stateVariant(data, entry, { VariantsBlockStateSupplier.create(entry, BlockStateVariant.create().put(VariantSettings.MODEL, model())) }, consumer)

    fun stateMultipart(data: DataAccessor, entry: Block, supplier: (entry: Block) -> MultipartBlockStateSupplier = MultipartBlockStateSupplier::create, consumer: MultipartBlockStateSupplier.() -> Unit) = data.modelStates.addState(entry, supplier(entry).apply(consumer))

}