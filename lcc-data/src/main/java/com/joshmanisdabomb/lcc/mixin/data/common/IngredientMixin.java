package com.joshmanisdabomb.lcc.mixin.data.common;

import com.joshmanisdabomb.lcc.data.recipe.IdentifiableIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements IdentifiableIngredient {

    @Shadow @Final
    private Ingredient.Entry[] entries;

    @NotNull
    @Override
    public List<ItemStack> getItemStacks() {
        return Arrays.stream(entries).filter(e -> e instanceof Ingredient.StackEntry).flatMap(s -> s.getStacks().stream()).toList();
    }

    @NotNull
    @Override
    public List<TagKey<Item>> getTags() {
        return Arrays.stream(entries).filter(e -> e instanceof Ingredient.TagEntry).map(t -> ((IngredientTagEntryAccessor)t).getTag()).toList();
    }

    @NotNull
    @Override
    public List<ItemStack> getTagStacks() {
        return Arrays.stream(entries).filter(e -> e instanceof Ingredient.TagEntry).flatMap(s -> s.getStacks().stream()).toList();
    }

}
