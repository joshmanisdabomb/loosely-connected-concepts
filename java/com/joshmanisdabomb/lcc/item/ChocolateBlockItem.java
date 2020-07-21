package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.block.ChocolateBlock;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;

import java.util.Map;

public class ChocolateBlockItem extends BlockItem {

    private final ChocolateBlock.Type type;

    public ChocolateBlockItem(ChocolateBlock.Type type, Properties builder) {
        super(LCCBlocks.chocolate, builder);
        this.type = type;
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

    public ChocolateBlock.Type getType() {
        return this.type;
    }

}
