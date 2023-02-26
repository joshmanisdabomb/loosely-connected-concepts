package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RainbowGateBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.absoluteValue

class RainbowGateBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(type, RainbowGateState.INCOMPLETE).with(symbol, 1)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(type, symbol).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = RainbowGateBlockEntity(pos, state)

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (state[type] != RainbowGateState.INCOMPLETE) return
        val portal = portalPositions(world, pos)
        if (portal.isNotEmpty()) {
            world.setBlockState(pos, state.with(type, RainbowGateState.MAIN))
            (world.getBlockEntity(pos) as? RainbowGateBlockEntity)?.main(portal)
            portal.forEach {
                world.setBlockState(it, world.getBlockState(it).with(type, RainbowGateState.COMPLETE))
                (world.getBlockEntity(it) as? RainbowGateBlockEntity)?.secondary(pos)
            }
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val random = MathHelper.hashCode(ctx.blockPos.x, ctx.blockPos.y, ctx.blockPos.z).absoluteValue.mod(8).plus(1)
        return defaultState.with(symbol, random)
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block) || newState[type] == RainbowGateState.INCOMPLETE) {
            val portal = (world.getBlockEntity(pos) as? RainbowGateBlockEntity)?.reset()
            portal?.forEach {
                if (it == pos) return@forEach
                val state2 = world.getBlockState(it)
                if (state2.isOf(this)) {
                    world.setBlockState(it, state2.with(type, RainbowGateState.INCOMPLETE))
                }
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    private fun portalPositions(world: World, pos: BlockPos, vararg types: RainbowGateState = arrayOf(RainbowGateState.INCOMPLETE)): List<BlockPos> {
        val positions = mutableListOf<BlockPos>()
        for (i in -2..2) {
            if (i == 0) continue
            val pos2 = pos.up(i)
            val state2 = world.getBlockState(pos2)
            if (!state2.isOf(this) || !types.contains(state2[type])) {
                positions.clear()
                continue
            }
            positions.add(pos2)
            if (positions.count() >= 2) break
        }
        if (positions.count() < 2) return emptyList()
        val positions2 = mutableListOf<BlockPos>()
        for (d in horizontalDirections) {
            positions2.clear()
            for (i in -2..2) {
                val pos2 = pos.offset(d, 4).up(i)
                val state2 = world.getBlockState(pos2)
                if (!state2.isOf(this) || !types.contains(state2[type])) {
                    positions2.clear()
                    continue
                }
                positions2.add(pos2)
                if (positions2.count() >= 3) return positions.take(2) + positions2.take(3)
            }
        }
        return emptyList()
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (player.isSneaking) {
            if (state[symbol] == 1) world.setBlockState(pos, state.with(symbol, symbol.values.maxOrNull()!!))
            else world.setBlockState(pos, state.with(symbol, state[symbol] - 1))
        } else {
            world.setBlockState(pos, state.cycle(symbol))
        }
        return ActionResult.SUCCESS
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (world.isClient) null else checkType(type, LCCBlockEntities.rainbow_gate, RainbowGateBlockEntity::serverTick)

    override fun <T : BlockEntity?> getGameEventListener(world: ServerWorld, entity: T) = entity as? RainbowGateBlockEntity

    companion object {
        val type = EnumProperty.of("state", RainbowGateState::class.java)
        val symbol = IntProperty.of("symbol", 1, 8)

        val shape = createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0)
    }

    enum class RainbowGateState() : StringIdentifiable {
        INCOMPLETE,
        COMPLETE,
        MAIN;

        override fun asString() = name.lowercase()
    }

}
