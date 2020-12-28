package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import com.joshmanisdabomb.lcc.mixin.content.common.ItemEntityAccessor
import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.max

class TimeRiftBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.time_rift, pos, state) {

    var size = 1f
    var lastSize = size

    lateinit var invStack: ItemStack

    val riftInv = object : DefaultInventory(1) {
        override fun getStack(slot: Int): ItemStack {
            return invStack
        }
    }

    companion object {
        const val range = 5.0

        fun tick(world: World, pos: BlockPos, state: BlockState, entity: TimeRiftBlockEntity, consume: (item: ItemEntity, recipe: TimeRiftRecipe, world: World, pos: BlockPos, state: BlockState, entity: TimeRiftBlockEntity) -> Unit) {
            world.getEntitiesByType(EntityType.ITEM, Box(pos).expand(range), Entity::isAlive).forEach {
                entity.invStack = it.stack
                val recipe = world.recipeManager.getFirstMatch(LCCRecipeTypes.time_rift, entity.riftInv, world).orElse(null) ?: return@forEach

                val distsq = Math.sqrt(it.pos.squaredDistanceTo(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5))
                val s: Double = range.times(range).minus(distsq) * 0.012f
                val speed = s * s * MathHelper.clamp((it as ItemEntityAccessor).age * 0.13, 0.0, 1.0)

                if (distsq < 0.9) {
                    entity.size = 3f
                    consume(it, recipe, world, pos, state, entity)
                } else {
                    entity.size = max(speed.times(4.0).plus(1), entity.size.toDouble()).toFloat()
                    it.velocity = it.velocity.add(it.pos.subtract(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5).normalize().multiply(-speed))
                }
            }
        }

        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: TimeRiftBlockEntity) {
            entity.lastSize = entity.size
            entity.size = max(1.0f, entity.size * 0.94f)

            tick(world, pos, state, entity) { item, _, _, _, _, _ -> item.velocity = Vec3d.ZERO }
        }

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: TimeRiftBlockEntity) {
            tick(world, pos, state, entity, ::receiveItem)
        }

        fun receiveItem(item: ItemEntity, recipe: TimeRiftRecipe, world: World, pos: BlockPos, state: BlockState, entity: TimeRiftBlockEntity) {
            item.stack.decrement(1)
            if (item.stack.isEmpty) item.remove(Entity.RemovalReason.KILLED)

            val result = ItemEntity(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, recipe.output.copy())
            result.velocity = result.velocity.multiply(2.2)
            result.setToDefaultPickupDelay()
            world.spawnEntity(result)
        }
    }

}