package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock
import com.joshmanisdabomb.lcc.extensions.toInt
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class RadioactiveBlock(val duration: Int, val amplifier: Int, settings: Settings) : Block(settings), LCCExtendedBlock {

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, duration.times((distSq < 1.3).toInt(3, 1)), amplifier)
    }

}