package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class RadioactiveBlock(val duration: Int, val amplifier: Int, settings: Settings) : Block(settings), LCCBlockTrait {

    override fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) {
        NuclearUtil.addRadiation(entity as? LivingEntity ?: return, duration.times((distSq < 1.3).transformInt(3, 1)), amplifier)
    }

}
