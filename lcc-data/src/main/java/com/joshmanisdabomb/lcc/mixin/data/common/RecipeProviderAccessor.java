package com.joshmanisdabomb.lcc.mixin.data.common;

import com.google.gson.JsonObject;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.file.Path;

@Mixin(RecipeProvider.class)
public interface RecipeProviderAccessor {

    @Invoker("saveRecipe")
    static void saveRecipe(DataWriter writer, JsonObject json, Path path) {
        throw new AssertionError();
    }

    @Invoker("saveRecipeAdvancement")
    static void saveRecipeAdvancement(DataWriter writer, JsonObject json, Path path) {
        throw new AssertionError();
    }

}
