package com.joshmanisdabomb.lcc.registry;

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

        int itemCount = 0;
        for (LCCGroupCategory c : LCCGroupCategory.values()) {
            itemCount += c.sortValueList.size();
            int emptySpaces = 9 - (((c.sortValueList.size() - 1) % 9) + 1);
            for (int i = 0; i < emptySpaces; i++) {
                items.add(itemCount++, ItemStack.EMPTY);
            }
        }
    }

    public static class LCCGroupSort implements Comparator<ItemStack> {

        public static void sortItems() {
            //Test Blocks
            setPosition(LCCBlocks.test_block, LCCGroupCategory.TESTING, 0);
            setPosition(LCCBlocks.test_block_2, LCCGroupCategory.TESTING, 10);
            setPosition(LCCBlocks.test_block_3, LCCGroupCategory.TESTING, 20);
            setPosition(LCCBlocks.test_block_4, LCCGroupCategory.TESTING, 30);
            setPosition(LCCBlocks.test_block_5, LCCGroupCategory.TESTING, 40);
            setPosition(LCCItems.test_item, LCCGroupCategory.TESTING, 50);

            //Resources
            setPosition(LCCBlocks.ruby_ore, LCCGroupCategory.RESOURCES, 0);
            setPosition(LCCItems.ruby, LCCGroupCategory.RESOURCES, 10);
            setPosition(LCCBlocks.ruby_storage, LCCGroupCategory.RESOURCES, 20);
            setPosition(LCCBlocks.topaz_ore, LCCGroupCategory.RESOURCES, 30);
            setPosition(LCCItems.topaz, LCCGroupCategory.RESOURCES, 40);
            setPosition(LCCBlocks.topaz_storage, LCCGroupCategory.RESOURCES, 50);
            setPosition(LCCBlocks.sapphire_ore, LCCGroupCategory.RESOURCES, 60);
            setPosition(LCCItems.sapphire, LCCGroupCategory.RESOURCES, 70);
            setPosition(LCCBlocks.sapphire_storage, LCCGroupCategory.RESOURCES, 80);
            setPosition(LCCBlocks.amethyst_ore, LCCGroupCategory.RESOURCES, 90);
            setPosition(LCCItems.amethyst, LCCGroupCategory.RESOURCES, 100);
            setPosition(LCCBlocks.amethyst_storage, LCCGroupCategory.RESOURCES, 110);
            setPosition(LCCBlocks.uranium_ore, LCCGroupCategory.RESOURCES, 120);
            setPosition(LCCItems.uranium_nugget, LCCGroupCategory.RESOURCES, 130);
            setPosition(LCCItems.uranium, LCCGroupCategory.RESOURCES, 140);
            setPosition(LCCBlocks.uranium_storage, LCCGroupCategory.RESOURCES, 150);
            setPosition(LCCItems.enriched_uranium_nugget, LCCGroupCategory.RESOURCES, 160);
            setPosition(LCCItems.enriched_uranium, LCCGroupCategory.RESOURCES, 170);
            setPosition(LCCBlocks.enriched_uranium_storage, LCCGroupCategory.RESOURCES, 180);

            //Tools
            setPosition(LCCItems.ruby_sword, LCCGroupCategory.TOOLS, 0);
            setPosition(LCCItems.ruby_pickaxe, LCCGroupCategory.TOOLS, 10);
            setPosition(LCCItems.ruby_shovel, LCCGroupCategory.TOOLS, 20);
            setPosition(LCCItems.ruby_axe, LCCGroupCategory.TOOLS, 30);
            setPosition(LCCItems.ruby_hoe, LCCGroupCategory.TOOLS, 40);
            setPosition(LCCItems.ruby_helmet, LCCGroupCategory.TOOLS, 50);
            setPosition(LCCItems.ruby_chestplate, LCCGroupCategory.TOOLS, 60);
            setPosition(LCCItems.ruby_leggings, LCCGroupCategory.TOOLS, 70);
            setPosition(LCCItems.ruby_boots, LCCGroupCategory.TOOLS, 80);

            setPosition(LCCItems.topaz_sword, LCCGroupCategory.TOOLS, 100);
            setPosition(LCCItems.topaz_pickaxe, LCCGroupCategory.TOOLS, 110);
            setPosition(LCCItems.topaz_shovel, LCCGroupCategory.TOOLS, 120);
            setPosition(LCCItems.topaz_axe, LCCGroupCategory.TOOLS, 130);
            setPosition(LCCItems.topaz_hoe, LCCGroupCategory.TOOLS, 140);
            setPosition(LCCItems.topaz_helmet, LCCGroupCategory.TOOLS, 150);
            setPosition(LCCItems.topaz_chestplate, LCCGroupCategory.TOOLS, 160);
            setPosition(LCCItems.topaz_leggings, LCCGroupCategory.TOOLS, 170);
            setPosition(LCCItems.topaz_boots, LCCGroupCategory.TOOLS, 180);

            setPosition(LCCItems.emerald_sword, LCCGroupCategory.TOOLS, 200);
            setPosition(LCCItems.emerald_pickaxe, LCCGroupCategory.TOOLS, 210);
            setPosition(LCCItems.emerald_shovel, LCCGroupCategory.TOOLS, 220);
            setPosition(LCCItems.emerald_axe, LCCGroupCategory.TOOLS, 230);
            setPosition(LCCItems.emerald_hoe, LCCGroupCategory.TOOLS, 240);
            setPosition(LCCItems.emerald_helmet, LCCGroupCategory.TOOLS, 250);
            setPosition(LCCItems.emerald_chestplate, LCCGroupCategory.TOOLS, 260);
            setPosition(LCCItems.emerald_leggings, LCCGroupCategory.TOOLS, 270);
            setPosition(LCCItems.emerald_boots, LCCGroupCategory.TOOLS, 280);

            setPosition(LCCItems.sapphire_sword, LCCGroupCategory.TOOLS, 300);
            setPosition(LCCItems.sapphire_pickaxe, LCCGroupCategory.TOOLS, 310);
            setPosition(LCCItems.sapphire_shovel, LCCGroupCategory.TOOLS, 320);
            setPosition(LCCItems.sapphire_axe, LCCGroupCategory.TOOLS, 330);
            setPosition(LCCItems.sapphire_hoe, LCCGroupCategory.TOOLS, 340);
            setPosition(LCCItems.sapphire_helmet, LCCGroupCategory.TOOLS, 350);
            setPosition(LCCItems.sapphire_chestplate, LCCGroupCategory.TOOLS, 360);
            setPosition(LCCItems.sapphire_leggings, LCCGroupCategory.TOOLS, 370);
            setPosition(LCCItems.sapphire_boots, LCCGroupCategory.TOOLS, 380);

            setPosition(LCCItems.amethyst_sword, LCCGroupCategory.TOOLS, 400);
            setPosition(LCCItems.amethyst_pickaxe, LCCGroupCategory.TOOLS, 410);
            setPosition(LCCItems.amethyst_shovel, LCCGroupCategory.TOOLS, 420);
            setPosition(LCCItems.amethyst_axe, LCCGroupCategory.TOOLS, 430);
            setPosition(LCCItems.amethyst_hoe, LCCGroupCategory.TOOLS, 440);
            setPosition(LCCItems.amethyst_helmet, LCCGroupCategory.TOOLS, 450);
            setPosition(LCCItems.amethyst_chestplate, LCCGroupCategory.TOOLS, 460);
            setPosition(LCCItems.amethyst_leggings, LCCGroupCategory.TOOLS, 470);
            setPosition(LCCItems.amethyst_boots, LCCGroupCategory.TOOLS, 480);

            //Nuclear
            setPosition(LCCBlocks.nuclear_waste, LCCGroupCategory.NUCLEAR, 0);

            //Power
            setPosition(LCCItems.gauntlet, LCCGroupCategory.POWER, 0);
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
        TOOLS,
        NUCLEAR,
        POWER,
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
