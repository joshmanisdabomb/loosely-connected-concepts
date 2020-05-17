package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockTagData extends BlockTagsProvider {

    static final BlockTags.Wrapper BEACON_BASE_BLOCKS_UPCOMING = new BlockTags.Wrapper(new ResourceLocation("minecraft", "beacon_base_blocks"));
    static final BlockTags.Wrapper FIRE_UPCOMING = new BlockTags.Wrapper(new ResourceLocation("minecraft", "fire"));

    public BlockTagData(DataGenerator dg) {
        super(dg);
    }

    @Override
    protected void registerTags() {
        this.registerMinecraftTags();
        this.registerForgeTags();

        this.getBuilder(LCCTags.COLORED_CANDY_CANE.block)
            .add(LCCBlocks.candy_cane_red)
            .add(LCCBlocks.candy_cane_green)
            .add(LCCBlocks.candy_cane_blue);

        this.getBuilder(LCCTags.COLORED_CANDY_CANE_COATING.block)
            .add(LCCBlocks.candy_cane_coating_red)
            .add(LCCBlocks.candy_cane_coating_green)
            .add(LCCBlocks.candy_cane_coating_blue);

        this.getBuilder(LCCTags.REFINED_COLORED_CANDY_CANE.block)
            .add(LCCBlocks.refined_candy_cane_red)
            .add(LCCBlocks.refined_candy_cane_green)
            .add(LCCBlocks.refined_candy_cane_blue);

        this.getBuilder(LCCTags.REFINED_COLORED_CANDY_CANE_COATING.block)
            .add(LCCBlocks.refined_candy_cane_coating_red)
            .add(LCCBlocks.refined_candy_cane_coating_green)
            .add(LCCBlocks.refined_candy_cane_coating_blue);

        this.getBuilder(LCCTags.COLORED_CANDY_CANES.block)
            .add(LCCTags.COLORED_CANDY_CANE.block)
            .add(LCCTags.COLORED_CANDY_CANE_COATING.block)
            .add(LCCTags.REFINED_COLORED_CANDY_CANE.block)
            .add(LCCTags.REFINED_COLORED_CANDY_CANE_COATING.block);

        this.getBuilder(LCCTags.STRIPPED_CANDY_CANES.block)
            .add(LCCBlocks.stripped_candy_cane)
            .add(LCCBlocks.stripped_candy_cane_coating)
            .add(LCCBlocks.refined_stripped_candy_cane)
            .add(LCCBlocks.refined_stripped_candy_cane_coating)
            .add(LCCBlocks.candy_cane_block);

        this.getBuilder(LCCTags.CHANNELITE.block)
            .add(LCCBlocks.channelite.values().toArray(new Block[0]));

        this.getBuilder(LCCTags.CHANNELITE_SOURCE.block)
            .add(LCCBlocks.sparkling_channelite_source.values().toArray(new Block[0]))
            .add(LCCBlocks.twilight_channelite_source.values().toArray(new Block[0]));

        this.getBuilder(LCCTags.VIVID_LOGS.block)
            .add(LCCBlocks.vivid_log)
            .add(LCCBlocks.vivid_wood)
            .add(LCCBlocks.stripped_vivid_log)
            .add(LCCBlocks.stripped_vivid_wood);

        this.getBuilder(LCCTags.RAINBOW_REQUIRED.block)
            .add(LCCBlocks.twilight_stone)
            .add(LCCBlocks.twilight_cobblestone)
            .add(LCCTags.CHANNELITE.block)
            .add(LCCTags.CHANNELITE_SOURCE.block)
            .add(LCCBlocks.neon_ore)
            .add(LCCBlocks.neon_storage);

        this.getBuilder(LCCTags.RAINBOW_EFFECTIVE.block)
            .add(LCCTags.RAINBOW_REQUIRED.block)
            .add(LCCBlocks.rainbow_grass_block)
            .add(LCCBlocks.sugar_grass_block)
            .add(LCCBlocks.star_plating)
            .add(LCCBlocks.sparkling_grass_block.values().toArray(new Block[0]))
            .add(LCCBlocks.sparkling_dirt)
            .add(LCCTags.COLORED_CANDY_CANES.block)
            .add(LCCTags.VIVID_LOGS.block)
            .add(LCCBlocks.vivid_planks)
            .add(LCCBlocks.vivid_leaves)
            .add(LCCBlocks.vivid_stairs)
            .add(LCCBlocks.vivid_slab)
            .add(LCCBlocks.vivid_door)
            .add(LCCBlocks.vivid_pressure_plate)
            .add(LCCBlocks.vivid_button)
            .add(LCCBlocks.vivid_fence)
            .add(LCCBlocks.vivid_fence_gate)
            .add(LCCBlocks.vivid_trapdoor);

        this.getBuilder(LCCTags.WASTELAND_REQUIRED.block);

        this.getBuilder(LCCTags.WASTELAND_EFFECTIVE.block)
            .add(LCCTags.WASTELAND_REQUIRED.block)
            .add(LCCBlocks.cracked_mud);
    }

    private void registerMinecraftTags() {
        this.getBuilder(BEACON_BASE_BLOCKS_UPCOMING)
            .add(LCCBlocks.ruby_storage)
            .add(LCCBlocks.topaz_storage)
            .add(LCCBlocks.sapphire_storage)
            .add(LCCBlocks.amethyst_storage)
            .add(LCCBlocks.neon_storage);

        this.getBuilder(BlockTags.DRAGON_IMMUNE)
            .add(LCCBlocks.nuclear_waste)
            .add(LCCBlocks.crying_obsidian)
            .add(LCCBlocks.glowing_obsidian);

        this.getBuilder(BlockTags.WITHER_IMMUNE)
            .add(LCCBlocks.nuclear_waste);

        this.getBuilder(BlockTags.ENDERMAN_HOLDABLE)
            .add(LCCBlocks.cracked_mud)
            .add(LCCBlocks.rainbow_grass_block)
            .add(LCCBlocks.sugar_grass_block)
            .add(LCCBlocks.star_plating)
            .add(LCCBlocks.sparkling_grass_block.values().toArray(new Block[0]));

        this.getBuilder(FIRE_UPCOMING)
            .add(LCCBlocks.nuclear_fire);

        this.getBuilder(BlockTags.FLOWER_POTS)
            .add(LCCBlocks.potted_classic_cyan_flower)
            .add(LCCBlocks.potted_classic_rose)
            .add(LCCBlocks.potted_classic_sapling);

        this.getBuilder(BlockTags.SAPLINGS)
            .add(LCCBlocks.classic_sapling);

        this.getBuilder(BlockTags.SMALL_FLOWERS)
            .add(LCCBlocks.classic_rose)
            .add(LCCBlocks.classic_cyan_flower);

        this.getBuilder(BlockTags.LOGS)
            .add(LCCTags.VIVID_LOGS.block);

        this.getBuilder(BlockTags.PLANKS)
            .add(LCCBlocks.vivid_planks);

        this.getBuilder(BlockTags.WOODEN_BUTTONS)
            .add(LCCBlocks.vivid_button);

        this.getBuilder(BlockTags.WOODEN_DOORS)
            .add(LCCBlocks.vivid_door);

        this.getBuilder(BlockTags.WOODEN_FENCES)
            .add(LCCBlocks.vivid_fence);

        this.getBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
            .add(LCCBlocks.vivid_pressure_plate);

        this.getBuilder(BlockTags.WOODEN_SLABS)
            .add(LCCBlocks.vivid_slab);

        this.getBuilder(BlockTags.WOODEN_STAIRS)
            .add(LCCBlocks.vivid_stairs);

        this.getBuilder(BlockTags.WOODEN_TRAPDOORS)
            .add(LCCBlocks.vivid_trapdoor);
    }

    private void registerForgeTags() {
        this.getBuilder(Tags.Blocks.CHESTS_WOODEN)
            .add(LCCBlocks.classic_chest);

        this.getBuilder(Tags.Blocks.FENCE_GATES_WOODEN)
            .add(LCCBlocks.vivid_fence_gate);

        this.getBuilder(Tags.Blocks.FENCES_WOODEN)
            .add(LCCBlocks.vivid_fence);

        this.getBuilder(Tags.Blocks.ORES)
            .add(LCCBlocks.ruby_ore)
            .add(LCCBlocks.topaz_ore)
            .add(LCCBlocks.sapphire_ore)
            .add(LCCBlocks.amethyst_ore)
            .add(LCCBlocks.uranium_ore)
            .add(LCCBlocks.neon_ore);

        this.getBuilder(Tags.Blocks.STORAGE_BLOCKS)
            .add(LCCBlocks.ruby_storage)
            .add(LCCBlocks.topaz_storage)
            .add(LCCBlocks.sapphire_storage)
            .add(LCCBlocks.amethyst_storage)
            .add(LCCBlocks.uranium_storage)
            .add(LCCBlocks.enriched_uranium_storage)
            .add(LCCBlocks.neon_storage);
    }

    Map<Tag<Block>, Collection<Tag.ITagEntry<Block>>> getEntries() {
        return this.tagToBuilder.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, b -> b.getValue().build(null).getEntries()));
    }

}
