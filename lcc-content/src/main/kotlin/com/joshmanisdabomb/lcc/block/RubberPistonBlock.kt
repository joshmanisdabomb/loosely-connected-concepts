package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.facade.piston.LCCPiston
import com.joshmanisdabomb.lcc.mixin.hooks.common.FallingBlockEntityAccessor
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FallingBlock
import net.minecraft.block.PistonBlock
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class RubberPistonBlock(sticky: Boolean, settings: Settings) : PistonBlock(sticky, settings), LCCPiston {

    override val head get() = LCCBlocks.rubber_piston_head

    override fun createExtension(world: World, pos: BlockPos, extension: BlockState, pushed: BlockState, facing: Direction, extending: Boolean, source: Boolean): PistonBlockEntity {
        val block = pushed.block
        if (block is FallingBlock) {
            if (world is ServerWorld) {
                val entity = FallingBlockEntityAccessor.create(world, pos.x.toDouble().plus(0.5).plus(facing.offsetX.times(-1.0)), pos.y.toDouble().plus(facing.offsetY.times(-1.0)), pos.z.toDouble().plus(0.5).plus(facing.offsetZ.times(-1.0)), pushed)
                entity.setVelocity(facing.offsetX.times(1.0), facing.offsetY.times(1.0), facing.offsetZ.times(1.0))
                entity.timeFalling = 300
                world.spawnEntity(entity)
            }
            return super.createExtension(world, pos, extension, Blocks.AIR.defaultState, facing, extending, source)
        }
        return super.createExtension(world, pos, extension, pushed, facing, extending, source)
    }

    override fun slimePush(entity: PistonBlockEntity, world: World, pos: BlockPos, f: Float) = true

}
