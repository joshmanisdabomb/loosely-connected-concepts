package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.item.Item;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Ingredient.TagEntry.class)
public interface IngredientTagEntryAccessor {

    @Accessor("tag")
    public TagKey<Item> getTag();

}
