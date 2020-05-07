package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;

public class ItemTagData extends ItemTagsProvider {

    public ItemTagData(DataGenerator dg) {
        super(dg);
    }

    @Override
    protected void registerTags() {
        this.registerForgeTags();
    }

    private void registerForgeTags() {
        this.getBuilder(Tags.Items.CHESTS_WOODEN)
            .add(LCCBlocks.classic_chest.asItem());

        this.getBuilder(Tags.Items.BEACON_PAYMENT)
            .add(LCCItems.ruby)
            .add(LCCItems.topaz)
            .add(LCCItems.sapphire)
            .add(LCCItems.amethyst)
            .add(LCCItems.neon);

        this.getBuilder(Tags.Items.GEMS)
            .add(LCCItems.ruby)
            .add(LCCItems.topaz)
            .add(LCCItems.sapphire)
            .add(LCCItems.amethyst);

        this.getBuilder(Tags.Items.NUGGETS)
            .add(LCCItems.uranium_nugget)
            .add(LCCItems.enriched_uranium_nugget)
            .add(LCCItems.neon_nugget);

        this.getBuilder(Tags.Items.ORES)
            .add(LCCBlocks.ruby_ore.asItem())
            .add(LCCBlocks.topaz_ore.asItem())
            .add(LCCBlocks.sapphire_ore.asItem())
            .add(LCCBlocks.amethyst_ore.asItem())
            .add(LCCBlocks.uranium_ore.asItem())
            .add(LCCBlocks.neon_ore.asItem());

        this.getBuilder(Tags.Items.STORAGE_BLOCKS)
            .add(LCCBlocks.ruby_storage.asItem())
            .add(LCCBlocks.topaz_storage.asItem())
            .add(LCCBlocks.sapphire_storage.asItem())
            .add(LCCBlocks.amethyst_storage.asItem())
            .add(LCCBlocks.uranium_storage.asItem())
            .add(LCCBlocks.neon_storage.asItem());
    }

}
