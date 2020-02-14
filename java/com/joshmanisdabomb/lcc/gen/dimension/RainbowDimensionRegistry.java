package com.joshmanisdabomb.lcc.gen.dimension;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class RainbowDimensionRegistry extends ModDimension implements LCCDimensionHelper {

    @Override
    public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
        return RainbowDimension::new;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }

    @Override
    public PacketBuffer getPacketData() {
        return new PacketBuffer(Unpooled.buffer(16));
    }

}