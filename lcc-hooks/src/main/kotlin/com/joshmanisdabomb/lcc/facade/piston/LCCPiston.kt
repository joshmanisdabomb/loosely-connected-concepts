package com.joshmanisdabomb.lcc.facade.piston

import com.joshmanisdabomb.lcc.LCCHooks
import net.minecraft.block.BlockState
import net.minecraft.block.PistonExtensionBlock
import net.minecraft.block.PistonHeadBlock
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

interface LCCPiston {

    val head: PistonHeadBlock

    fun createExtension(world: World, pos: BlockPos, extension: BlockState, pushed: BlockState, facing: Direction, extending: Boolean, source: Boolean): PistonBlockEntity {
        val ret = PistonExtensionBlock.createBlockEntityPiston(pos, extension, pushed, facing, extending, source) as PistonBlockEntity
        val component = LCCHooks.piston_cause_component_key.getNullable(ret) ?: return ret
        component.head = head as? LCCPistonHead
        component.base = this
        return ret
    }

    fun slimePush(entity: PistonBlockEntity, world: World, pos: BlockPos, f: Float): Boolean? = null

}