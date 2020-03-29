package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.data.RecipeData;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public abstract class DataEvents {

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent e) {
        DataGenerator dg = e.getGenerator();
        dg.addProvider(new RecipeData(dg));
    }

}