package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.function.Supplier;

public class GlobalProvider<C> {

    public static final DimensionType DIMENSION = DimensionType.OVERWORLD;

    private final Supplier<Capability<C>> defaultCap;

    public GlobalProvider(Supplier<Capability<C>> defaultCap) {
        this.defaultCap = defaultCap;
    }

    public LazyOptional<C> get(MinecraftServer server) {
        return DimensionManager.getWorld(server, DIMENSION, false, false).getCapability(this.defaultCap.get());
    }

}
