package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.ChestType
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.BlockStateVariantMap
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object ClassicChestBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.CHEST_TYPE).register { facing, chest ->
                BlockStateVariant.create().put(VariantSettings.MODEL, modelCube(data, entry, suffix(id, chest.asString().toLowerCase()),
                    textureUp = suffix(id, "top"),
                    textureDown = suffix(id, "top"),
                    textureNorth = suffix(id, (if (chest != ChestType.SINGLE) "${chest.opposite.asString().toLowerCase()}_" else "") + "front"),
                    textureSouth = suffix(id, if (chest != ChestType.SINGLE) "${chest.asString().toLowerCase()}_back" else "side"),
                    textureEast = suffix(id, "side"),
                    textureWest = suffix(id, "side"),
                    textureParticle = suffix(id, "front")
                )).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![facing]!!)
            })
        }
    }

}
