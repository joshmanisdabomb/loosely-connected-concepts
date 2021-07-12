package com.joshmanisdabomb.lcc.facade.piston

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import net.minecraft.block.*
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.block.enums.PistonType
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.block.piston.PistonHandler
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

abstract class AbstractPistonBlock(protected val sticky: Boolean, settings: Settings) : PistonBlock(sticky, settings), LCCPiston {

    override fun onSyncedBlockEvent(state: BlockState, world: World, pos: BlockPos, type: Int, data: Int): Boolean {
        val direction = state.get(FACING)
        if (!world.isClient) {
            val bl = shouldExtend(world, pos, direction)
            if (bl && (type == 1 || type == 2)) {
                world.setBlockState(pos, state.with(EXTENDED, true), 2)
                return false
            }
            if (!bl && type == 0) {
                return false
            }
        }
        if (type == 0) {
            if (!move(world, pos, direction, true)) {
                return false
            }
            world.setBlockState(pos, state.with(EXTENDED, true), 67)
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.25f + 0.6f)
            world.emitGameEvent(GameEvent.PISTON_EXTEND, pos)
        } else if (type == 1 || type == 2) {
            (world.getBlockEntity(pos.offset(direction)) as? PistonBlockEntity)?.finish()
            val blockState = Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, direction).with(PistonExtensionBlock.TYPE, if (this.sticky) PistonType.STICKY else PistonType.DEFAULT)
            world.setBlockState(pos, blockState, 20)
            world.addBlockEntity(PistonExtensionBlock.createBlockEntityPiston(pos, blockState, defaultState.with(FACING, Direction.byId(data and 7)), direction, false, true))
            world.updateNeighbors(pos, blockState.block)
            blockState.updateNeighbors(world, pos, 2)
            if (this.sticky) {
                val blockPos = pos.add(direction.offsetX * 2, direction.offsetY * 2, direction.offsetZ * 2)
                val blockState2 = world.getBlockState(blockPos)
                var bl2 = false
                if (blockState2.isOf(Blocks.MOVING_PISTON)) {
                    val blockEntity2 = world.getBlockEntity(blockPos) as? PistonBlockEntity
                    if (blockEntity2 != null) {
                        if (blockEntity2.facing == direction && blockEntity2.isExtending) {
                            blockEntity2.finish()
                            bl2 = true
                        }
                    }
                }
                if (!bl2) {
                    if (type != 1 || blockState2.isAir || !isMovable(blockState2, world, blockPos, direction.opposite, false, direction) || blockState2.pistonBehavior != PistonBehavior.NORMAL && !blockState2.isOf(Blocks.PISTON) && !blockState2.isOf(Blocks.STICKY_PISTON)) {
                        world.removeBlock(pos.offset(direction), false)
                    } else {
                        move(world, pos, direction, false)
                    }
                }
            } else {
                world.removeBlock(pos.offset(direction), false)
            }
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.15f + 0.6f)
            world.emitGameEvent(GameEvent.PISTON_CONTRACT, pos)
        }
        return true
    }

    protected fun shouldExtend(world: World, pos: BlockPos, pistonFace: Direction): Boolean {
        val var4 = Direction.values()
        val var5 = var4.size
        var var6: Int
        var6 = 0
        while (var6 < var5) {
            val direction = var4[var6]
            if (direction != pistonFace && world.isEmittingRedstonePower(pos.offset(direction), direction)) {
                return true
            }
            ++var6
        }
        return if (world.isEmittingRedstonePower(pos, Direction.DOWN)) {
            true
        } else {
            val blockPos = pos.up()
            val var10 = Direction.values()
            var6 = var10.size
            for (var11 in 0 until var6) {
                val direction2 = var10[var11]
                if (direction2 != Direction.DOWN && world.isEmittingRedstonePower(blockPos.offset(direction2), direction2)) {
                    return true
                }
            }
            false
        }
    }

    protected fun move(world: World, pos: BlockPos, dir: Direction, retract: Boolean): Boolean {
        val blockPos = pos.offset(dir)
        if (!retract && world.getBlockState(blockPos).isOf(head)) {
            world.setBlockState(blockPos, Blocks.AIR.defaultState, 20)
        }
        val pistonHandler = PistonHandler(world, pos, dir, retract)
        return if (!pistonHandler.calculatePush()) {
            false
        } else {
            val map: MutableMap<BlockPos, BlockState> = Maps.newHashMap()
            val list = pistonHandler.movedBlocks
            val list2: MutableList<BlockState> = Lists.newArrayList()
            for (i in list.indices) {
                val blockPos2 = list[i]
                val blockState = world.getBlockState(blockPos2)
                list2.add(blockState)
                map[blockPos2] = blockState
            }
            val list3 = pistonHandler.brokenBlocks
            val blockStates = arrayOfNulls<BlockState>(list.size + list3.size)
            val direction = if (retract) dir else dir.opposite
            var j = 0
            var l: Int
            var blockPos4: BlockPos
            var blockState9: BlockState?
            l = list3.size - 1
            while (l >= 0) {
                blockPos4 = list3[l]
                blockState9 = world.getBlockState(blockPos4)
                val blockEntity = if (blockState9.hasBlockEntity()) world.getBlockEntity(blockPos4) else null
                dropStacks(blockState9, world, blockPos4, blockEntity)
                world.setBlockState(blockPos4, Blocks.AIR.defaultState, 18)
                if (!blockState9.isIn(BlockTags.FIRE)) {
                    world.addBlockBreakParticles(blockPos4, blockState9)
                }
                blockStates[j++] = blockState9
                --l
            }
            l = list.size - 1
            while (l >= 0) {
                blockPos4 = list[l]
                blockState9 = world.getBlockState(blockPos4)
                blockPos4 = blockPos4.offset(direction)
                map.remove(blockPos4)
                val blockState4 = Blocks.MOVING_PISTON.defaultState.with(FACING, dir)
                world.setBlockState(blockPos4, blockState4, 68)
                world.addBlockEntity(PistonExtensionBlock.createBlockEntityPiston(blockPos4, blockState4, list2[l], dir, retract, false))
                blockStates[j++] = blockState9
                --l
            }
            if (retract) {
                val pistonType = if (this.sticky) PistonType.STICKY else PistonType.DEFAULT
                val blockState5 = head.defaultState.with(FACING, dir).with(PistonHeadBlock.TYPE, pistonType)
                blockState9 = Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, dir).with(PistonExtensionBlock.TYPE, if (this.sticky) PistonType.STICKY else PistonType.DEFAULT)
                map.remove(blockPos)
                world.setBlockState(blockPos, blockState9, 68)
                world.addBlockEntity(PistonExtensionBlock.createBlockEntityPiston(blockPos, blockState9, blockState5, dir, true, true))
            }
            val blockState7 = Blocks.AIR.defaultState
            var var25: Iterator<*> = map.keys.iterator()
            while (var25.hasNext()) {
                val blockPos5 = var25.next() as BlockPos
                world.setBlockState(blockPos5, blockState7, 82)
            }
            var25 = map.entries.iterator()
            var blockPos7: BlockPos
            while (var25.hasNext()) {
                val entry: Map.Entry<BlockPos, BlockState> = var25.next()
                blockPos7 = entry.key
                entry.value.prepare(world, blockPos7, 2)
                blockState7.updateNeighbors(world, blockPos7, 2)
                blockState7.prepare(world, blockPos7, 2)
            }
            j = 0
            var n: Int
            n = list3.size - 1
            while (n >= 0) {
                blockState9 = blockStates[j++]
                blockPos7 = list3[n]
                blockState9!!.prepare(world, blockPos7, 2)
                world.updateNeighborsAlways(blockPos7, blockState9.block)
                --n
            }
            n = list.size - 1
            while (n >= 0) {
                world.updateNeighborsAlways(list[n], blockStates[j++]!!.block)
                --n
            }
            if (retract) {
                world.updateNeighborsAlways(blockPos, head)
            }
            true
        }
    }

}