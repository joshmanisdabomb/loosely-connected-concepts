package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.DungeonTableBlockEntity
import net.minecraft.block.*
import net.minecraft.block.entity.MobSpawnerBlockEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.BOTTOM
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView


class DungeonTableBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(BOTTOM, false).with(ENTITY, DungeonTableEntity.SKELETON)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(BOTTOM, ENTITY).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state.get(BOTTOM)) VoxelShapes.fullCube() else SHAPE

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state.get(BOTTOM)) VoxelShapes.fullCube() else SHAPE

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = if (state.get(BOTTOM)) VoxelShapes.fullCube() else SHAPE

    override fun getVisualShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state.get(BOTTOM)) VoxelShapes.fullCube() else SHAPE

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = if (state.get(BOTTOM)) DungeonTableBlockEntity(pos, state) else null

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) {
            if (state.get(BOTTOM)) {
                player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
            } else {
                val down = pos.down()
                if (world.getBlockEntity(down) !is DungeonTableBlockEntity) return ActionResult.SUCCESS
                player.openHandledScreen(state.createScreenHandlerFactory(world, down) ?: return ActionResult.SUCCESS)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val down = pos.down()
        val state2 = world.getBlockState(down)
        return state2.isOf(Blocks.SPAWNER) || world.getBlockEntity(down) is DungeonTableBlockEntity
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val down = ctx.blockPos.down()
        val state2 = ctx.world.getBlockState(down)
        if (state2.isOf(this)) {
            return defaultState.with(BOTTOM, false).with(ENTITY, state2.get(ENTITY))
        } else if (state2.isOf(Blocks.SPAWNER)) {
            val be = ctx.world.getBlockEntity(down) as? MobSpawnerBlockEntity ?: return null
            val tag = be.logic.toTag(ctx.world, down, CompoundTag()).getCompound("SpawnData")
            return try {
                val entity = DungeonTableEntity.fromOr(Registry.ENTITY_TYPE.get(Identifier(tag.getString("id"))))
                defaultState.with(BOTTOM, false).with(ENTITY, entity)
            } catch (e: InvalidIdentifierException) {
                null
            }
        } else {
            return null
        }
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (itemStack.hasCustomName()) {
            val blockEntity = world.getBlockEntity(pos) as? DungeonTableBlockEntity ?: return
            blockEntity.customName = itemStack.name
        }
        val down = pos.down()
        val state2 = world.getBlockState(down)
        if (state2.isOf(Blocks.SPAWNER)) {
            world.setBlockState(down, defaultState.with(BOTTOM, true).with(ENTITY, state.get(ENTITY)))
        }
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (state != newState) {
            ItemScatterer.spawn(world, pos, world.getBlockEntity(pos) as? DungeonTableBlockEntity ?: return super.onStateReplaced(state, world, pos, newState, moved))
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    companion object {
        val SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)

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
