package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.DungeonTableBlockEntity
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.block.*
import net.minecraft.block.entity.MobSpawnerBlockEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.SpawnEggItem
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.BOTTOM
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import kotlin.random.asKotlinRandom

class DungeonTableBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(BOTTOM, false).with(ENTITY, DungeonTableEntity.SKELETON)
    }

    //TODO maybe lava ember particle effects and zombie infection sounds

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(BOTTOM, ENTITY).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) BOTTOM_OUTLINE else TOP_OUTLINE

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) VoxelShapes.fullCube() else SHAPE

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = if (state[BOTTOM]) VoxelShapes.fullCube() else SHAPE

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) VoxelShapes.fullCube() else SHAPE

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = if (state[BOTTOM]) DungeonTableBlockEntity(pos, state) else null

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        val item = stack?.item
        if (item is SpawnEggItem) {
            val entity = DungeonTableEntity.from(item.getEntityType(stack.tag)) ?: return ActionResult.FAIL
            world.setBlockState(pos, state.with(ENTITY, entity), 18)
            if (state[BOTTOM]) {
                world.setBlockState(pos.up(), state.with(BOTTOM, false).with(ENTITY, entity), 18)
            } else {
                world.setBlockState(pos.down(), state.with(BOTTOM, true).with(ENTITY, entity), 18)
            }
            if (player.isSurvival) stack.decrement(1)
            return ActionResult.CONSUME
        } else if (!world.isClient) {
            if (state[BOTTOM]) {
                player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
            } else {
                val down = pos.down()
                if (world.getBlockEntity(down) !is DungeonTableBlockEntity) return ActionResult.SUCCESS
                player.openHandledScreen(state.createScreenHandlerFactory(world, down) ?: return ActionResult.SUCCESS)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val down = ctx.blockPos.down()
        val state2 = ctx.world.getBlockState(down)
        if (state2.isOf(Blocks.SPAWNER)) {
            val be = ctx.world.getBlockEntity(down) as? MobSpawnerBlockEntity ?: return null
            val tag = be.logic.writeNbt(ctx.world, down, NbtCompound()).getCompound("SpawnData")
            return try {
                val entity = DungeonTableEntity.fromOr(Registry.ENTITY_TYPE[Identifier(tag.getString("id"))])
                defaultState.with(BOTTOM, false).with(ENTITY, entity)
            } catch (e: InvalidIdentifierException) {
                null
            }
        } else if (ctx.player?.isSurvival == false) {
            if (state2.isOf(this)) {
                return defaultState.with(BOTTOM, true).with(ENTITY, state2[ENTITY])
            }
            val entity = DungeonTableEntity.values().random(ctx.world.random.asKotlinRandom())
            val up = ctx.blockPos.up()
            if (ctx.world.getBlockState(up).isAir) {
                return defaultState.with(BOTTOM, true).with(ENTITY, entity)
            }
        }
        return null
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (state[BOTTOM]) {
            val up = pos.up()
            world.setBlockState(up, state.with(BOTTOM, false))
            if (itemStack.hasCustomName()) (world.getBlockEntity(pos) as? DungeonTableBlockEntity)?.customName = itemStack.name
        } else {
            val down = pos.down()
            val state2 = world.getBlockState(down)
            if (state2.isOf(Blocks.SPAWNER)) {
                world.setBlockState(down, state.with(BOTTOM, true))
                if (itemStack.hasCustomName()) (world.getBlockEntity(down) as? DungeonTableBlockEntity)?.customName = itemStack.name
            }
        }
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, fromState: BlockState, world: WorldAccess, pos: BlockPos, fromPos: BlockPos): BlockState {
        return if (state[BOTTOM] && direction == Direction.UP && fromState != state.with(BOTTOM, false)) {
            Blocks.AIR.defaultState
        } else if (!state[BOTTOM] && direction == Direction.DOWN && fromState != state.with(BOTTOM, true)) {
            Blocks.AIR.defaultState
        } else {
            state
        }
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? DungeonTableBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = state[BOTTOM]

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        if (!state[BOTTOM]) return 0
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    companion object {
        val SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)

        val BOTTOM_OUTLINE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 20.0, 16.0)
        val TOP_OUTLINE = Block.createCuboidShape(0.0, -16.0, 0.0, 16.0, 4.0, 16.0)

        val ENTITY = EnumProperty.of("entity", DungeonTableEntity::class.java)

        enum class DungeonTableEntity(vararg val types: EntityType<*>) : StringIdentifiable {
            ZOMBIE(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.DROWNED),
            SKELETON(EntityType.SKELETON, EntityType.STRAY),
            SPIDER(EntityType.SPIDER),
            CAVE_SPIDER(EntityType.CAVE_SPIDER),
            SILVERFISH(EntityType.SILVERFISH),
            BLAZE(EntityType.BLAZE),
            MAGMA_CUBE(EntityType.MAGMA_CUBE);

            override fun asString() = name.toLowerCase()

            companion object {
                fun from(type: EntityType<*>) = values().firstOrNull { it.types.contains(type) }
                fun fromOr(type: EntityType<*>, default: DungeonTableEntity = SKELETON) = from(type) ?: default
            }
        }
    }

}
