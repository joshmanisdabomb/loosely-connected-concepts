package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.inventory.container.KilnScreenHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos

class KilnBlockEntity(pos: BlockPos, state: BlockState) : AbstractFurnaceBlockEntity(LCCBlockEntities.kiln, pos, state, LCCRecipeTypes.kiln) {

    override fun getContainerName() = Text.translatable("container.lcc.kiln")

    override fun getFuelTime(fuel: ItemStack) = super.getFuelTime(fuel).div(2)

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory) = KilnScreenHandler(syncId, playerInventory, this, propertyDelegate)

}