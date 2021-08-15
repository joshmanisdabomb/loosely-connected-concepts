package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class SapphireAltarBlock(settings: AbstractBlock.Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(key, false).with(middle, SapphireState.EMPTY).with(tl, SapphireState.EMPTY).with(tr, SapphireState.EMPTY).with(bl, SapphireState.EMPTY).with(br, SapphireState.EMPTY)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, key, middle, tl, tr, bl, br).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = SapphireAltarBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getPlacementState(ctx: ItemPlacementContext) = horizontalPlacement(ctx)

    override fun rotate(state: BlockState, rotation: BlockRotation) = state.with(HORIZONTAL_FACING, rotation.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (stack.isOf(LCCItems.sapphire) || stack.isOf(LCCItems.dull_sapphire)) {
            if (hit.side != Direction.UP && hit.side != state[HORIZONTAL_FACING]) return ActionResult.PASS
            val dull = stack.isOf(LCCItems.dull_sapphire)
            return if (placeSapphire(state, world, pos, hit.pos, dull)) {
                world.playSound(null, hit.pos.x, hit.pos.y, hit.pos.z, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat().times(0.2f).plus(dull.transform(0.5f, 0.9f)))
                ActionResult.SUCCESS
            } else {
                ActionResult.PASS
            }
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    private fun placeSapphire(state: BlockState, world: World, pos: BlockPos, hit: Vec3d, dull: Boolean): Boolean {
        val perp = state[HORIZONTAL_FACING].rotateYClockwise()
        val left = when (perp.axis) {
            Direction.Axis.X -> MathHelper.floorMod(hit.x * perp.offsetX, 1.0) >= 0.5
            else -> MathHelper.floorMod(hit.z * perp.offsetZ, 1.0) >= 0.5
        }

        var property: EnumProperty<SapphireState>? = null
        val y = MathHelper.floorMod(hit.y, 1.0)
        if (y > 11.0.div(16.0)) {
            property = left.transform(tl, tr)
        } else if (y > 10.0.div(16.0)) {
            property = middle
        } else if (y > 9.0.div(16.0)) {
            property = left.transform(bl, br)
        }

        if (property == null) return false
        if (state[property] != SapphireState.EMPTY) return false
        world.setBlockState(pos, state.with(property, dull.transform(SapphireState.DULL, SapphireState.NORMAL)))
        return true
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape[state[HORIZONTAL_FACING]]

    companion object {
        val shape = VoxelShapes.union(
            createCuboidShape(0.0, 0.0, 0.0, 2.0, 6.0, 2.0),
            createCuboidShape(14.0, 0.0, 0.0, 16.0, 6.0, 2.0),
            createCuboidShape(0.0, 0.0, 14.0, 2.0, 8.0, 16.0),
            createCuboidShape(14.0, 0.0, 14.0, 16.0, 8.0, 16.0),
            createCuboidShape(1.0, 0.0, 1.0, 15.0, 10.0, 15.0),
            createCuboidShape(1.0, 10.0, 5.0, 15.0, 11.0, 15.0),
            createCuboidShape(1.0, 11.0, 9.0, 15.0, 12.0, 15.0)
        ).rotatable(Direction.NORTH)

        val key = BooleanProperty.of("key")
        val middle = EnumProperty.of("middle", SapphireState::class.java)
        val tl = EnumProperty.of("tl", SapphireState::class.java)
        val tr = EnumProperty.of("tr", SapphireState::class.java)
        val bl = EnumProperty.of("bl", SapphireState::class.java)
        val br = EnumProperty.of("br", SapphireState::class.java)
    }

    enum class SapphireState : StringIdentifiable {
        EMPTY,
        DULL,
        NORMAL;

        override fun asString() = name.toLowerCase()
    }

}
