package com.joshmanisdabomb.lcc.adaptation

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface LCCExtendedItem {

    @JvmDefault
    fun lcc_onEntitySwing(stack: ItemStack, entity: LivingEntity) = false

    @JvmDefault
    fun lcc_getArrow(stack: ItemStack): ItemStack? = null

    @JvmDefault
    fun lcc_createArrow(world: World, stack: ItemStack, shooter: LivingEntity): PersistentProjectileEntity? = null

    @JvmDefault
    fun lcc_fireStackBow(stack: ItemStack) = false

    @JvmDefault
    fun lcc_fireStackCrossbow(stack: ItemStack): ItemStack? = null

}