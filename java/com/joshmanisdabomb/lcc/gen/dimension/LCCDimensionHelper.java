package com.joshmanisdabomb.lcc.gen.dimension;

import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;

public interface LCCDimensionHelper {

    ResourceLocation getRegistryName();

    boolean hasSkyLight();

    PacketBuffer getPacketData();

    default DimensionType getType() {
        return DimensionManager.registerOrGetDimension(this.getRegistryName(), (ModDimension)this, this.getPacketData(), this.hasSkyLight());
    }

}
