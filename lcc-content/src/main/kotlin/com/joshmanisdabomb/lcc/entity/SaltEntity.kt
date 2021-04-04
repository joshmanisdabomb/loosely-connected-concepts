package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.AutomaticItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties.LEVEL_3
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class SaltEntity : ThrownItemEntity, LCCExtendedEntity {

    constructor(type: EntityType<out ThrownItemEntity>, world: World) : super(type, world)

    constructor(world: World, owner: LivingEntity) : super(LCCEntities.salt, owner, world)

    constructor(world: World, x: Double, y: Double, z: Double) : super(LCCEntities.salt, x, y, z, world)

    override fun getDefaultItem() = LCCItems.salt

    override fun tick() {
        if (!world.getFluidState(blockPos).isEmpty) {
            destroy()
        } else {
            super.tick()
        }
    }

    override fun canHit(entity: Entity) = entity.type.isIn(LCCTags.salt_weakness) || (entity as? PlayerEntity)?.isSurvival == true

    override fun onEntityHit(result: EntityHitResult) {
        super.onEntityHit(result)
        val entity = result.entity
        if (entity is LivingEntity && entity.type.isIn(LCCTags.salt_weakness)) {
            entity.addStatusEffect(StatusEffectInstance(StatusEffects.BLINDNESS, 50))
            entity.hurtTime = 0
            entity.timeUntilRegen = 0
            entity.damage(LCCDamage.salt(this, this.owner), 1f)
            destroy()
        } else if (entity is PlayerEntity && entity != this.owner && entity.isSurvival) {
            entity.addStatusEffect(StatusEffectInstance(StatusEffects.BLINDNESS, 50))
            destroy()
        }
    }

    override fun onBlockHit(result: BlockHitResult) {
        val state = world.getBlockState(result.blockPos)
        if (state.isOf(LCCBlocks.scattered_salt) && state[LEVEL_3] < 3) {
            world.setBlockState(result.blockPos, state.cycle(LEVEL_3))
        } else {
            if (result.side == Direction.UP && state.isSideSolidFullSquare(world, result.blockPos, Direction.UP)) {
                val up = result.blockPos.up()
                val above = world.getBlockState(up)
                if (above.canReplace(AutomaticItemPlacementContext(world, up, Direction.DOWN, ItemStack.EMPTY, Direction.UP))) {
                    world.setBlockState(up, LCCBlocks.scattered_salt.defaultState.with(LEVEL_3, 1))
                }
            }
        }
        destroy()
        super.onBlockHit(result)
    }

    override fun getGravity() = 0.07f

    private fun destroy() {
        if (!world.isClient) {
            world.sendEntityStatus(this, 3.toByte())
            discard()
        }
    }

}