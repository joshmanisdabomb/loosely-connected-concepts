package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.horizontalFacePlacement
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.World
import net.minecraft.world.WorldView
import net.minecraft.world.event.GameEvent

abstract class AbstractTreetapBlock(settings: Settings) : HorizontalBlock(settings) {

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        for (i in -1..1 step 2) {
            val state2 = world.getBlockState(pos.add(0, i, 0))
            if (state2.block is AbstractTreetapBlock && state2[HORIZONTAL_FACING] == state[HORIZONTAL_FACING]) return false
        }
        val pos2 = pos.offset(state[HORIZONTAL_FACING].opposite)
        if (world.getBlockState(pos2).block !is SapLogBlock) return false
        horizontalDirections.filter { it != state[HORIZONTAL_FACING] }.forEach {
            val state3 = world.getBlockState(pos2.offset(it))
            if (state3.block is AbstractTreetapBlock && state3[HORIZONTAL_FACING] == it) return false
        }
        return true
    }

    override fun getPlacementState(context: ItemPlacementContext) = horizontalFacePlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (canPlaceAt(state, world, pos)) return
        world.breakBlock(pos, true)
    }

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val direction = state[HORIZONTAL_FACING]
        val log = world.getBlockState(pos.offset(direction.opposite))
        val block = log.block as? SapLogBlock ?: return
        if (!log[direction.booleanProperty]) return
        val liquid = getLiquidFromState(state)
        if (liquid != null && liquid != block.liquid) return
        val progress = block.liquid.progress
        if (progress > 1 && world.random.nextInt(progress) != 0) return
        incrementLiquidLevel(state, world, pos, block.liquid)?.also {
            world.setBlockState(pos, it)
        }
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        val liquid = getLiquidFromState(state) ?: return ActionResult.PASS
        if (stack.item != liquid.bottle) return ActionResult.PASS
        decrementLiquidLevel(state, world, pos)?.also {
            stack.decrement(1)
            world.setBlockState(pos, it)
            if (stack.isEmpty) {
                player.setStackInHand(hand, liquid.bottled.copy())
            } else if (!player.inventory.insertStack(liquid.bottled.copy())) {
                player.dropItem(liquid.bottled.copy(), false)
            }
            world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos)
            world.playSound(player, player.x, player.y, player.z, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f)
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    abstract fun incrementLiquidLevel(state: BlockState, world: World, pos: BlockPos, liquid: TreetapLiquid): BlockState?
    abstract fun decrementLiquidLevel(state: BlockState, world: World, pos: BlockPos): BlockState?

    abstract fun getLiquidFromState(state: BlockState): TreetapLiquid?
    abstract fun getContainerFromState(state: BlockState): TreetapContainer?

    enum class TreetapLiquid(bottled: () -> ItemStack, val progress: Int, bottle: () -> Item = { Items.GLASS_BOTTLE }, dryProduct: (() -> ItemStack)? = null, val dryAge: Int = 0) : StringIdentifiable {
        LATEX({ LCCItems.latex_bottle.defaultStack }, 6, dryProduct = { LCCItems.flexible_rubber.defaultStack }, dryAge = 3),
        VIVID_SAP({ Items.AIR.defaultStack }, 10, dryProduct = { Items.AIR.defaultStack }, dryAge = 0);

        val bottle by lazy(bottle)
        val bottled by lazy(bottled)
        val dryProduct by lazy { dryProduct?.invoke() }
        val canDry = dryProduct != null

        override fun asString() = name.toLowerCase()
    }

    enum class TreetapContainer(val amount: Int, item: () -> Item, shape: VoxelShape, storage: (() -> Block)? = null) : StringIdentifiable {
        BOWL(3, { Items.BOWL }, createCuboidShape(3.5, 8.0, 7.0, 12.5, 16.0, 16.0), { LCCBlocks.treetap_bowl }),
        BUCKET(5, { Items.BUCKET }, VoxelShapes.empty(), { LCCBlocks.treetap_bowl });

        val shape = shape.rotatable(Direction.NORTH)
        val storage by lazy { storage?.invoke() as? TreetapStorageBlock }
        val item by lazy(item)

        override fun asString() = name.toLowerCase()
    }

}