package com.joshmanisdabomb.lcc.mixin.data.common;

import com.google.gson.JsonObject;
import net.minecraft.data.DataCache;
import net.minecraft.data.server.RecipesProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.file.Path;

@Mixin(RecipesProvider.class)
public interface RecipesProviderAccessor {

    @Invoker("saveRecipe")
    static void saveRecipe(DataCache cache, JsonObject json, Path path) {
        throw new AssertionError();
    }

    @Invoker("saveRecipeAdvancement")
    static void saveRecipeAdvancement(DataCache cache, JsonObject json, Path path) {
        throw new AssertionError();
    }

}
