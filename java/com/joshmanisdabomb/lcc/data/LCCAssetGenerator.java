package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Consumer;
import java.util.function.Function;

public interface LCCAssetGenerator<O extends ForgeRegistryEntry<?>> {

    String getDefaultFolder();

    default ResourceLocation path(O object, Function<String, String> path, String folder) {
        ResourceLocation name = object.getRegistryName();
        return new ResourceLocation(name.getNamespace(), folder + "/" + path.apply(name.getPath()));
    }

    default ResourceLocation path(O object, Function<String, String> path) {
        return this.path(object, path, this.getDefaultFolder());
    }

    default ResourceLocation path(String path, String folder) {
        return new ResourceLocation(LCC.MODID, folder + "/" + path);
    }

    default ResourceLocation path(String path) {
        return this.path(path, this.getDefaultFolder());
    }

    default String name(O object) {
        return object.getRegistryName().getPath();
    }

    default <T extends O> void addAll(Consumer<T> adder, T... objects) {
        for (T value : objects) {
            adder.accept(value);
        }
    }

}
