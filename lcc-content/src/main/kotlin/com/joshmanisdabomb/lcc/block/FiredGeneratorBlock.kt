package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.block.entity.FiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.LIT
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import kotlin.math.roundToInt

abstract class FiredGeneratorBlock(settings: Settings) : BlockWithEntity(settings) {

    abstract val slots: Int
    abstract val maxOutput: Float

    open val defaultDisplayName by lazy { TranslatableText("container.lcc.${LCCBlocks[this].name}") }

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(LIT, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, LIT).let {}

    abstract fun getBurnTime(stack: ItemStack): Int?

    abstract fun getSteam(stack: ItemStack): Float

    fun getSteam(world: BlockView, pos: BlockPos, state: BlockState) = (world.getBlockEntity(pos) as? FiredGeneratorBlockEntity)?.steam ?: 0f

    fun getWaterMultiplier(world: World, pos: BlockPos, state: BlockState): Float {
        val pos2 = pos.up()
        val state2 = world.getBlockState(pos2)
        return (state2.block as? LCCExtendedBlockContent)?.lcc_content_getSteamMultiplier(world, state2, pos2, state, pos) ?: 0f
    }

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = FiredGeneratorBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? FiredGeneratorBlockEntity)?.customName = stack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? FiredGeneratorBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state[LIT]) {
            val level = getWaterMultiplier(world, pos, state).times(3).roundToInt()
            if (level > 0) {
                world.addParticle(ParticleTypes.BUBBLE_POP, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.BUBBLE_POP, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.UNDERWATER, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + 0.17 + random.nextDouble().times(0.07), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, random.nextDouble().times(0.04), 0.0)
                world.addParticle(ParticleTypes.SPLASH, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, 0.0, 0.0)
                world.addParticle(ParticleTypes.SPLASH, pos.x + 0.125 + random.nextDouble().times(0.75), pos.y + 1.35 + level.times(0.1875) + random.nextDouble().times(0.05), pos.z + 0.125 + random.nextDouble().times(0.75), 0.0, 0.0, 0.0)
            }
            super.randomDisplayTick(state, world, pos, random)
        }
    }

    override fun onSteppedOn(world: World, pos: BlockPos, entity: Entity) {
        if (world.getBlockState(pos)[LIT] && entity is LivingEntity && !entity.isFireImmune && !EnchantmentHelper.hasFrostWalker(entity)) {
            entity.damage(LCCDamage.heated, 2.0f)
        }
        super.onSteppedOn(world, pos, entity)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.generator, FiredGeneratorBlockEntity::serverTick) else null

    abstract fun createMenu(syncId: Int, inv: PlayerInventory, inventory: LCCInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler

}
