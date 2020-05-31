package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class LCCTags {

    public static final ArrayList<WrapperWrapper> ALL = new ArrayList<>();

    public static final WrapperWrapper RED_CANDY_CANES = new WrapperWrapper(new ResourceLocation(LCC.MODID, "red_candy_canes"));
    public static final WrapperWrapper GREEN_CANDY_CANES = new WrapperWrapper(new ResourceLocation(LCC.MODID, "green_candy_canes"));
    public static final WrapperWrapper BLUE_CANDY_CANES = new WrapperWrapper(new ResourceLocation(LCC.MODID, "blue_candy_canes"));
    public static final WrapperWrapper COLORED_CANDY_CANES = new WrapperWrapper(new ResourceLocation(LCC.MODID, "colored_candy_canes"));
    public static final WrapperWrapper COLORED_CANDY_CANE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane"));
    public static final WrapperWrapper COLORED_CANDY_CANE_COATING = new WrapperWrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane_coating"));
    public static final WrapperWrapper REFINED_COLORED_CANDY_CANE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane"));
    public static final WrapperWrapper REFINED_COLORED_CANDY_CANE_COATING = new WrapperWrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane_coating"));
    public static final WrapperWrapper STRIPPED_CANDY_CANES = new WrapperWrapper(new ResourceLocation(LCC.MODID, "stripped_candy_canes"));

    public static final WrapperWrapper CHANNELITE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "channelite"));
    public static final WrapperWrapper CHANNELITE_SOURCE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "channelite_source"));

    public static final WrapperWrapper VIVID_LOGS = new WrapperWrapper(new ResourceLocation(LCC.MODID, "vivid_logs"));

    public static final WrapperWrapper RAINBOW_EFFECTIVE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "rainbow_effective"));
    public static final WrapperWrapper RAINBOW_REQUIRED = new WrapperWrapper(new ResourceLocation(LCC.MODID, "rainbow_required"));
    public static final ItemTags.Wrapper RAINBOW_EQUIPMENT = new ItemTags.Wrapper(new ResourceLocation(LCC.MODID, "rainbow_equipment"));
    public static final WrapperWrapper WASTELAND_EFFECTIVE = new WrapperWrapper(new ResourceLocation(LCC.MODID, "wasteland_effective"));
    public static final WrapperWrapper WASTELAND_REQUIRED = new WrapperWrapper(new ResourceLocation(LCC.MODID, "wasteland_required"));
    public static final ItemTags.Wrapper WASTELAND_EQUIPMENT = new ItemTags.Wrapper(new ResourceLocation(LCC.MODID, "wasteland_equipment"));

    public static final FluidTags.Wrapper OIL = new FluidTags.Wrapper(new ResourceLocation(LCC.MODID, "oil"));

    public static class WrapperWrapper {

        public final BlockTags.Wrapper block;
        public final ItemTags.Wrapper item;

        private WrapperWrapper(ResourceLocation rl) {
            this(new BlockTags.Wrapper(rl), new ItemTags.Wrapper(rl));
        }

        private WrapperWrapper(BlockTags.Wrapper block, ItemTags.Wrapper item) {
            this.block = block;
            this.item = item;
            ALL.add(this);
        }

    }

    public enum AreaEffectivity {
        RAINBOW(RAINBOW_EQUIPMENT, RAINBOW_EFFECTIVE, RAINBOW_REQUIRED),
        WASTELAND(WASTELAND_EQUIPMENT, WASTELAND_EFFECTIVE, WASTELAND_REQUIRED);

        public final ItemTags.Wrapper equipment;
        public final WrapperWrapper effective;
        public final WrapperWrapper required;

        AreaEffectivity(ItemTags.Wrapper equipment, WrapperWrapper effective, WrapperWrapper required) {
            this.equipment = equipment;
            this.effective = effective;
            this.required = required;
        }

        public float getEffectiveSpeed(float speed) {
            return 0.2F + (speed * 0.04F);
        }

        public float getRequiredSpeed(float speed) {
            return 0.1F + (speed * 0.02F);
        }

    }

}