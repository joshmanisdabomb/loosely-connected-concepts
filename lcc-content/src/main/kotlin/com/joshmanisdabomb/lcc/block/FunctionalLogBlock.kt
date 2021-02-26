package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.PillarBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.AxeItem
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class FunctionalLogBlock(settings: Settings, val stripped: (state: BlockState) -> BlockState?) : PillarBlock(settings) {

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (stack.item is AxeItem) {
            val state2 = stripped(state) ?: return ActionResult.PASS
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
            if (!world.isClient) {
                world.setBlockState(pos, state2, 11)
                stack.damage(1, player) { it.sendToolBreakStatus(hand) }
            }
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

}