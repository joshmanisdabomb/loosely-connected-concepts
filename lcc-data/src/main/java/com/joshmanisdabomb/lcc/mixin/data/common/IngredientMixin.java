package com.joshmanisdabomb.lcc.mixin.data.common;

import com.joshmanisdabomb.lcc.data.recipe.IdentifiableIngredient;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManager;
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

    @Redirect(method = "entryFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/tag/TagManager;getTag(Lnet/minecraft/util/registry/RegistryKey;Lnet/minecraft/util/Identifier;Ljava/util/function/Function;)Lnet/minecraft/tag/Tag;"))
    private static <T, E extends Exception> Tag<T> ignoreUninitialisedTags(TagManager tm, RegistryKey<? extends Registry<T>> key, Identifier id, Function<Identifier, E> exception) {
        try {
            return tm.getTag(key, id, exception);
        } catch (Exception e) {
            return TagFactory.of(() -> tm.getOrCreateTagGroup(key)).create(id);
        }
    }

    @NotNull
    @Override
    public List<ItemStack> getItemStacks() {
        return Arrays.stream(entries).filter(e -> e instanceof Ingredient.StackEntry).flatMap(s -> s.getStacks().stream()).toList();
    }

    @NotNull
    @Override
    public List<Tag<Item>> getTags() {
        return Arrays.stream(entries).filter(e -> e instanceof Ingredient.TagEntry).map(t -> ((IngredientTagEntryAccessor)t).getTag()).toList();
    }

}
