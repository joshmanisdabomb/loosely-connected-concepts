package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.block.enums.WallMountLocation
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier

class ButtonBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val button = Models.BUTTON.upload(idh.loc(entry), Texture.texture(texture ?: idh.loc(entry)), data.modelStates::addModel)
        val buttonDown = Models.BUTTON_PRESSED.upload(idh.locSuffix(entry, "down"), Texture.texture(texture ?: idh.loc(entry)), data.modelStates::addModel)

        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(Properties.WALL_MOUNT_LOCATION, Properties.HORIZONTAL_FACING, Properties.POWERED).register { w, f, p ->
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, if (p) buttonDown else button)
                    .put(VariantSettings.X, VariantSettings.Rotation.values()[w.ordinal])
                    .put(VariantSettings.Y, VariantSettings.Rotation.values()[f.opposite.horizontal])
                    .put(VariantSettings.UVLOCK, w == WallMountLocation.WALL)
        }) }
    }

}
