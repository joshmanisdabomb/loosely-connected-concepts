package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCParticles
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.*

class SoakingSoulSandBlock(settings: Settings) : Block(settings) {

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        with(Direction.random(random)) {
            world.addParticle(
                LCCParticles.soaking_soul_sand_bubble,
                pos.x.toDouble() + if (this.offsetX != 0) MathHelper.clamp(this.offsetX.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                pos.y.toDouble() + if (this.offsetY != 0) MathHelper.clamp(this.offsetY.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                pos.z.toDouble() + if (this.offsetZ != 0) MathHelper.clamp(this.offsetZ.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                if (this.offsetX == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0,
                if (this.offsetY == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0,
                if (this.offsetZ == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0
            )
        }
    }

}