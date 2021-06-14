package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.PapercombBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.entity.WaspEntity
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.FireBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.TntEntity
import net.minecraft.entity.boss.WitherEntity
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.WitherSkullEntity
import net.minecraft.entity.vehicle.TntMinecartEntity
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class PapercombBlock(settings: Settings) : BlockWithEntity(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = PapercombBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun afterBreak(world: World, player: PlayerEntity, pos: BlockPos, state: BlockState, be: BlockEntity?, stack: ItemStack) {
        super.afterBreak(world, player, pos, state, be, stack)
        if (world.isClient || be !is PapercombBlockEntity) return
        be.emergency(player)
        WaspEntity.angerNearby(world, pos, player, 32)
    }

    override fun getDroppedStacks(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack> {
        val entity = builder.getNullable(LootContextParameters.THIS_ENTITY)
        when (entity) {
            is TntEntity, is CreeperEntity, is WitherSkullEntity, is WitherEntity, is TntMinecartEntity -> {
                val be = builder.getNullable(LootContextParameters.BLOCK_ENTITY) as? PapercombBlockEntity
                be?.emergency(null)
            }
        }
        return super.getDroppedStacks(state, builder)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (world.getBlockState(neighborPos).block is FireBlock) {
            val be = world.getBlockEntity(pos) as? PapercombBlockEntity
            be?.emergency(null)
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.papercomb, PapercombBlockEntity::tick) else null

}