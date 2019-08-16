package com.joshmanisdabomb.lcc.event.mod;

import com.joshmanisdabomb.lcc.block.TintedBlock;
import com.joshmanisdabomb.lcc.item.TintedItem;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Stream;

public abstract class ColorEvents {

    @SubscribeEvent
    public static void onBlockColors(final ColorHandlerEvent.Block e) {
        e.getBlockColors().register((state, world, pos, tintIndex) -> ((TintedBlock)state.getBlock()).getBlockTintColor(state, world, pos, tintIndex), LCCBlocks.all.stream().filter(block -> block instanceof TintedBlock).toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void onItemColors(final ColorHandlerEvent.Item e) {
        e.getItemColors().register((stack, tintIndex) -> ((TintedBlock)((BlockItem)stack.getItem()).getBlock()).getItemTintColor(stack, tintIndex), LCCBlocks.allItem.stream().filter(blockItem -> blockItem.getBlock() instanceof TintedBlock).toArray(BlockItem[]::new));
        e.getItemColors().register((stack, tintIndex) -> ((TintedItem)stack.getItem()).getItemTintColor(stack, tintIndex), LCCItems.all.stream().filter(item -> item instanceof TintedItem).toArray(Item[]::new));
    }

}
