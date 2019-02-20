package com.joshmanisdabomb.lcc;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Comparator;
import java.util.HashMap;

public class LCCGroup extends ItemGroup {

    public final LCCGroupSort compar = new LCCGroupSort();

    public LCCGroup() {
        super("lcc");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(LCCBlocks.test_block, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.sort(compar);

        int categoryCount = 0;
        LCCGroupCategory c = null;
        for (int x = 0; x < items.size(); x++) {
            ItemStack is = items.get(x);
            if (is != ItemStack.EMPTY) {
                LCCGroupCategory c2 = LCCGroupCategory.getCategoryFromList(is.getItem().getRegistryName().toString(), is.getDamage());
                if (c == null) {
                    c = c2;
                    categoryCount++;
                } else if (c != c2) {
                    for (int y = 0; y < 9 - (categoryCount % 9); y++) {
                        items.add(x, ItemStack.EMPTY);
                    }
                    c = c2;
                    categoryCount = 0;
                } else {
                    categoryCount++;
                }
            }
        }
    }

    public static class LCCGroupSort implements Comparator<ItemStack> {

        public static void sortItems() {
            //Test Blocks
            setPosition(LCCBlocks.test_block, LCCGroupCategory.TESTING, 0);
            setPosition(LCCBlocks.test_block_2, LCCGroupCategory.TESTING, 1);
            setPosition(LCCBlocks.test_block_3, LCCGroupCategory.TESTING, 2);
            setPosition(LCCBlocks.test_block_4, LCCGroupCategory.TESTING, 3);
            setPosition(LCCBlocks.test_block_5, LCCGroupCategory.TESTING, 4);
            setPosition(LCCItems.test_item, LCCGroupCategory.TESTING, 5);

            //Resources
            setPosition(LCCBlocks.ruby_ore, LCCGroupCategory.RESOURCES, 0);
            setPosition(LCCItems.ruby, LCCGroupCategory.RESOURCES, 1);
            setPosition(LCCBlocks.ruby_storage, LCCGroupCategory.RESOURCES, 2);
            setPosition(LCCBlocks.topaz_ore, LCCGroupCategory.RESOURCES, 3);
            setPosition(LCCItems.topaz, LCCGroupCategory.RESOURCES, 4);
            setPosition(LCCBlocks.topaz_storage, LCCGroupCategory.RESOURCES, 5);
            setPosition(LCCBlocks.sapphire_ore, LCCGroupCategory.RESOURCES, 6);
            setPosition(LCCItems.sapphire, LCCGroupCategory.RESOURCES, 7);
            setPosition(LCCBlocks.sapphire_storage, LCCGroupCategory.RESOURCES, 8);
            setPosition(LCCBlocks.amethyst_ore, LCCGroupCategory.RESOURCES, 9);
            setPosition(LCCItems.amethyst, LCCGroupCategory.RESOURCES, 10);
            setPosition(LCCBlocks.amethyst_storage, LCCGroupCategory.RESOURCES, 11);
            setPosition(LCCBlocks.uranium_ore, LCCGroupCategory.RESOURCES, 12);
            setPosition(LCCItems.uranium_nugget, LCCGroupCategory.RESOURCES, 13);
            setPosition(LCCItems.uranium, LCCGroupCategory.RESOURCES, 14);
            setPosition(LCCBlocks.uranium_storage, LCCGroupCategory.RESOURCES, 15);
            setPosition(LCCItems.enriched_uranium_nugget, LCCGroupCategory.RESOURCES, 16);
            setPosition(LCCItems.enriched_uranium, LCCGroupCategory.RESOURCES, 17);
            setPosition(LCCBlocks.enriched_uranium_storage, LCCGroupCategory.RESOURCES, 18);

            //Nuclear
            setPosition(LCCBlocks.nuclear_waste, LCCGroupCategory.NUCLEAR, 0);
        }

        private static void setPosition(Object o, LCCGroupCategory cat, int sortVal) {
            setPosition(o, cat, sortVal, -1);
        }

        private static void setPosition(Object o, LCCGroupCategory cat, int sortVal, int metadata) {
            Item i = null;
            if (o instanceof Block) {
                i = Item.getItemFromBlock((Block)o);
            } else if (o instanceof Item) {
                i = (Item)o;
            }
            cat.sortValueList.put(i.getRegistryName().toString() + "|" + metadata, sortVal);
        }

        @Override
        public int compare(ItemStack is1, ItemStack is2) {
            if (is1 == ItemStack.EMPTY || is2 == ItemStack.EMPTY) {
                return 0;
            }

            LCCGroupCategory category1 = LCCGroupCategory.getCategoryFromList(is1.getItem().getRegistryName().toString(), is1.getDamage());
            LCCGroupCategory category2 = LCCGroupCategory.getCategoryFromList(is2.getItem().getRegistryName().toString(), is2.getDamage());
            if (category1.ordinal() != category2.ordinal()) {
                return category1.ordinal() - category2.ordinal();
            }

            int sortValue1 = LCCGroupCategory.getSortValueFromList(is1.getItem().getRegistryName().toString(), is1.getDamage());
            int sortValue2 = LCCGroupCategory.getSortValueFromList(is2.getItem().getRegistryName().toString(), is2.getDamage());
            if (sortValue1 != sortValue2) {
                return sortValue1 - sortValue2;
            }

            return 0;
        }

    }

    public enum LCCGroupCategory {

        RESOURCES,
        NUCLEAR,
        TESTING;

        protected HashMap<String, Integer> sortValueList = new HashMap<String, Integer>();

        public static LCCGroupCategory getCategoryFromList(String itemId, int metadata) {
            for (LCCGroupCategory cat : LCCGroupCategory.values()) {
                if (cat.sortValueList.containsKey(itemId + "|" + -1) || cat.sortValueList.containsKey(itemId + "|" + metadata)) {
                    return cat;
                }
            }
            throw new IllegalArgumentException("Something in the Loosely Connected Concepts tab doesn't have a category.");
        }

        public static int getSortValueFromList(String itemId, int metadata) {
            for (LCCGroupCategory cat : LCCGroupCategory.values()) {
                if (cat.sortValueList.containsKey(itemId + "|" + -1)) {
                    return cat.sortValueList.get(itemId + "|" + -1);
                } else if (cat.sortValueList.containsKey(itemId + "|" + metadata)) {
                    return cat.sortValueList.get(itemId + "|" + metadata);
                }
            }
            throw new IllegalArgumentException("Something in the Loosely Connected Concepts tab doesn't have an upper sort value.");
        }

    }

}
