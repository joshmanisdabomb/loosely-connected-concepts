package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;

import java.util.Map;

public class ComputingBlockItem extends BlockItem implements TintedItem {

    private final DyeColor color;
    private final ComputingModule.Type module;

    public ComputingBlockItem(ComputingModule.Type module, DyeColor color, Properties properties) {
        super(LCCBlocks.computing, properties);
        this.color = color;
        this.module = module;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
        }
    }

    @Override
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }

    @Override
    public void addToBlockToItemMap(Map<Block, Item> map, Item i) {

    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> map, Item i) {

    }

    @Override
    public int getItemTintColor(ItemStack stack, int tintIndex) {
        return this.color.getColorValue();
    }

    public DyeColor getColor() {
        return this.color;
    }

    public ComputingModule.Type getModule() {
        return this.module;
    }

}
