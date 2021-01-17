package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.state.property.Properties.LIT
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class FiredGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.generator, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val generatorBlock get() = cachedState.block as? FiredGeneratorBlock

    val inventory by lazy { object : DefaultInventory(generatorBlock!!.slots) {

        override fun isValid(slot: Int, stack: ItemStack): Boolean {
            return generatorBlock!!.getBurnTime(stack) != null
        }

    }.apply { addListener { this@FiredGeneratorBlockEntity.markDirty() } } }

    private var burn = 0
    private var output = 0f
    private var boost = 0f

    private var working = false

    var customName: Text? = null

    private var boostDisplay = DecimalTransport(::boost)
    private var outputDisplay = DecimalTransport(::output)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = TODO()

    override fun getDisplayName() = customName ?: generatorBlock?.defaultDisplayName

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        burn = tag.getInt("Burn")
        boost = tag.getFloat("Boost")
        output = tag.getFloat("Output")

        working = tag.getBoolean("Working")

        inventory.apply { clear(); Inventories.fromTag(tag, inventory) }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)

        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }

        tag.putInt("Burn", burn)
        tag.putFloat("Boost", boost)
        tag.putFloat("Output", output)

        tag.putBoolean("Working", working)

        Inventories.toTag(tag, inventory.inventory)

        return tag
    }

    override fun clear() = inventory.clear()
    override fun size() = inventory.size()
    override fun isEmpty() = inventory.isEmpty
    override fun getStack(slot: Int) = inventory.getStack(slot)
    override fun removeStack(slot: Int, amount: Int) = inventory.removeStack(slot, amount)
    override fun removeStack(slot: Int) = inventory.removeStack(slot)
    override fun setStack(slot: Int, stack: ItemStack) = inventory.setStack(slot, stack)
    override fun canPlayerUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun isValid(slot: Int, stack: ItemStack) = inventory.isValid(slot, stack)

    override fun getAvailableSlots(side: Direction) = (0 until size()).toList().toIntArray()
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = !isValid(slot, getStack(slot))

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: FiredGeneratorBlockEntity) {
            val working = entity.working

            if (entity.burn > 0) {
                entity.working = true
                entity.burn--
            } else {
                entity.working = false
                for ((k, v) in entity.inventory.inventory.withIndex()) {
                    if (v.isEmpty) continue;
                    val burn = entity.generatorBlock!!.getBurnTime(v) ?: continue
                    v.decrement(1)
                    if (v.isEmpty) entity.inventory.setStack(k, v.item?.recipeRemainder?.defaultStack ?: ItemStack.EMPTY)
                    entity.burn += burn
                    entity.output = entity.generatorBlock!!.getSteam(v)
                    entity.working = true
                    break
                }
            }

            if (working != entity.working) world.setBlockState(pos, state.with(LIT, entity.working), 3)

            val water = entity.generatorBlock!!.getWaterLevel(world, pos, state)
            if (entity.working && water > 0) {
                world.getEntitiesByClass(LivingEntity::class.java, Box(pos).offset(0.0, 1.0, 0.0).contract(0.125), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and { !it.isFireImmune }).forEach {
                    it.damage(LCCDamage.boiled, 3.0F)
                }
            }
        }
    }

}