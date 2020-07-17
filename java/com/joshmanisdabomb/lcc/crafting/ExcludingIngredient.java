package com.joshmanisdabomb.lcc.crafting;

import javax.annotation.Nullable;

import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ExcludingIngredient extends Ingredient {

    private final Ingredient sub;
    private final Collection<Item> excludes;

    public ExcludingIngredient(Ingredient sub, Collection<Item> excludes) {
        super(Arrays.stream(sub.getMatchingStacks()).filter(i -> excludes.stream().noneMatch(e -> i.getItem() == e)).map(SingleItemList::new));
        this.sub = sub;
        this.excludes = excludes;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null) return false;
        if (excludes.stream().anyMatch(i -> i == input.getItem())) return false;
        return super.test(input);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        return Arrays.stream(sub.getMatchingStacks()).filter(i -> excludes.stream().noneMatch(e -> i.getItem() == e)).toArray(ItemStack[]::new);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(ExcludingIngredient.Serializer.INSTANCE).toString());
        json.add("ingredient", sub.serialize());

        JsonArray excludes = new JsonArray();
        this.excludes.forEach(e -> excludes.add(e.getRegistryName().toString()));
        json.add("excludes", excludes);

        return json;
    }

    public static class Serializer implements IIngredientSerializer<ExcludingIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ExcludingIngredient parse(PacketBuffer buffer) {
            Ingredient sub = Ingredient.read(buffer);
            ArrayList<Item> excludes = new ArrayList<>();

            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++) {
                excludes.add(ForgeRegistries.ITEMS.getValue(ResourceLocation.create(buffer.readString(), ':')));
            }

            return new ExcludingIngredient(sub, excludes);
        }

        @Override
        public ExcludingIngredient parse(JsonObject json) {
            Collection<Item> excludes = StreamSupport.stream(json.getAsJsonArray("excludes").spliterator(), false).map(i -> ForgeRegistries.ITEMS.getValue(ResourceLocation.create(i.getAsString(), ':'))).collect(Collectors.toList());
            return new ExcludingIngredient(Ingredient.deserialize(json.getAsJsonObject("ingredient")), excludes);
        }

        @Override
        public void write(PacketBuffer buffer, ExcludingIngredient ingredient) {
            CraftingHelper.write(buffer, ingredient.sub);
            buffer.writeVarInt(ingredient.excludes.size());
            ingredient.excludes.forEach(s -> buffer.writeString(s.getRegistryName().toString()));
        }
    }
}