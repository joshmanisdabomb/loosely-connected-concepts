package com.joshmanisdabomb.lcc.adaptation;

import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.block.TurbineBlock
import com.joshmanisdabomb.lcc.directory.LCCParticles
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FireBlock
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Properties.LEVEL_3
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.*
import kotlin.math.roundToInt

interface LCCExtendedBlockContent {

    @JvmDefault
    fun lcc_content_getSteamMultiplier(world: BlockView, provider: BlockState, pos: BlockPos, below: BlockState, pos2: BlockPos) = when (provider.block) {
        Blocks.WATER_CAULDRON -> provider[LEVEL_3].div(3f)
        else -> null
    }

    @JvmDefault
    fun lcc_content_getSteam(world: BlockView, provider: BlockState, pos: BlockPos, below: BlockState, pos2: BlockPos, turbineDist: Int): Float? {
        if (provider.isOf(Blocks.WATER_CAULDRON)) {
            val block = below.block
            return when (block) {
                is FiredGeneratorBlock -> block.getSteam(world, pos2, below).run { if (this <= 0f) null else this }
                else -> { TurbineBlock.getGeothermalLevel(world, pos2, below, block) }
            }
        }
        return null
    }

    @JvmDefault
    fun lcc_content_createParticles(world: BlockView, provider: BlockState, pos: BlockPos, below: BlockState, pos2: BlockPos, turbineDist: Int, random: Random) {
        if (world !is WorldAccess) return
        if (provider.isOf(Blocks.WATER_CAULDRON)) {
            val block = below.block
            when (block) {
                is FiredGeneratorBlock -> {
                    if (below[Properties.LIT]) {
                        for (j in 0..(lcc_content_getSteamMultiplier(world, provider, pos, below, pos2) ?: 0f).times(3).roundToInt()) {
                            world.addParticle(LCCParticles.steam, pos.x.plus(0.4).plus(random.nextDouble().times(0.2)), pos.y.plus(0.7), pos.z.plus(0.4).plus(random.nextDouble().times(0.2)), 0.0, random.nextDouble().times(0.1), 0.0)
                        }
                    }
                }
                else -> {
                    val steam = TurbineBlock.getGeothermalLevel(world, pos2, below, block) ?: return
                    for (j in 0..steam.div(3f).toInt()) {
                        if (random.nextFloat() <= steam.div(3f).rem(1f)) {
                            world.addParticle(LCCParticles.steam, pos.x.plus(0.4).plus(random.nextDouble().times(0.2)), pos.y.plus(0.7), pos.z.plus(0.4).plus(random.nextDouble().times(0.2)), 0.0, random.nextDouble().times(0.1), 0.0)
                        }
                    }
                }
            }
        }
    }

    @JvmDefault
    fun lcc_content_nukeIgnore() = false

}