package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class RadioactiveItem(val duration: Int, val amplifier: Int, settings: Settings) : Item(settings) {

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, duration.times(stack.count) + 1, amplifier)
    }

}