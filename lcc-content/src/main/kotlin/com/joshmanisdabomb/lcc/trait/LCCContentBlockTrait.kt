package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.Temperature
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.block.AbstractFiredGeneratorBlock
import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Properties.LEVEL_3
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.*
import kotlin.math.roundToInt

interface LCCContentBlockTrait {

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
                is AbstractFiredGeneratorBlock ->
                    if (below[Properties.LIT]) {
                        block.getSteam(world, pos2, below).run { if (this <= 0f) null else this }
                    } else 0f
                else -> { Temperature.getEnergyFromPos(world, pos2, below, block) }
            }
        }
        return null
    }

    @JvmDefault
    fun lcc_content_steamProviderParticles(world: BlockView, provider: BlockState, pos: BlockPos, below: BlockState, pos2: BlockPos, turbineDist: Int, random: Random) {
        if (world !is WorldAccess) return
        if (provider.isOf(Blocks.WATER_CAULDRON)) {
            val block = below.block
            when (block) {
                is AbstractFiredGeneratorBlock -> {
                    if (below[Properties.LIT]) {
                        for (j in 0..(lcc_content_getSteamMultiplier(world, provider, pos, below, pos2) ?: 0f).times(3).roundToInt()) {
                            world.addParticle(LCCParticles.steam, pos.x.plus(0.4).plus(random.nextDouble().times(0.2)), pos.y.plus(0.7), pos.z.plus(0.4).plus(random.nextDouble().times(0.2)), 0.0, random.nextDouble().times(0.1), 0.0)
                        }
                    }
                }
                else -> {
                    val steam = Temperature.getEnergyFromPos(world, pos2, below, block) ?: return
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

    @JvmDefault
    fun lcc_content_nukeResistance(state: BlockState, target: BlockPos, rand: SplittableRandom) = (this as Block).blastResistance

    @JvmDefault
    fun lcc_content_getTemperature(world: BlockView, state: BlockState, pos: BlockPos) = Temperature.values().filter { state.isIn(it.tag) || state.isIn(it.soulTag ?: return@filter false) }.maxOrNull()

    @JvmDefault
    fun lcc_content_getTemperatureEnergy(world: BlockView, state: BlockState, pos: BlockPos, temperature: Temperature, soul: Boolean = temperature.soulTag?.let { state.isIn(it) } ?: false) = when (this) {
        is CandleBlock -> if (!state[Properties.LIT]) null else temperature.energy * state[Properties.CANDLES]
        is CandleCakeBlock -> if (!state[Properties.LIT]) null else temperature.energy
        is CampfireBlock -> if (!state[Properties.LIT]) null else temperature.energy.times(state[Properties.SIGNAL_FIRE].transform(0.175f.div(0.15f), 1f))
        is FluidBlock -> getFluidState(state).let { if (it.isStill) 1f else if (it[Properties.FALLING]) return@let null else it[Properties.LEVEL_1_8].times(0.1f) }?.times(temperature.energy)
        else -> temperature.energy
    }?.plus(soul.transform(0.025f, 0.0f))

    @JvmDefault
    fun lcc_content_isToolEffective(state: BlockState, stack: ItemStack, effectivity: ToolEffectivity) = state.isIn(effectivity.effective)

    @JvmDefault
    fun lcc_content_isToolRequired(state: BlockState, stack: ItemStack, effectivity: ToolEffectivity) = state.isIn(effectivity.required)

    @JvmDefault
    fun lcc_content_hideStateFromDebug(state: BlockState, player: PlayerEntity, hit: BlockHitResult): String? = null

}