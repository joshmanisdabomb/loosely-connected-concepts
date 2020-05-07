package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;

public abstract class LCCTags {

    public static final BlockTags.Wrapper COLORED_CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_canes"));
    public static final BlockTags.Wrapper COLORED_CANDY_CANE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane"));
    public static final BlockTags.Wrapper COLORED_CANDY_CANE_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane_coating"));
    public static final BlockTags.Wrapper REFINED_COLORED_CANDY_CANE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane"));
    public static final BlockTags.Wrapper REFINED_COLORED_CANDY_CANE_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane_coating"));
    public static final BlockTags.Wrapper STRIPPED_CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "stripped_candy_canes"));

    public static final BlockTags.Wrapper CHANNELITE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "channelite"));
    public static final BlockTags.Wrapper CHANNELITE_SOURCE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "channelite_source"));

    public static final BlockTags.Wrapper RAINBOW_EFFECTIVE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "rainbow_effective"));
    public static final BlockTags.Wrapper RAINBOW_REQUIRED = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "rainbow_required"));
    public static final BlockTags.Wrapper WASTELAND_EFFECTIVE = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "wasteland_effective"));
    public static final BlockTags.Wrapper WASTELAND_REQUIRED = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "wasteland_required"));

    public static final FluidTags.Wrapper OIL = new FluidTags.Wrapper(new ResourceLocation(LCC.MODID, "oil"));

}