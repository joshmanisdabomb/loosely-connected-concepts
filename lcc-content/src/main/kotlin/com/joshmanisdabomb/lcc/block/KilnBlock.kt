package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.KilnBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import java.util.*

class KilnBlock(settings: Settings) : AbstractFurnaceBlock(settings) {

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = KilnBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = checkType(world, type, LCCBlockEntities.kiln)

    override fun openScreen(world: World, pos: BlockPos, player: PlayerEntity) {
        player.openHandledScreen(world.getBlockEntity(pos) as? KilnBlockEntity ?: return)
    }

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.get(LIT) as Boolean) {
            val d = pos.x.toDouble() + 0.5
            val e = pos.y.toDouble()
            val f = pos.z.toDouble() + 0.5
            if (random.nextDouble() < 0.1) {
                world.playSound(d, e, f, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false)
            }
            val direction = state.get(FACING) as Direction
            val axis = direction.axis
            val h = random.nextDouble() * 0.6 - 0.3
            val i = if (axis === Direction.Axis.X) direction.offsetX.toDouble() * 0.52 else h
            val j = random.nextDouble() * 9.0 / 16.0
            val k = if (axis === Direction.Axis.Z) direction.offsetZ.toDouble() * 0.52 else h
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0)
        }
    }

}