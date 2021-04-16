package com.joshmanisdabomb.lcc.world.decorator

import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorContext
import net.minecraft.world.gen.decorator.NopeDecoratorConfig
import java.util.*
import java.util.stream.Stream

class NearAirDecorator(codec: Codec<NopeDecoratorConfig>) : Decorator<NopeDecoratorConfig>(codec) {

    override fun getPositions(context: DecoratorContext, random: Random, config: NopeDecoratorConfig, pos: BlockPos): Stream<BlockPos> {
        if (GenUtils.areaMatches(context::getBlockState, pos.x, pos.y, pos.z, expand = 1, test = GenUtils::any)) {
            return Stream.of(pos)
        }
        return Stream.empty()
    }

}