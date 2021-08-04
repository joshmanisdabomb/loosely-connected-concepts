package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.ImprovisedExplosiveBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.thrown.ThrownEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion

class ImprovisedExplosiveBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(ie_state, ImprovisedExplosiveState.INACTIVE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(ie_state).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = ImprovisedExplosiveBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (oldState.isOf(state.block)) return
        react(state, world, pos)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        react(state, world, pos)
    }

    override fun onDestroyedByExplosion(world: World, pos: BlockPos, explosion: Explosion) {
        world.setBlockState(pos, defaultState.with(ie_state, ImprovisedExplosiveState.IMMINENT))
        (world.getBlockEntity(pos) as? ImprovisedExplosiveBlockEntity)?.fuse = world.random.nextInt(20).plus(10)
    }

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        if (world.isClient) return
        if (projectile !is ThrownEntity) {
            react(state, world, hit.blockPos, true)
        }
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (state[ie_state] != ImprovisedExplosiveState.INACTIVE && player.getStackInHand(hand).isIn(FabricToolTags.SHEARS)) {
            world.breakBlock(pos, true)
            world.playSound(null, pos, LCCSounds.improvised_explosive_defuse, SoundCategory.BLOCKS, 1.0f, 1.0f)
            return ActionResult.SUCCESS
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    protected fun react(state: BlockState, world: World, pos: BlockPos, redstone: Boolean = world.isReceivingRedstonePower(pos)) {
        if (state[ie_state] == ImprovisedExplosiveState.INACTIVE && redstone) {
            world.setBlockState(pos, state.with(ie_state, ImprovisedExplosiveState.ACTIVE))
            world.playSound(null, pos, LCCSounds.improvised_explosive_triggered, SoundCategory.BLOCKS, 2.0f, 1.0f)
            if (!world.isClient) {
                (world.getBlockEntity(pos) as? ImprovisedExplosiveBlockEntity)?.fuse = world.random.nextInt(4).plus(4).times(20)
            }
        } else if (state[ie_state] == ImprovisedExplosiveState.ACTIVE && !redstone) {
            explode(world, pos)
        }
    }

    fun explode(world: World, pos: BlockPos) {
        world.removeBlock(pos, false)
        world.createExplosion(null, null, null, pos.x.plus(0.5), pos.y.plus(0.5), pos.z.plus(0.5), 6.0F, true, Explosion.DestructionType.DESTROY)
    }

    override fun shouldDropItemsOnExplosion(explosion: Explosion) = false

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (world.isClient) checkType(type, LCCBlockEntities.improvised_explosive, ImprovisedExplosiveBlockEntity::clientTick) else checkType(type, LCCBlockEntities.improvised_explosive, ImprovisedExplosiveBlockEntity::serverTick)
    }

    companion object {
        val ie_state = EnumProperty.of("state", ImprovisedExplosiveState::class.java)
    }

    enum class ImprovisedExplosiveState : StringIdentifiable {
        INACTIVE,
        ACTIVE,
        IMMINENT;

        override fun asString() = name.toLowerCase()
    }

}