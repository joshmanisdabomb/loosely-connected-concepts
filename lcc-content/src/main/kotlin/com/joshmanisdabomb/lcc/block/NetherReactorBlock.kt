package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.NetherReactorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCTags
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class NetherReactorBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(reactor_state, NetherReactorState.READY)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(reactor_state).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NetherReactorBlockEntity(pos, state)

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (state[reactor_state] === NetherReactorState.READY) {
            if (!world.isClient) {
                if (pos.y < world.bottomY + 4 || pos.y >= world.topY - 35) {
                    player.sendMessage(TranslatableText("block.lcc.nether_reactor.y"), true)
                } else if (!this.checkStructure(world, pos)) {
                    player.sendMessage(TranslatableText("block.lcc.nether_reactor.incorrect"), true)
                } else if (!player.isCreative && !this.checkForPlayers(world, pos)) {
                    player.sendMessage(TranslatableText("block.lcc.nether_reactor.players"), true)
                } else {
                    (world.getBlockEntity(pos) as? NetherReactorBlockEntity)?.activate()
                    world.setBlockState(pos, state.with(reactor_state, NetherReactorState.ACTIVE), 3)
                    player.sendMessage(TranslatableText("block.lcc.nether_reactor.active"), true)
                }
            }
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    protected fun checkStructure(world: World, pos: BlockPos?): Boolean {
        val bp = BlockPos.Mutable()
        for (i in -1..1) {
            for (k in -1..1) {
                val bottom = world.getBlockState(bp.set(pos).move(i, -1, k))
                val middle = world.getBlockState(bp.move(Direction.UP))
                val top = world.getBlockState(bp.move(Direction.UP))
                if (i != 0 && k != 0 && (!bottom.isIn(LCCTags.nether_reactor_base) || !middle.isIn(LCCTags.nether_reactor_shell) || !top.isAir)) return false
                if ((i != 0 && k == 0 || i == 0 && k != 0) && (!bottom.isIn(LCCTags.nether_reactor_shell) || !middle.isAir || !top.isIn(LCCTags.nether_reactor_shell))) return false
                if (i == 0 && k == 0 && (!bottom.isIn(LCCTags.nether_reactor_shell) || !top.isIn(LCCTags.nether_reactor_shell))) return false
            }
        }
        return true
    }

    protected fun checkForPlayers(world: World, pos: BlockPos): Boolean {
        val range = Box(pos.down(), pos.up(2)).expand(9.0, 0.0, 9.0)

        //TODO: config option, big servers won't feasibly get everyone together
        return world.server!!.playerManager.playerList.stream().allMatch { player -> player.world.dimension === world.dimension && range.intersects(player.boundingBox) }
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (world.isClient) checkType(type, LCCBlockEntities.nether_reactor, NetherReactorBlockEntity::tick) else checkType(type, LCCBlockEntities.nether_reactor, NetherReactorBlockEntity::tick)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (itemStack.hasCustomName()) (world.getBlockEntity(pos) as? NetherReactorBlockEntity)?.customName = itemStack.name
    }

    enum class NetherReactorState : StringIdentifiable {

        READY,
        ACTIVE,
        USED;

        override fun asString() = name.toLowerCase()

    }

    companion object {

        val reactor_state = EnumProperty.of("state", NetherReactorState::class.java)

        fun getMapColor(state: BlockState) = when (state[reactor_state]) {
            NetherReactorState.ACTIVE -> MapColor.BRIGHT_RED;
            NetherReactorState.USED -> MapColor.BLACK;
            else -> MapColor.CYAN;
        }

    }

}