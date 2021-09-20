package com.joshmanisdabomb.lcc.mixin.hooks.common;

import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators")
public interface DecoratorAccessor {

    @Accessor("SQUARE_HEIGHTMAP")
    public static ConfiguredDecorator<?> getSquareHeightmap() { throw new AssertionError(); }

    @Accessor("SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER")
    public static ConfiguredDecorator<?> getSquareHeightmapOceanFloorNoWater() { throw new AssertionError(); }

    @Accessor("SQUARE_TOP_SOLID_HEIGHTMAP")
    public static ConfiguredDecorator<?> getSquareTopSolidHeightmap() { throw new AssertionError(); }

    @Accessor("SPREAD_32_ABOVE")
    public static ConfiguredDecorator<?> getSpread32Above() { throw new AssertionError(); }

    @Accessor("HEIGHTMAP_WORLD_SURFACE")
    public static ConfiguredDecorator<?> getHeightmapWorldSurface() { throw new AssertionError(); }

}