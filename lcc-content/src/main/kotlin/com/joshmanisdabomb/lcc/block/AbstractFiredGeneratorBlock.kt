package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.directory.LCCDamage
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Properties.LIT
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import kotlin.math.roundToInt

abstract class AbstractFiredGeneratorBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(LIT, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(LIT).let {}

    abstract fun getSteam(world: BlockView, pos: BlockPos, state: BlockState): Float

    open fun getWaterMultiplier(world: World, pos: BlockPos, state: BlockState): Float {
        val pos2 = pos.up()
        val state2 = world.getBlockState(pos2)
        return (state2.block as? LCCContentBlockTrait)?.lcc_content_getSteamMultiplier(world, state2, pos2, state, pos) ?: 0f
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state[LIT]) {
            val level = getWaterMultiplier(world, pos, state).times(3).roundToInt()
            if (level > 0) {
                world.addParticle(ParticleTypes.BUBBLE_POP, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.BUBBLE_POP, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.UNDERWATER, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + 0.17 + random.nextDouble().times(0.07), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.SPLASH, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, 0.0, 0.0)
                world.addParticle(ParticleTypes.SPLASH, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, 0.0, 0.0)
            }
            super.randomDisplayTick(state, world, pos, random)
        }
    }

    override fun onSteppedOn(world: World, pos: BlockPos, entity: Entity) {
        if (world.getBlockState(pos)[Properties.LIT] && entity is LivingEntity && !entity.isFireImmune && !EnchantmentHelper.hasFrostWalker(entity)) {
            entity.damage(LCCDamage.heated, 2.0f)
        }
        super.onSteppedOn(world, pos, entity)
    }

}