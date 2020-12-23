package com.joshmanisdabomb.lcc.world.decorator

import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorContext
import net.minecraft.world.gen.decorator.NopeDecoratorConfig
import java.util.*
import java.util.stream.Stream

class NearLavaLakeDecorator(codec: Codec<NopeDecoratorConfig>) : Decorator<NopeDecoratorConfig>(codec) {

    override fun getPositions(context: DecoratorContext, random: Random, config: NopeDecoratorConfig, pos: BlockPos): Stream<BlockPos> {
        val pos2 = BlockPos.Mutable(pos.x, context.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.x, pos.z), pos.z)

        println(pos2)
        if (!GenUtils.areaMatches(context, pos2.x, pos2.y, pos2.z, expand = 5, test = GenUtils::any) {
            pos2.set(it)
            for (i in -1..1) {
                for (k in -1..1) {
                    pos2.set(it).move(i, 0, k)
                    if (!context.getBlockState(pos2).fluidState.isIn(FluidTags.LAVA)) return@areaMatches false
                }
            }
            pos2.set(it)
            true
        }) {
            println("n")
            return Stream.empty()
        }

        println("y")
        return Stream.of(pos2.add(random.nextInt(2), random.nextInt(2), random.nextInt(2)))
    }

}