package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.item.HazmatArmorItem
import com.joshmanisdabomb.lcc.item.HazmatTankArmorItem
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FallingBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class NuclearWasteBlock(settings: Settings) : Block(settings), LCCExtendedBlock, LCCExtendedBlockContent {

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        fall(world, pos, state, oldState, initCascade = true)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        world.blockTickScheduler.schedule(pos, this, 1)
        return state
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        fall(world, pos, state, state_air, random)
    }

    fun fall(world: World, pos: BlockPos, state: BlockState, oldState: BlockState, random: Random = world.random, initCascade: Boolean = false) {
        val bp = BlockPos.Mutable().set(pos, 0, 1, 0)
        val fire = world.getBlockState(bp).isOf(LCCBlocks.nuclear_fire)
        bp.move(0, -1, 0)
        var flag = false
        while (FallingBlock.canFallThrough(world.getBlockState(bp.move(0, -1, 0))) && bp.y >= world.bottomY) {
            flag = true
        }
        if (flag) {
            world.setBlockState(pos, oldState)
            bp.move(0, 1, 0)

            if (world.random.nextInt(12) == 0 || !cascadeFall(world, bp, state, fire, random)) {
                world.setBlockState(bp, state)
                if (fire) world.setBlockState(bp.move(0, 1, 0), state_fire)
            }
        } else if (initCascade && world.random.nextInt(2) == 0 && world.getBlockState(pos.down()).isOf(this) && cascadeFall(world, pos, state, fire, random)) {
            world.setBlockState(pos, oldState)
        }
    }

    fun cascadeFall(world: World, pos: BlockPos, state: BlockState, fire: Boolean, random: Random = world.random): Boolean {
        val bp = BlockPos.Mutable()
        for (d in horizontalDirections.toList().apply { Collections.shuffle(this, random) }) {
            if (world.getBlockState(bp.set(pos).move(d)).isAir && world.getBlockState(bp.move(0, -1, 0)).isAir) {
                world.setBlockState(bp, state)
                if (fire) world.setBlockState(bp.move(0, 1, 0), state_fire)
                return true
            }
        }
        return false
    }

    override fun lcc_content_nukeIgnore() = true

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, 2.times((distSq < 1.3).transformInt(3, 1)), 0)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (entity is LivingEntity) {
            val stack = entity.getEquippedStack(EquipmentSlot.FEET)
            if ((stack.item as? HazmatArmorItem)?.hasFullSuit(stack, entity.armorItems) == true && ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(entity.armorItems) > 0f) {
                return
            }
        }
        entity.damage(LCCDamage.radiation, 0.5f)
    }

    companion object {
        val state_air by lazy { Blocks.AIR.defaultState }
        val state_fire by lazy { LCCBlocks.nuclear_fire.defaultState }
    }

}