package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.container.DungeonTableScreenHandler
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.LCCInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class DungeonTableBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.spawner_table, pos, state), NamedScreenHandlerFactory, LCCInventory {

    override val inventory = DefaultedList.ofSize(48, ItemStack.EMPTY)
    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = DungeonTableScreenHandler(syncId, inv, this)

    override fun getDisplayName() = customName ?: TranslatableText("container.spawner_table")

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)

        if (tag.contains("CustomName", 8)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.fromTag(tag, this) }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)

        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))

        Inventories.toTag(tag, inventory)

        return tag
    }

}