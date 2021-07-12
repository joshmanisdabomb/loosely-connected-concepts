package com.joshmanisdabomb.lcc.trait

import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface LCCItemTrait {

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

    @JvmDefault
    fun lcc_pickupItem(world: World, stack: ItemStack, listener: ItemStack?, listenerSlot: Int?, player: PlayerEntity, entity: ItemEntity): Boolean? = null

    @JvmDefault
    fun lcc_pickupItemListen(world: World, stack: ItemStack, slot: Int, acquired: ItemStack, player: PlayerEntity, entity: ItemEntity): Boolean? = null

    @JvmDefault
    fun lcc_pickupProjectile(world: World, stack: ItemStack, listener: ItemStack?, listenerSlot: Int?, player: PlayerEntity, entity: PersistentProjectileEntity): Boolean? = null

    @JvmDefault
    fun lcc_pickupProjectileListen(world: World, stack: ItemStack, slot: Int, acquired: ItemStack, player: PlayerEntity, entity: PersistentProjectileEntity): Boolean? = null

    @JvmDefault
    fun lcc_getAdditionalItemBarIndexes(stack: ItemStack) = intArrayOf()

    @JvmDefault
    fun lcc_getAdditionalItemBarOffset(stack: ItemStack, index: Int) = 0

    @JvmDefault
    fun lcc_getAdditionalItemBarStep(stack: ItemStack, index: Int) = 0

    @JvmDefault
    fun lcc_getAdditionalItemBarColor(stack: ItemStack, index: Int) = 0

}