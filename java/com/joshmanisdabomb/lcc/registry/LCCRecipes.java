package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCRecipes {

    public static final ArrayList<IRecipeSerializer<?>> all = new ArrayList<>();

    public static final IRecipeType<TimeRiftRecipe> TIME_RIFT = new IRecipeType<TimeRiftRecipe>() {
        public String toString() {
            return "time_rift";
        }
    };

    public static IRecipeSerializer<TimeRiftRecipe> time_rift;

    public static void init(RegistryEvent<IRecipeSerializer<?>> e) {
        all.add(time_rift = (IRecipeSerializer<TimeRiftRecipe>)new TimeRiftRecipe.Serializer(TimeRiftRecipe::new).setRegistryName(LCC.MODID, "time_rift"));
    }

}