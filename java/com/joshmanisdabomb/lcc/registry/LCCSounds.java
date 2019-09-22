package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;

public abstract class LCCSounds {

    public static final ArrayList<SoundEvent> all = new ArrayList<>();

    public static SoundEvent block_hydrated_soul_sand_break;
    public static SoundEvent block_hydrated_soul_sand_fall;
    public static SoundEvent block_hydrated_soul_sand_hit;
    public static SoundEvent block_hydrated_soul_sand_place;
    public static SoundEvent block_hydrated_soul_sand_step;
    public static SoundEvent block_hydrated_soul_sand_jump;

    public static SoundEvent block_bounce_pad_jump;

    public static SoundEvent block_cog_break;

    //Block Sound Types
    public static SoundType cog_multiple;
    public static SoundType hydrated_soul_sand;

    public static void init() {
        ResourceLocation loc;
        all.add(block_cog_break = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.cog.break")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_break = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.break")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_fall = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.fall")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_hit = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.hit")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_place = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.place")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_step = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.step")).setRegistryName(loc));
        all.add(block_hydrated_soul_sand_jump = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.hydrated_soul_sand.jump")).setRegistryName(loc));
        all.add(block_bounce_pad_jump = new SoundEvent(loc = new ResourceLocation(LCC.MODID, "block.bounce_pad.jump")).setRegistryName(loc));

        //Block Sound Types
        cog_multiple = new SoundType(1.0F, 1.0F, block_cog_break, SoundType.STONE.getStepSound(), SoundType.STONE.getPlaceSound(), SoundType.STONE.getHitSound(), SoundType.STONE.getFallSound());
        hydrated_soul_sand = new SoundType(1.0F, 1.0F, block_hydrated_soul_sand_break, block_hydrated_soul_sand_step, block_hydrated_soul_sand_place, block_hydrated_soul_sand_hit, block_hydrated_soul_sand_fall);
    }

}
