package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.ComputerCableBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.util.math.Direction

object ComputerCableBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val cable = entry as? ComputerCableBlock ?: return
        val center = LCCModelTemplates.cable4_center.upload(idh.loc(entry), TextureMap().put(TextureKey.END, idh.locSuffix(entry, "end")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
        val texture = TextureMap().put(TextureKey.SIDE, idh.loc(entry)).put(TextureKey.END, idh.locSuffix(entry, "end")).put(TextureKey.PARTICLE, idh.loc(entry))
        val connection = LCCModelTemplates.cable4_connection.upload(idh.locSuffix(entry, "connection"), texture, data.models)
        val elbow = LCCModelTemplates.template_computer_cable_elbow.upload(idh.locSuffix(entry, "elbow"), texture, data.models)
        val top = LCCModelTemplates.template_computer_cable_top.upload(idh.locSuffix(entry, "top"), texture, data.models)
        val bottom = LCCModelTemplates.template_computer_cable_bottom.upload(idh.locSuffix(entry, "bottom"), texture, data.models)

        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, center))
            with(When.create().set(cable.topProperty, true), BlockStateVariant().put(VariantSettings.MODEL, connection).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(Direction.UP)))
            with(When.create().set(cable.bottomProperty, true), BlockStateVariant().put(VariantSettings.MODEL, connection).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(Direction.DOWN)))
            horizontalDirections.forEach {
                with(When.create().set(cable.sideProperties[it], ComputerCableBlock.ComputerCableConnection.FULL), BlockStateVariant().put(VariantSettings.MODEL, connection).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(it)))
                with(When.create().set(cable.sideProperties[it], ComputerCableBlock.ComputerCableConnection.TOP), BlockStateVariant().put(VariantSettings.MODEL, top).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(it)))
                with(When.create().set(cable.sideProperties[it], ComputerCableBlock.ComputerCableConnection.BOTTOM), BlockStateVariant().put(VariantSettings.MODEL, bottom).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(it)))
            }
            with(When.allOf(When.create().set(cable.topProperty, false), When.anyOf(*cable.sideProperties.values.map { When.create().set(it, ComputerCableBlock.ComputerCableConnection.TOP) }.toTypedArray())), BlockStateVariant().put(VariantSettings.MODEL, elbow).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(Direction.UP)))
            with(When.allOf(When.create().set(cable.bottomProperty, false), When.anyOf(*cable.sideProperties.values.map { When.create().set(it, ComputerCableBlock.ComputerCableConnection.BOTTOM) }.toTypedArray())), BlockStateVariant().put(VariantSettings.MODEL, elbow).put(VariantSettings.UVLOCK, true).apply(ModelProvider.directionalRotation(Direction.DOWN)))
        }
    }

}
