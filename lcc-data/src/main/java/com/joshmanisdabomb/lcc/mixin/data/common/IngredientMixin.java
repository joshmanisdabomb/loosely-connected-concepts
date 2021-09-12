package com.joshmanisdabomb.lcc.mixin.data.common;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Ingredient.class)
public class IngredientMixin {

    @Redirect(method = "entryFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/tag/TagManager;getTag(Lnet/minecraft/util/registry/RegistryKey;Lnet/minecraft/util/Identifier;Ljava/util/function/Function;)Lnet/minecraft/tag/Tag;"))
    private static <T, E extends Exception> Tag<T> ignoreUninitialisedTags(TagManager tm, RegistryKey<? extends Registry<T>> key, Identifier id, Function<Identifier, E> exception) {
        try {
            return tm.getTag(key, id, exception);
        } catch (Exception e) {
            return TagRegistry.create(id, () -> tm.getOrCreateTagGroup(key));
        }
    }

}
