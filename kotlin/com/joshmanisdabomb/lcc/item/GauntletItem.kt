package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GauntletItem(settings: Item.Settings) : Item(settings) {

    override fun canMine(state: BlockState?, world: World?, pos: BlockPos?, miner: PlayerEntity?) = false

    override fun getMiningSpeedMultiplier(stack: ItemStack?, state: BlockState?): Float = Float.MAX_VALUE

    override fun use(world: World?, user: PlayerEntity, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand) ?: ItemStack.EMPTY;
        if (GauntletAction.UPPERCUT.act(user)) {
            return TypedActionResult.success(stack)
        } else {
            return TypedActionResult.fail(stack)
        }
    }

}