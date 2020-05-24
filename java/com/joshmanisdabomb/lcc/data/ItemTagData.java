package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ItemTagData extends ItemTagsProvider {

    static final ItemTags.Wrapper BEACON_BASE_BLOCKS_UPCOMING = new ItemTags.Wrapper(new ResourceLocation("minecraft", "beacon_base_blocks"));
    static final ItemTags.Wrapper FIRE_UPCOMING = new ItemTags.Wrapper(new ResourceLocation("minecraft", "fire"));

    private final BlockTagData blocks;

    public ItemTagData(DataGenerator dg, BlockTagData blocks) {
        super(dg);
        this.blocks = blocks;
    }

    @Override
    protected void registerTags() {
        LCCTags.ALL.forEach(w -> this.copy(w.block, w.item));
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTagData.BEACON_BASE_BLOCKS_UPCOMING, ItemTagData.BEACON_BASE_BLOCKS_UPCOMING);
        this.copy(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        this.copy(Tags.Blocks.ORES, Tags.Items.ORES);
        this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        this.registerForgeTags();

        this.getBuilder(LCCTags.RAINBOW_EQUIPMENT)
            .add(LCCItems.vivid_sword)
            .add(LCCItems.vivid_pickaxe)
            .add(LCCItems.vivid_shovel)
            .add(LCCItems.vivid_axe)
            .add(LCCItems.vivid_hoe)
            .add(LCCItems.red_candy_cane_sword)
            .add(LCCItems.red_candy_cane_pickaxe)
            .add(LCCItems.red_candy_cane_shovel)
            .add(LCCItems.red_candy_cane_axe)
            .add(LCCItems.red_candy_cane_hoe)
            .add(LCCItems.green_candy_cane_sword)
            .add(LCCItems.green_candy_cane_pickaxe)
            .add(LCCItems.green_candy_cane_shovel)
            .add(LCCItems.green_candy_cane_axe)
            .add(LCCItems.green_candy_cane_hoe)
            .add(LCCItems.blue_candy_cane_sword)
            .add(LCCItems.blue_candy_cane_pickaxe)
            .add(LCCItems.blue_candy_cane_shovel)
            .add(LCCItems.blue_candy_cane_axe)
            .add(LCCItems.blue_candy_cane_hoe)
            .add(LCCItems.neon_sword)
            .add(LCCItems.neon_pickaxe)
            .add(LCCItems.neon_shovel)
            .add(LCCItems.neon_axe)
            .add(LCCItems.neon_hoe)
            .add(LCCItems.neon_helmet)
            .add(LCCItems.neon_chestplate)
            .add(LCCItems.neon_leggings)
            .add(LCCItems.neon_boots)
            .ordered(true);
    }

    private void registerForgeTags() {
        this.getBuilder(Tags.Items.CHESTS_WOODEN)
            .add(LCCBlocks.classic_chest.asItem())
            .ordered(true);

        this.getBuilder(Tags.Items.BEACON_PAYMENT)
            .add(LCCItems.ruby)
            .add(LCCItems.topaz)
            .add(LCCItems.sapphire)
            .add(LCCItems.amethyst)
            .add(LCCItems.neon)
            .ordered(true);

        this.getBuilder(Tags.Items.GEMS)
            .add(LCCItems.ruby)
            .add(LCCItems.topaz)
            .add(LCCItems.sapphire)
            .add(LCCItems.amethyst)
            .ordered(true);

        this.getBuilder(Tags.Items.NUGGETS)
            .add(LCCItems.uranium_nugget)
            .add(LCCItems.enriched_uranium_nugget)
            .add(LCCItems.neon_nugget)
            .ordered(true);

        this.getBuilder(Tags.Items.ORES)
            .add(LCCBlocks.ruby_ore.asItem())
            .add(LCCBlocks.topaz_ore.asItem())
            .add(LCCBlocks.sapphire_ore.asItem())
            .add(LCCBlocks.amethyst_ore.asItem())
            .add(LCCBlocks.uranium_ore.asItem())
            .add(LCCBlocks.neon_ore.asItem())
            .ordered(true);

        this.getBuilder(Tags.Items.STORAGE_BLOCKS)
            .add(LCCBlocks.ruby_storage.asItem())
            .add(LCCBlocks.topaz_storage.asItem())
            .add(LCCBlocks.sapphire_storage.asItem())
            .add(LCCBlocks.amethyst_storage.asItem())
            .add(LCCBlocks.uranium_storage.asItem())
            .add(LCCBlocks.neon_storage.asItem())
            .ordered(true);
    }

}
