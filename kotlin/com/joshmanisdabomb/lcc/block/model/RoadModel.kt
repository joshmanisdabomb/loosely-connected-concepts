package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import net.minecraft.world.BlockView
import java.util.*
import java.util.function.Supplier

class RoadModel(markings: RoadBlock.Companion.RoadMarkings, inner: Boolean, height: Float) : ConnectedTextureModel("road", connector = if (inner) ::innerConnector else ::connector, innerSeams = false, borderSize = if (inner) 7 else 4, pos2 = Vector3f(1.0f, height, 1.0f), mapConsumer = { if (inner) innerMap(this, markings) else map(this, markings) }) {

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        super.emitBlockQuads(renderView, state, pos, random, renderContext)
    }

    companion object {
        private fun connector(world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) = LCCBlocks.road.connector(world, state, pos, other, otherPos, path)
        private fun innerConnector(world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) = LCCBlocks.road.connector(world, state, pos, other, otherPos, path, true)

        private fun map(map: ConnectedTextureMap, markings: RoadBlock.Companion.RoadMarkings) = map.top("road_${markings.asString()}").sides("road_${markings.asString()}") {it == 1}
        private fun innerMap(map: ConnectedTextureMap, markings: RoadBlock.Companion.RoadMarkings) = map.top("road_inner_${markings.asString()}")
    }

}