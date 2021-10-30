package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.modify
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class GlassPaneBlockAssetFactory(val top: (entry: Block) -> Identifier? = { null }) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val texture = Texture().put(TextureKey.PANE, idh.loc(entry).modify { it.replace("_pane", "") }).put(TextureKey.EDGE, top(entry) ?: idh.locSuffix(entry, "top"))
        val post = Models.TEMPLATE_GLASS_PANE_POST.upload(idh.locSuffix(entry, "post"), texture, data.models)
        val side = Models.TEMPLATE_GLASS_PANE_SIDE.upload(idh.locSuffix(entry, "side"), texture, data.models)
        val side_alt = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(idh.locSuffix(entry, "side_alt"), texture, data.models)
        val noside = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(idh.locSuffix(entry, "noside"), texture, data.models)
        val noside_alt = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(idh.locSuffix(entry, "noside_alt"), texture, data.models)
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
            for (d in Direction.Type.HORIZONTAL) {
                val northSouth = d.axis == Direction.Axis.Z
                val northEast = d == Direction.NORTH || d == Direction.EAST
                val northWest = d == Direction.NORTH || d == Direction.WEST
                val rot = northSouth.transform(Direction.NORTH, Direction.EAST)

                with(When.create().set(d.booleanProperty, true), BlockStateVariant.create().put(VariantSettings.MODEL, northEast.transform(side, side_alt)).apply(ModelProvider.horizontalRotation(rot)))
                with(When.create().set(d.booleanProperty, false), BlockStateVariant.create().put(VariantSettings.MODEL, northWest.transform(noside, noside_alt)).put(VariantSettings.Y, when (d) {
                    Direction.SOUTH -> VariantSettings.Rotation.R90
                    Direction.WEST -> VariantSettings.Rotation.R270
                    else -> VariantSettings.Rotation.R0
                }))
            }
        }
    }

    companion object : GlassPaneBlockAssetFactory() {

    }

}