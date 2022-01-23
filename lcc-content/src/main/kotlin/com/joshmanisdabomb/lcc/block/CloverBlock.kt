package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.BlockState
import net.minecraft.block.Fertilizable
import net.minecraft.block.FlowerBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*

class CloverBlock(val effect: StatusEffect, val duration: Int, settings: Settings, stewEffect: StatusEffect = effect, stewDuration: Int = duration) : FlowerBlock(stewEffect, stewDuration, settings), LCCBlockTrait, Fertilizable {

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(effect, duration))
    }

    fun spread(world: WorldView, pos: BlockPos, state: BlockState) = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).shuffled().firstOrNull { world.isAir(it) && state.canPlaceAt(world, it) }

    override fun isFertilizable(world: BlockView, pos: BlockPos, state: BlockState, isClient: Boolean): Boolean {
        return spread(world as? WorldView ?: return false, pos, state) != null
    }

    override fun canGrow(world: World, random: Random, pos: BlockPos, state: BlockState) = true

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        val newPos = spread(world, pos, state) ?: return
        val four = random.nextInt(300.div((this == LCCBlocks.four_leaf_clover).transformInt(2, 1))) == 0
        world.setBlockState(newPos, (if (four) LCCBlocks.four_leaf_clover else LCCBlocks.three_leaf_clover).defaultState, 3)
    }

}