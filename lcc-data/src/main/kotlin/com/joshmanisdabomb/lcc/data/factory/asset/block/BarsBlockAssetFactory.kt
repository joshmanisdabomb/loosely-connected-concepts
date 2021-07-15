package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Block
import net.minecraft.data.client.model.BlockStateVariant
import net.minecraft.data.client.model.VariantSettings
import net.minecraft.data.client.model.When
import net.minecraft.util.math.Direction

class BarsBlockAssetFactory(val post: ModelProvider.ModelFactory<Block>, val post_ends: ModelProvider.ModelFactory<Block>, val cap: ModelProvider.ModelFactory<Block>, val cap_alt: ModelProvider.ModelFactory<Block>, val side: ModelProvider.ModelFactory<Block>, val side_alt: ModelProvider.ModelFactory<Block>) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val post = post.create(data, entry) { idh.locSuffix(it, "post") }
        val post_ends = post_ends.create(data, entry) { idh.locSuffix(it, "post_ends") }
        val cap = cap.create(data, entry) { idh.locSuffix(it, "cap") }
        val cap_alt = cap_alt.create(data, entry) { idh.locSuffix(it, "cap_alt") }
        val side = side.create(data, entry) { idh.locSuffix(it, "side") }
        val side_alt = side_alt.create(data, entry) { idh.locSuffix(it, "side_alt") }
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, post_ends))
            with(When.create().apply { Direction.Type.HORIZONTAL.forEach { set(it.booleanProperty, false) } }, BlockStateVariant.create().put(VariantSettings.MODEL, post))
            for (d in Direction.Type.HORIZONTAL) {
                val northSouth = d.axis == Direction.Axis.Z
                val northEast = d == Direction.NORTH || d == Direction.EAST
                val rot = northSouth.transform(Direction.NORTH, Direction.EAST)

                with(When.create().apply { Direction.Type.HORIZONTAL.forEach { set(it.booleanProperty, it == d) } }, BlockStateVariant.create().put(VariantSettings.MODEL, northEast.transform(cap, cap_alt)).apply(ModelProvider.horizontalRotation(rot)))
                with(When.create().set(d.booleanProperty, true), BlockStateVariant.create().put(VariantSettings.MODEL, northEast.transform(side, side_alt)).apply(ModelProvider.horizontalRotation(rot)))
            }
        }
    }

}