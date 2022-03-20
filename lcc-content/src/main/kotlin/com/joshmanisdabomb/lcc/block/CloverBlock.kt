package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*

class CloverBlock(val effect: StatusEffect, val duration: Int, settings: Settings, stewEffect: StatusEffect = effect, stewDuration: Int = duration) : FlowerBlock(stewEffect, stewDuration, settings), LCCBlockTrait, Fertilizable {

    val fourLeafChance = 3000 / (this.effect == StatusEffects.LUCK).transformInt(4, 1)

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(effect, duration))
    }

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (random.nextInt(25) == 0) grow(world, random, pos, state)
    }

    fun canGrowAtBlock(world: WorldView, pos: BlockPos, state: BlockState): Boolean {
        if (!state.canPlaceAt(world, pos)) return false
        val other = world.getBlockState(pos)
        if (other.isAir) return true
        return when (other.block) {
            is CloverBlock, is SweetBerryBushBlock, is AzaleaBlock, is DeadBushBlock -> false
            is PlantBlock -> true
            else -> false
        }
    }

    override fun isFertilizable(world: BlockView, pos: BlockPos, state: BlockState, isClient: Boolean): Boolean {
        return BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).any { canGrowAtBlock(world as? WorldView ?: return false, it, state) }
    }

    override fun canGrow(world: World, random: Random, pos: BlockPos, state: BlockState) = true

    override fun grow(world: ServerWorld, random: Random, pos: BlockPos, state: BlockState) {
        val newPos = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).map(BlockPos::toImmutable).shuffled().firstOrNull { canGrowAtBlock(world, it, state) } ?: return
        val four = random.nextInt(fourLeafChance) == 0
        if (!world.isAir(newPos)) {
            if (random.nextInt(5) != 0) return
            world.breakBlock(newPos, false)
        }
        world.setBlockState(newPos, four.transform(LCCBlocks.four_leaf_clover, LCCBlocks.three_leaf_clover).defaultState, 3)
    }

}