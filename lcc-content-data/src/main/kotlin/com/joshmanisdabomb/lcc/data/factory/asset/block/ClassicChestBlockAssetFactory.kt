package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.block.enums.ChestType
import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.BlockStateVariantMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.state.property.Properties

object ClassicChestBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.CHEST_TYPE).register { facing, chest ->
                BlockStateVariant.create().put(VariantSettings.MODEL, models.cube(
                    textureUp = { idh.locSuffix(entry, "top") },
                    textureDown = { idh.locSuffix(entry, "top") },
                    textureNorth = { idh.locSuffix(entry, (if (chest != ChestType.SINGLE) "${chest.opposite.asString().toLowerCase()}_" else "") + "front") },
                    textureSouth = { idh.locSuffix(entry, if (chest != ChestType.SINGLE) "${chest.asString().toLowerCase()}_back" else "side") },
                    textureEast = { idh.locSuffix(entry, "side") },
                    textureWest = { idh.locSuffix(entry, "side") },
                    textureParticle = { idh.locSuffix(entry, "front") }
                ).create(data, entry) { idh.locSuffix(entry, chest.asString().toLowerCase()) }).apply(ModelProvider.horizontalRotation(facing))
            })
        }
    }

}
