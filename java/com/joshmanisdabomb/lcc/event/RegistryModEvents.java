package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.registry.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class RegistryModEvents {

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
    public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> e) {
        LCCEntities.init(e);
        e.getRegistry().registerAll(LCCEntities.all.toArray(new EntityType<?>[0]));
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

}
