package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ArrowItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.ItemTags
import net.minecraft.world.World

class QuiverItem(size: Int, settings: Settings) : BagItem(size, settings, { it.isIn(ItemTags.ARROWS) }), LCCExtendedItem {

    override fun lcc_getArrow(stack: ItemStack) = if (getBundleOccupancy(stack) > 0) stack else null

    override fun lcc_createArrow(world: World, stack: ItemStack, shooter: LivingEntity): PersistentProjectileEntity? {
        val projectile = getBundledStacks(stack).findFirst().orElse(null) ?: return null
        if (projectile.isEmpty) return null
        val item = projectile.item

        (item as? ArrowItem)?.createArrow(world, projectile, shooter)?.apply { return this }
        (item as? LCCExtendedItem)?.lcc_createArrow(world, projectile, shooter)?.apply { return this }

        return null
    }

    override fun lcc_fireStackBow(stack: ItemStack): Boolean {
        retrieve(stack).ifPresent { it.decrement(1); transfer(stack, it) }
        return true
    }

    override fun lcc_fireStackCrossbow(stack: ItemStack): ItemStack? {
        val arrow = retrieve(stack).orElse(null) ?: return null
        if (arrow.isEmpty) return null

        val ret = arrow.split(1)
        transfer(stack, arrow)
        return ret
    }

}
