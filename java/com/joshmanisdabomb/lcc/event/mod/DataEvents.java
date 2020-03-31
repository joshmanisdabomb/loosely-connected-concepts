package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.data.BlockAssetData;
import com.joshmanisdabomb.lcc.data.LangData;
import com.joshmanisdabomb.lcc.data.LootTableData;
import com.joshmanisdabomb.lcc.data.RecipeData;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public abstract class DataEvents {

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent e) {
        DataGenerator dg = e.getGenerator();
        if (e.includeClient()) {
            dg.addProvider(new LangData(dg));
            dg.addProvider(new BlockAssetData(dg, e.getExistingFileHelper()));
            //dg.addProvider(new ItemAssetData(dg));
        }
        if (e.includeServer()) {
            dg.addProvider(new RecipeData(dg));
            dg.addProvider(new LootTableData(dg));
        }
    }

}