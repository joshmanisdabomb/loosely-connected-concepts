package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.TntBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.TntEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion

class FunctionalTNTBlock(val factory: (world: World, x: Double, y: Double, z: Double, placer: LivingEntity?) -> TntEntity, settings: Settings, unstable: Boolean = false) : TntBlock(settings) {

    init {
        defaultState = stateManager.defaultState.with(UNSTABLE, unstable)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (!oldState.isOf(state.block)) {
            if (world.isReceivingRedstonePower(pos)) {
                ignite(world, pos, null)
                world.removeBlock(pos, false)
            }
        }
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (world.isReceivingRedstonePower(pos)) {
            ignite(world, pos, null)
            world.removeBlock(pos, false)
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        if (!world.isClient() && player.isSurvival && state[UNSTABLE]) {
            ignite(world, pos, null)
        }
        super.onBreak(world, pos, state.with(UNSTABLE, false), player)
    }

    override fun onDestroyedByExplosion(world: World, pos: BlockPos, explosion: Explosion) {
        if (!world.isClient) {
            val tntEntity = factory(world, pos.x.toDouble() + 0.5, pos.y.toDouble(), pos.z.toDouble() + 0.5, explosion.causingEntity)
            val i = tntEntity.fuse
            tntEntity.fuse = world.random.nextInt(i / 4) + i / 8
            world.spawnEntity(tntEntity)
        }
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        return if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
            super.onUse(state, world, pos, player, hand, hit)
        } else {
            ignite(world, pos, player)
            world.setBlockState(pos, Blocks.AIR.defaultState, 11)
            if (player.isSurvival) {
                if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    stack.damage(1, player) { it.sendToolBreakStatus(hand) }
                } else {
                    stack.decrement(1)
                }
            }
            ActionResult.success(world.isClient)
        }
    }

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        if (!world.isClient) {
            val owner = projectile.owner
            if (projectile.isOnFire) {
                val blockPos = hit.blockPos
                ignite(world, blockPos, owner as? LivingEntity)
                world.removeBlock(blockPos, false)
            }
        }
    }

    fun ignite(world: World, pos: BlockPos, igniter: LivingEntity?) {
        if (!world.isClient) {
            val tntEntity = factory(world, pos.x.toDouble() + 0.5, pos.y.toDouble(), pos.z.toDouble() + 0.5, igniter)
            world.spawnEntity(tntEntity)
            world.playSound(null, tntEntity.x, tntEntity.y, tntEntity.z, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f)
        }
    }

}