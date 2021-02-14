package com.joshmanisdabomb.lcc.item.block

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class RadioactiveBlockItem(val duration: Int, val amplifier: Int, block: Block, settings: Settings) : BlockItem(block, settings) {

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, duration.times(stack.count) + 1, amplifier)
    }

}