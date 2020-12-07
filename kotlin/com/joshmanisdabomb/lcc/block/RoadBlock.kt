package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.concepts.color.AlternateDyeColor
import com.joshmanisdabomb.lcc.concepts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.isHorizontal
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.SideShapeType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.AxeItem
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class RoadBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(SHAPE, RoadShape.PATH).with(MARKINGS, RoadMarkings.NONE).with(INNER, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(SHAPE, MARKINGS, INNER) }

    override fun hasSidedTransparency(state: BlockState)= state.get(SHAPE) != RoadShape.FULL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = when (state.get(SHAPE)) {
        RoadShape.HALF -> HALF_SHAPE
        RoadShape.PATH, null -> PATH_SHAPE
        RoadShape.FULL -> VoxelShapes.fullCube()
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        val vec = fromPos.subtract(pos)
        val state2 = this.getStateForNeighborUpdate(state, Direction.fromVector(vec.x, vec.y, vec.z) ?: return, state, world, pos, fromPos)
        if (state2 != state) world.setBlockState(pos, state2)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (state.get(SHAPE) == RoadShape.FULL) return ActionResult.PASS
        val stack = player.getStackInHand(hand)
        val item = stack?.item ?: ActionResult.PASS
        if (state.get(MARKINGS) != RoadMarkings.NONE && item is AxeItem) {
            //TODO horrible custom scrape sound effect
            world.setBlockState(pos, state.with(MARKINGS, RoadMarkings.NONE))
            updateRoads(world, pos)
            stack.damage(1, player, { it.sendToolBreakStatus(hand) })
            return ActionResult.SUCCESS
        }
        return RoadMarkings.values().firstOrNull { state.get(MARKINGS) != it && item == it.color?.lcc_dye }?.run { world.setBlockState(pos, state.with(MARKINGS, this)); updateRoads(world, pos); ActionResult.SUCCESS } ?: ActionResult.PASS
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        var state2 = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
        if (state.get(SHAPE) != RoadShape.HALF && direction == Direction.UP) {
            val stateUp = world.getBlockState(posFrom)
            state2 = state2.with(SHAPE, if (stateUp.isSideSolid(world, posFrom, Direction.DOWN, SideShapeType.FULL)) RoadShape.FULL else RoadShape.PATH)
            if (stateUp.isOf(this) && stateUp.get(SHAPE) == RoadShape.HALF && stateUp.get(MARKINGS) != state.get(MARKINGS)) {
                state2 = state2.with(MARKINGS, stateUp.get(MARKINGS))
            }
        }
        state2 = state2.with(INNER, isInner(world, state, pos))
        return state2
    }

    override fun onSteppedOn(world: World, pos: BlockPos, entity: Entity) {
        entity.velocity = entity.velocity.multiply(1.3, 1.0, 1.3)
    }

    fun connector(world: BlockView, state: BlockState, pos: BlockPos, other: BlockState, otherPos: BlockPos, path: Array<Direction>, inner: Boolean? = null): Boolean {
        if (state.get(SHAPE) == RoadShape.FULL) return false
        if (!other.isOf(this) || other.get(SHAPE) == RoadShape.FULL) {
            if (state.get(SHAPE) == RoadShape.HALF) {
                val other2 = world.getBlockState(otherPos.down())
                if (!other2.isOf(this)) return false
                val m = other2.get(MARKINGS)
                return other2.get(SHAPE) == RoadShape.PATH && (m == state.get(MARKINGS) || m == RoadMarkings.NONE) && (inner?.run { other2.get(INNER) == this } ?: true)
            } else {
                val other2 = world.getBlockState(otherPos.up())
                if (!other2.isOf(this)) return false
                val m = other2.get(MARKINGS)
                return other2.get(SHAPE) == RoadShape.HALF && (m == state.get(MARKINGS) || m == RoadMarkings.NONE) && (inner?.run { other2.get(INNER) == this } ?: true)
            }
        } else {
            val m = other.get(MARKINGS)
            return (m == state.get(MARKINGS) || m == RoadMarkings.NONE) && (inner?.run { other.get(INNER) == this } ?: true)
        }
    }

    fun isInner(world: WorldAccess, state: BlockState, pos: BlockPos) = Direction.values().filter(Direction::isHorizontal).all { val pos2 = pos.offset(it); val pos3 = pos.offset(it).offset(it.rotateYClockwise()); connector(world, state, pos, world.getBlockState(pos2), pos2, arrayOf(it)) && connector(world, state, pos, world.getBlockState(pos3), pos3, arrayOf(it, it.rotateYClockwise())) }

    fun inner(world: WorldAccess, state: BlockState, pos: BlockPos) = state.with(INNER, isInner(world, state, pos))

    fun updateRoads(world: World, pos: BlockPos) {
        if (world.getBlockState(pos).isOf(LCCBlocks.road)) world.updateNeighbors(pos, LCCBlocks.road)
        Direction.values().filter(Direction::isHorizontal).forEach {
            with (pos.offset(it)) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
            with (pos.offset(it).offset(it.rotateYClockwise())) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
            with (pos.offset(it).down()) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
            with (pos.offset(it).down().offset(it.rotateYClockwise())) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
            with (pos.offset(it).up()) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
            with (pos.offset(it).up().offset(it.rotateYClockwise())) { if (world.getBlockState(this).isOf(LCCBlocks.road)) world.updateNeighbors(this, LCCBlocks.road) }
        }
    }

    companion object {
        val SHAPE = EnumProperty.of("shape", RoadShape::class.java)
        val MARKINGS = EnumProperty.of("markings", RoadMarkings::class.java)
        val INNER = BooleanProperty.of("inner")

        val HALF_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0)
        val PATH_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)

        enum class RoadShape : StringIdentifiable {
            FULL,
            PATH,
            HALF;

            override fun asString() = name.toLowerCase()
        }

        enum class RoadMarkings(val color: LCCExtendedDyeColor? = null) : StringIdentifiable {
            NONE,
            WHITE(DyeColor.WHITE),
            ORANGE(DyeColor.ORANGE),
            MAGENTA(DyeColor.MAGENTA),
            LIGHT_BLUE(DyeColor.LIGHT_BLUE),
            YELLOW(DyeColor.YELLOW),
            LIME(DyeColor.LIME),
            PINK(DyeColor.PINK),
            GRAY(DyeColor.GRAY),
            LIGHT_GRAY(DyeColor.LIGHT_GRAY),
            CYAN(DyeColor.CYAN),
            PURPLE(DyeColor.PURPLE),
            BLUE(DyeColor.BLUE),
            BROWN(DyeColor.BROWN),
            GREEN(DyeColor.GREEN),
            RED(DyeColor.RED),
            BLACK(DyeColor.BLACK),
            CINNABAR(AlternateDyeColor.CINNABAR),
            MAROON(AlternateDyeColor.MAROON),
            BRICK(AlternateDyeColor.BRICK),
            TAN(AlternateDyeColor.TAN),
            GOLD(AlternateDyeColor.GOLD),
            LIGHT_GREEN(AlternateDyeColor.LIGHT_GREEN),
            MINT(AlternateDyeColor.MINT),
            TURQUOISE(AlternateDyeColor.TURQUOISE),
            NAVY(AlternateDyeColor.NAVY),
            INDIGO(AlternateDyeColor.INDIGO),
            LAVENDER(AlternateDyeColor.LAVENDER),
            LIGHT_PURPLE(AlternateDyeColor.LIGHT_PURPLE),
            HOT_PINK(AlternateDyeColor.HOT_PINK),
            BURGUNDY(AlternateDyeColor.BURGUNDY),
            ROSE(AlternateDyeColor.ROSE),
            DARK_GRAY(AlternateDyeColor.DARK_GRAY);

            constructor(color: DyeColor) : this(color as LCCExtendedDyeColor)

            override fun asString() = name.toLowerCase()

            fun suffix(prefix: String = "") = when (this) {
                NONE -> ""
                else -> "_$prefix${this.asString()}"
            }
        }
    }

}