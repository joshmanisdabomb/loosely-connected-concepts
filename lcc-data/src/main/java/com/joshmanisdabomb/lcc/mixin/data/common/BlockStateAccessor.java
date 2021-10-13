package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.AbstractBlockState.class)
public interface BlockStateAccessor {

    @Accessor("hardness")
    public float getHardness();

    @Accessor("mapColor")
    public MapColor getMapColor();

}