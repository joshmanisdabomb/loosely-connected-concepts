package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.stack
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult

class IronOxideItem(settings: Settings) : Item(settings) {

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val player = context.player ?: return ActionResult.FAIL
        if (context.world.getBlockState(context.blockPos).isOf(Blocks.LODESTONE)) {
            context.world.playSound(null, context.blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0f, 1.0f)
            if (!player.abilities.creativeMode) context.stack.decrement(1)
            val stack = LCCItems.magnetic_iron.stack()
            if (!player.inventory.insertStack(stack)) player.dropItem(stack, false)
            return ActionResult.SUCCESS
        }
        return super.useOnBlock(context)
    }

}
