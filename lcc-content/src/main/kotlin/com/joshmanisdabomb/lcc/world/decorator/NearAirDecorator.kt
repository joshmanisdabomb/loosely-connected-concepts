package com.joshmanisdabomb.lcc.world.decorator

/*import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorContext
import net.minecraft.world.gen.decorator.NopeDecoratorConfig
import java.util.*
import java.util.stream.Stream

class NearAirDecorator(codec: Codec<NopeDecoratorConfig>) : Decorator<NopeDecoratorConfig>(codec) {

    override fun getPositions(context: DecoratorContext, random: Random, config: NopeDecoratorConfig, pos: BlockPos): Stream<BlockPos> {
        for (d in Direction.values()) {
            if (context.getBlockState(pos.offset(d)).isAir) return Stream.of(pos)
        }
        return Stream.empty()
    }

}*/