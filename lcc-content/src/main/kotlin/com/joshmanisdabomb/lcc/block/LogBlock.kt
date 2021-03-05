package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.PillarBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class LogBlock(settings: Settings, override val stripTransform: (BlockState) -> BlockState?) : PillarBlock(settings), StrippableBlock {

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult) = stripUse(state, world, pos, player, hand, hit)

}