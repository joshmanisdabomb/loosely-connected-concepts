package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.block.RoadBlock.Companion.INNER
import com.joshmanisdabomb.lcc.block.RoadBlock.Companion.SHAPE
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f
import net.minecraft.world.BlockRenderView
import net.minecraft.world.BlockView
import java.util.*
import java.util.function.Supplier

class RoadModel(markings: RoadBlock.Companion.RoadMarkings, inner: Boolean, height: Float) : ConnectedTextureModel(LCC.id("road"), connector = if (inner) ::innerConnector else ::connector, innerSeams = false, borderSize = if (inner) 7 else 4, pos2 = Vec3f(1.0f, height, 1.0f), mapConsumer = { if (inner) innerMap(this, markings) else map(this, markings) }) {

    override fun emitBlockQuads(renderView: BlockRenderView, state: BlockState, pos: BlockPos, random: Supplier<Random>, renderContext: RenderContext) {
        Direction.values().forEach {
            if (state.get(SHAPE) == RoadBlock.Companion.RoadShape.FULL) {
                val pos2 = pos.up()
                val state2 = renderView.getBlockState(pos2)
                if (state2.isOf(LCCBlocks.road) && state2.get(SHAPE) == RoadBlock.Companion.RoadShape.HALF) {
                    faceConnections.reload(it, renderView, renderView.getBlockState(pos2), pos2)
                } else {
                    faceConnections.reload(it, renderView, state, pos)
                }
            } else {
                faceConnections.reload(it, renderView, state, pos)
            }

            if (!state.get(INNER) && it.isHorizontal && faceConnections.front) {
                emitFace(it, renderContext, map.getSide(it, faceConnections.frontLeft && faceConnections.left, true, 1), pos1, pos2) {
                    it[2] = it[2].coerceAtMost(it[0] + 0.25f)
                }
                emitFace(it, renderContext, map.getSide(it, faceConnections.frontRight && faceConnections.right, true, 1), pos1, pos2) {
                    it[0] = it[0].coerceAtLeast(it[2] - 0.25f)
                }
                emitFace(it, renderContext, map.getBase(it, 1), pos1, pos2) {
                    it[0] += 0.25f
                    it[2] -= 0.25f
                }
            } else {
                emitConnectedFace(it, renderContext)
            }
        }
    }

    companion object {
        private fun connector(world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) = LCCBlocks.road.connector(world, state, pos, other, otherPos, path)
        private fun innerConnector(world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>) = LCCBlocks.road.connector(world, state, pos, other, otherPos, path, true)

        private fun map(map: ConnectedTextureMap, markings: RoadBlock.Companion.RoadMarkings) = map.top(LCC.id("road${markings.suffix()}")).sides(LCC.id("road${markings.suffix()}")) {it == 1}
        private fun innerMap(map: ConnectedTextureMap, markings: RoadBlock.Companion.RoadMarkings) = map.top(LCC.id("road${markings.suffix("inner_")}"))
    }

}