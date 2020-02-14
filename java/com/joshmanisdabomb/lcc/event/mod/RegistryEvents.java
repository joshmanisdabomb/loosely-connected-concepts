package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.registry.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class RegistryEvents {

    @SubscribeEvent
    public static void onBlockRegistry(final RegistryEvent.Register<Block> e) {
        LCCBlocks.init(e);
        e.getRegistry().registerAll(LCCBlocks.all.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> e) {
        LCCItems.init(e);
        e.getRegistry().registerAll(LCCBlocks.allItem.toArray(new BlockItem[0]));
        e.getRegistry().registerAll(LCCItems.all.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onFluidRegistry(final RegistryEvent.Register<Fluid> e) {
        LCCFluids.init(e);
        e.getRegistry().registerAll(LCCFluids.all.toArray(new Fluid[0]));
    }

    @SubscribeEvent
    public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> e) {
        LCCEntities.init(e);
        e.getRegistry().registerAll(LCCEntities.all.toArray(new EntityType<?>[0]));
    }

    @SubscribeEvent
    public static void onBiomeRegistry(final RegistryEvent.Register<Biome> e) {
        LCCBiomes.init(e);
        e.getRegistry().registerAll(LCCBiomes.all.toArray(new Biome[0]));
    }

    @SubscribeEvent
    public static void onDimensionRegistry(final RegistryEvent.Register<ModDimension> e) {
        LCCDimensions.init(e);
        e.getRegistry().registerAll(LCCDimensions.all.toArray(new ModDimension[0]));
    }

    @SubscribeEvent
    public static void onBiomeProviderRegistry(final RegistryEvent.Register<BiomeProviderType<?, ?>> e) {
        LCCDimensions.initBiomeProviders(e);
        e.getRegistry().registerAll(LCCDimensions.allBiomeProviders.toArray(new BiomeProviderType[0]));
    }

    @SubscribeEvent
    public static void onPotionRegistry(final RegistryEvent.Register<Effect> e) {
        LCCEffects.init(e);
        e.getRegistry().registerAll(LCCEffects.all.toArray(new Effect[0]));
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e) {
        LCCTileEntities.init(e);
        e.getRegistry().registerAll(LCCTileEntities.all.toArray(new TileEntityType<?>[0]));
    }

    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> e) {
        LCCContainers.init(e);
        e.getRegistry().registerAll(LCCContainers.all.toArray(new ContainerType<?>[0]));
    }

    @SubscribeEvent
    public static void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> e) {
        LCCFeatures.init(e);
        e.getRegistry().registerAll(LCCFeatures.all.toArray(new Feature<?>[0]));
    }

    @SubscribeEvent
    public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> e) {
        LCCParticles.init(e);
        e.getRegistry().registerAll(LCCParticles.all.toArray(new ParticleType<?>[0]));
    }

    @SubscribeEvent
    public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().registerAll(LCCSounds.all.toArray(new SoundEvent[0]));
    }

    @SubscribeEvent
    public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> e) {
        LCCRecipes.init(e);
        e.getRegistry().registerAll(LCCRecipes.all.toArray(new IRecipeSerializer<?>[0]));
    }

}
