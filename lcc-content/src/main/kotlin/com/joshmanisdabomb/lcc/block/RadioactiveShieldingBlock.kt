package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import java.util.*

class RadioactiveShieldingBlock(duration: Int, amplifier: Int, settings: Settings) : RadioactiveBlock(duration, amplifier, settings), LCCContentBlockTrait {

    override fun lcc_content_nukeResistance(state: BlockState, target: BlockPos, rand: SplittableRandom) = if (rand.nextInt(400) == 0) 0f else 10000f

}