package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class LCCGroup extends ItemGroup {

    private static final HashMap<Predicate<ItemStack>, LCCGroupCategory> CATEGORY_MAP = new HashMap<>();
    private static final HashMap<Predicate<ItemStack>, Integer> VALUE_MAP = new HashMap<>();

    private static final Comparator<ItemStack> SORTER = (i1, i2) -> {
        if (i1.isEmpty() || i2.isEmpty()) return 0;

        LCCGroupCategory ic1 = null, ic2 = null;
        for (Map.Entry<Predicate<ItemStack>, LCCGroupCategory> e : CATEGORY_MAP.entrySet()) {
            if (ic1 != null && ic2 != null) break;
            if (ic1 == null && e.getKey().test(i1)) ic1 = e.getValue();
            if (ic2 == null && e.getKey().test(i2)) ic2 = e.getValue();
        }
        if (ic1.ordinal() != ic2.ordinal()) return ic1.ordinal() - ic2.ordinal();

        Integer iv1 = null, iv2 = null;
        for (Map.Entry<Predicate<ItemStack>, Integer> e : VALUE_MAP.entrySet()) {
            if (iv1 != null && iv2 != null) break;
            if (iv1 == null && e.getKey().test(i1)) iv1 = e.getValue();
            if (iv2 == null && e.getKey().test(i2)) iv2 = e.getValue();
        }
        return iv1 - iv2;
    };

    public LCCGroup() {
        super(LCC.MODID);
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
        items.sort(SORTER);
        LCCGroup.addSpacing(items);
    }

    public static void initSorting() {
        //Test Blocks
        set(LCCBlocks.test_block, LCCGroupCategory.TESTING, 0);
        set(LCCBlocks.test_block_2, LCCGroupCategory.TESTING, 10);
        set(LCCBlocks.test_block_3, LCCGroupCategory.TESTING, 20);
        set(LCCBlocks.test_block_4, LCCGroupCategory.TESTING, 30);
        set(LCCBlocks.test_block_5, LCCGroupCategory.TESTING, 40);
        set(LCCItems.test_item, LCCGroupCategory.TESTING, 50);

        //Gizmos
        set(LCCBlocks.road, LCCGroupCategory.GIZMOS, 0);
        set(LCCBlocks.hydrated_soul_sand, LCCGroupCategory.GIZMOS, 100);
        set(LCCBlocks.bounce_pad, LCCGroupCategory.GIZMOS, 110);

        //Resources
        set(LCCBlocks.ruby_ore, LCCGroupCategory.RESOURCES, 0);
        set(LCCItems.ruby, LCCGroupCategory.RESOURCES, 10);
        set(LCCBlocks.ruby_storage, LCCGroupCategory.RESOURCES, 20);
        set(LCCBlocks.topaz_ore, LCCGroupCategory.RESOURCES, 30);
        set(LCCItems.topaz, LCCGroupCategory.RESOURCES, 40);
        set(LCCBlocks.topaz_storage, LCCGroupCategory.RESOURCES, 50);
        set(LCCBlocks.sapphire_ore, LCCGroupCategory.RESOURCES, 60);
        set(LCCItems.sapphire, LCCGroupCategory.RESOURCES, 70);
        set(LCCBlocks.sapphire_storage, LCCGroupCategory.RESOURCES, 80);
        set(LCCBlocks.amethyst_ore, LCCGroupCategory.RESOURCES, 90);
        set(LCCItems.amethyst, LCCGroupCategory.RESOURCES, 100);
        set(LCCBlocks.amethyst_storage, LCCGroupCategory.RESOURCES, 110);
        set(LCCBlocks.uranium_ore, LCCGroupCategory.RESOURCES, 120);
        set(LCCItems.uranium_nugget, LCCGroupCategory.RESOURCES, 130);
        set(LCCItems.uranium, LCCGroupCategory.RESOURCES, 140);
        set(LCCBlocks.uranium_storage, LCCGroupCategory.RESOURCES, 150);
        set(LCCItems.enriched_uranium_nugget, LCCGroupCategory.RESOURCES, 160);
        set(LCCItems.enriched_uranium, LCCGroupCategory.RESOURCES, 170);
        set(LCCBlocks.enriched_uranium_storage, LCCGroupCategory.RESOURCES, 180);

        //Tools
        set(LCCItems.ruby_sword, LCCGroupCategory.TOOLS, 0);
        set(LCCItems.ruby_pickaxe, LCCGroupCategory.TOOLS, 10);
        set(LCCItems.ruby_shovel, LCCGroupCategory.TOOLS, 20);
        set(LCCItems.ruby_axe, LCCGroupCategory.TOOLS, 30);
        set(LCCItems.ruby_hoe, LCCGroupCategory.TOOLS, 40);
        set(LCCItems.ruby_helmet, LCCGroupCategory.TOOLS, 50);
        set(LCCItems.ruby_chestplate, LCCGroupCategory.TOOLS, 60);
        set(LCCItems.ruby_leggings, LCCGroupCategory.TOOLS, 70);
        set(LCCItems.ruby_boots, LCCGroupCategory.TOOLS, 80);

        set(LCCItems.topaz_sword, LCCGroupCategory.TOOLS, 100);
        set(LCCItems.topaz_pickaxe, LCCGroupCategory.TOOLS, 110);
        set(LCCItems.topaz_shovel, LCCGroupCategory.TOOLS, 120);
        set(LCCItems.topaz_axe, LCCGroupCategory.TOOLS, 130);
        set(LCCItems.topaz_hoe, LCCGroupCategory.TOOLS, 140);
        set(LCCItems.topaz_helmet, LCCGroupCategory.TOOLS, 150);
        set(LCCItems.topaz_chestplate, LCCGroupCategory.TOOLS, 160);
        set(LCCItems.topaz_leggings, LCCGroupCategory.TOOLS, 170);
        set(LCCItems.topaz_boots, LCCGroupCategory.TOOLS, 180);

        set(LCCItems.emerald_sword, LCCGroupCategory.TOOLS, 200);
        set(LCCItems.emerald_pickaxe, LCCGroupCategory.TOOLS, 210);
        set(LCCItems.emerald_shovel, LCCGroupCategory.TOOLS, 220);
        set(LCCItems.emerald_axe, LCCGroupCategory.TOOLS, 230);
        set(LCCItems.emerald_hoe, LCCGroupCategory.TOOLS, 240);
        set(LCCItems.emerald_helmet, LCCGroupCategory.TOOLS, 250);
        set(LCCItems.emerald_chestplate, LCCGroupCategory.TOOLS, 260);
        set(LCCItems.emerald_leggings, LCCGroupCategory.TOOLS, 270);
        set(LCCItems.emerald_boots, LCCGroupCategory.TOOLS, 280);

        set(LCCItems.sapphire_sword, LCCGroupCategory.TOOLS, 300);
        set(LCCItems.sapphire_pickaxe, LCCGroupCategory.TOOLS, 310);
        set(LCCItems.sapphire_shovel, LCCGroupCategory.TOOLS, 320);
        set(LCCItems.sapphire_axe, LCCGroupCategory.TOOLS, 330);
        set(LCCItems.sapphire_hoe, LCCGroupCategory.TOOLS, 340);
        set(LCCItems.sapphire_helmet, LCCGroupCategory.TOOLS, 350);
        set(LCCItems.sapphire_chestplate, LCCGroupCategory.TOOLS, 360);
        set(LCCItems.sapphire_leggings, LCCGroupCategory.TOOLS, 370);
        set(LCCItems.sapphire_boots, LCCGroupCategory.TOOLS, 380);

        set(LCCItems.amethyst_sword, LCCGroupCategory.TOOLS, 400);
        set(LCCItems.amethyst_pickaxe, LCCGroupCategory.TOOLS, 410);
        set(LCCItems.amethyst_shovel, LCCGroupCategory.TOOLS, 420);
        set(LCCItems.amethyst_axe, LCCGroupCategory.TOOLS, 430);
        set(LCCItems.amethyst_hoe, LCCGroupCategory.TOOLS, 440);
        set(LCCItems.amethyst_helmet, LCCGroupCategory.TOOLS, 450);
        set(LCCItems.amethyst_chestplate, LCCGroupCategory.TOOLS, 460);
        set(LCCItems.amethyst_leggings, LCCGroupCategory.TOOLS, 470);
        set(LCCItems.amethyst_boots, LCCGroupCategory.TOOLS, 480);

        //Rainbow
        set(LCCItems.chromatic_core, LCCGroupCategory.RAINBOW, 0);

        //Spreaders
        set(LCCItems.spreader_essence, LCCGroupCategory.SPREADERS, 0);
        set(LCCBlocks.spreader_interface, LCCGroupCategory.SPREADERS, 1);
        for (DyeColor color : DyeColor.values()) {
            set(LCCBlocks.spreaders.get(color), LCCGroupCategory.SPREADERS, color.getId()+2);
        }

        //Nuclear TODO: lump in with explosives
        set(LCCBlocks.nuclear_waste, LCCGroupCategory.NUCLEAR, 0);

        //Power TODO: rename
        set(LCCItems.gauntlet, LCCGroupCategory.POWER, 0);

        //Health
        set(LCCItems.red_heart_half, LCCGroupCategory.HEALTH, 0);
        set(LCCItems.red_heart, LCCGroupCategory.HEALTH, 10);
        set(LCCItems.red_heart_container, LCCGroupCategory.HEALTH, 20);
        set(LCCItems.iron_heart_half, LCCGroupCategory.HEALTH, 30);
        set(LCCItems.iron_heart, LCCGroupCategory.HEALTH, 40);
        set(LCCItems.iron_heart_container, LCCGroupCategory.HEALTH, 50);
        set(LCCItems.crystal_heart_half, LCCGroupCategory.HEALTH, 60);
        set(LCCItems.crystal_heart, LCCGroupCategory.HEALTH, 70);
        set(LCCItems.crystal_heart_container, LCCGroupCategory.HEALTH, 80);
        set(LCCItems.temporary_heart_half, LCCGroupCategory.HEALTH, 90);
        set(LCCItems.temporary_heart, LCCGroupCategory.HEALTH, 100);
    }

    private static void set(IItemProvider item, LCCGroupCategory category, int sortValue) {
        set(i -> i.getItem().asItem() == item.asItem(), category, sortValue);
    }

    private static void set(Predicate<ItemStack> predicate, LCCGroupCategory category, int sortValue) {
        CATEGORY_MAP.put(predicate, category);
        VALUE_MAP.put(predicate, sortValue);
    }

    private static void addSpacing(NonNullList<ItemStack> items) {
        NonNullList<ItemStack> items2 = NonNullList.create();
        int categorySize = 0;
        for (int i = 0; i < items.size(); i++) {
            ItemStack s = items.get(i);
            categorySize++;
            items2.add(s);

            if (i < items.size() - 1) {
                ItemStack s2 = items.get(i + 1);

                LCCGroupCategory ic1 = null, ic2 = null;
                for (Map.Entry<Predicate<ItemStack>, LCCGroupCategory> e : CATEGORY_MAP.entrySet()) {
                    if (ic1 != null && ic2 != null) break;
                    if (ic1 == null && e.getKey().test(s)) ic1 = e.getValue();
                    if (ic2 == null && e.getKey().test(s2)) ic2 = e.getValue();
                }
                if (ic1.ordinal() != ic2.ordinal()) {
                    int emptySpaces = 9 - (((categorySize - 1) % 9) + 1);
                    for (int j = 0; j < emptySpaces; j++) {
                        items2.add(ItemStack.EMPTY);
                    }
                    categorySize = 0;
                }
            }
        }
        items.clear();
        items.addAll(items2);
    }

    public enum LCCGroupCategory {

        RESOURCES,
        TOOLS,
        GIZMOS,
        RAINBOW,
        SPREADERS,
        NUCLEAR,
        POWER,
        HEALTH,
        TESTING;

    }

}
