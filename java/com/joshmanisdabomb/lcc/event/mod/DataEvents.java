package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public abstract class DataEvents {

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent e) {
        DataGenerator dg = e.getGenerator();
        if (e.includeClient()) {
            dg.addProvider(new LangData(dg));

            BlockAssetData blockAssetData;

            dg.addProvider(blockAssetData = new BlockAssetData(dg, e.getExistingFileHelper()));
            dg.addProvider(new ItemAssetData(dg, e.getExistingFileHelper(), blockAssetData));
        }
        if (e.includeServer()) {
            dg.addProvider(new RecipeData(dg));
            dg.addProvider(new LootTableData(dg));

            BlockTagData blockTagData;

            dg.addProvider(blockTagData = new BlockTagData(dg));
            dg.addProvider(new ItemTagData(dg, blockTagData));
            dg.addProvider(new FluidTagData(dg));

            dg.addProvider(new AdvancementData(dg));
        }
    }

}