package com.joshmanisdabomb.lcc.recipe;

import com.google.gson.JsonObject;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TimeRiftRecipe extends SingleItemRecipe {

    public TimeRiftRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
        super(LCCRecipes.TIME_RIFT, LCCRecipes.time_rift, id, group, ingredient, result);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public boolean matches(ItemStack is) {
        return this.ingredient.test(is);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    public ItemStack getIcon() {
        return new ItemStack(LCCBlocks.time_rift);
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TimeRiftRecipe> {
        final IRecipeFactory<TimeRiftRecipe> factory;

        public Serializer(IRecipeFactory<TimeRiftRecipe> factory) {
            this.factory = factory;
        }

        public TimeRiftRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Ingredient ingredient;
            if (JSONUtils.isJsonArray(json, "ingredient")) {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            }

            String s1 = JSONUtils.getString(json, "result");
            int i = JSONUtils.getInt(json, "count");
            ItemStack itemstack = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(s1)), i);
            return this.factory.create(recipeId, s, ingredient, itemstack);
        }

        public TimeRiftRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack itemstack = buffer.readItemStack();
            return this.factory.create(recipeId, s, ingredient, itemstack);
        }

        public void write(PacketBuffer buffer, TimeRiftRecipe recipe) {
            buffer.writeString(recipe.group);
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.result);
        }

        public interface IRecipeFactory<T extends SingleItemRecipe> {
            T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient p_create_3_, ItemStack p_create_4_);
        }
    }

}