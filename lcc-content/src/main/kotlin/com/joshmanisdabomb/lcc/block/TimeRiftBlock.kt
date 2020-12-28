package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.TimeRiftBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class TimeRiftBlock(settings: Settings) : BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = TimeRiftBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (world.isClient) checkType(type, LCCBlockEntities.time_rift, TimeRiftBlockEntity::clientTick) else checkType(type, LCCBlockEntities.time_rift, TimeRiftBlockEntity::serverTick)

    override fun isTranslucent(state: BlockState, world: BlockView, pos: BlockPos) = true

    override fun canMobSpawnInside() = false

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.empty()

    override fun getRaycastShape(state: BlockState, world: BlockView, pos: BlockPos) = VoxelShapes.fullCube()

}