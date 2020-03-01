package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.misc.Colors.ClassicDyeColor;
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
        set(LCCItems.plastic, LCCGroupCategory.GIZMOS, 0);
        set(LCCBlocks.road, LCCGroupCategory.GIZMOS, 1000);
        set(LCCBlocks.hydrated_soul_sand, LCCGroupCategory.GIZMOS, 2000);
        set(LCCBlocks.bounce_pad, LCCGroupCategory.GIZMOS, 2010);

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
        set(LCCBlocks.rainbow_gate, LCCGroupCategory.RAINBOW, 10);
        set(LCCBlocks.rainbow_grass_block, LCCGroupCategory.RAINBOW, 1000);
        set(LCCBlocks.sugar_grass_block, LCCGroupCategory.RAINBOW, 1001);
        set(LCCBlocks.star_grass_block, LCCGroupCategory.RAINBOW, 1002);
        for (DyeColor color : DyeColor.values()) {
            set(LCCBlocks.sparkling_grass_block.get(color), LCCGroupCategory.RAINBOW, color.getId()+1003);
        }
        set(LCCBlocks.sparkling_dirt, LCCGroupCategory.RAINBOW, 1020);
        set(LCCBlocks.twilight_stone, LCCGroupCategory.RAINBOW, 1021);
        set(LCCBlocks.twilight_cobblestone, LCCGroupCategory.RAINBOW, 1022);
        set(LCCBlocks.candy_cane_red, LCCGroupCategory.RAINBOW, 1100);
        set(LCCBlocks.candy_cane_green, LCCGroupCategory.RAINBOW, 1101);
        set(LCCBlocks.candy_cane_blue, LCCGroupCategory.RAINBOW, 1102);
        set(LCCBlocks.stripped_candy_cane, LCCGroupCategory.RAINBOW, 1103);
        set(LCCBlocks.candy_cane_coating_red, LCCGroupCategory.RAINBOW, 1104);
        set(LCCBlocks.candy_cane_coating_green, LCCGroupCategory.RAINBOW, 1105);
        set(LCCBlocks.candy_cane_coating_blue, LCCGroupCategory.RAINBOW, 1106);
        set(LCCBlocks.stripped_candy_cane_coating, LCCGroupCategory.RAINBOW, 1107);
        set(LCCBlocks.refined_candy_cane_red, LCCGroupCategory.RAINBOW, 1108);
        set(LCCBlocks.refined_candy_cane_green, LCCGroupCategory.RAINBOW, 1109);
        set(LCCBlocks.refined_candy_cane_blue, LCCGroupCategory.RAINBOW, 1110);
        set(LCCBlocks.refined_stripped_candy_cane, LCCGroupCategory.RAINBOW, 1111);
        set(LCCBlocks.refined_candy_cane_coating_red, LCCGroupCategory.RAINBOW, 1112);
        set(LCCBlocks.refined_candy_cane_coating_green, LCCGroupCategory.RAINBOW, 1113);
        set(LCCBlocks.refined_candy_cane_coating_blue, LCCGroupCategory.RAINBOW, 1114);
        set(LCCBlocks.refined_stripped_candy_cane_coating, LCCGroupCategory.RAINBOW, 1115);
        set(LCCBlocks.candy_cane_block, LCCGroupCategory.RAINBOW, 1116);
        set(LCCBlocks.channelite.get(null), LCCGroupCategory.RAINBOW, 1120);
        for (DyeColor color : DyeColor.values()) {
            set(LCCBlocks.sparkling_channelite_source.get(color), LCCGroupCategory.RAINBOW, color.getId()+1121);
            set(LCCBlocks.twilight_channelite_source.get(color), LCCGroupCategory.RAINBOW, color.getId()+1141);
        }

        //Spreaders
        set(LCCItems.spreader_essence, LCCGroupCategory.SPREADERS, 0);
        set(LCCBlocks.spreader_interface, LCCGroupCategory.SPREADERS, 1);
        for (DyeColor color : DyeColor.values()) {
            set(LCCBlocks.spreaders.get(color), LCCGroupCategory.SPREADERS, color.getId()+2);
        }

        //Wasteland
        set(LCCBlocks.cracked_mud, LCCGroupCategory.WASTELAND, 0);
        set(LCCItems.oil_bucket, LCCGroupCategory.WASTELAND, 10);

        //Nuclear TODO: lump in with explosives
        set(LCCBlocks.atomic_bomb, LCCGroupCategory.NUCLEAR, 0);
        set(LCCBlocks.nuclear_waste, LCCGroupCategory.NUCLEAR, 100);

        //Computing
        set(LCCItems.microchip, LCCGroupCategory.COMPUTING, -1000);
        set(LCCItems.chipset, LCCGroupCategory.COMPUTING, -990);
        set(LCCBlocks.networking_cable, LCCGroupCategory.COMPUTING, -100);
        set(LCCBlocks.terminal_cable, LCCGroupCategory.COMPUTING, -90);
        for (DyeColor color : DyeColor.values()) {
            set(LCCItems.computer_casings.get(color), LCCGroupCategory.COMPUTING, color.getId());
            set(LCCItems.computers.get(color), LCCGroupCategory.COMPUTING, color.getId()+100);
            set(LCCBlocks.terminals.get(color), LCCGroupCategory.COMPUTING, color.getId()+200);
            set(LCCItems.floppy_drives.get(color), LCCGroupCategory.COMPUTING, color.getId()+300);
            set(LCCItems.cd_drives.get(color), LCCGroupCategory.COMPUTING, color.getId()+400);
            set(LCCItems.card_readers.get(color), LCCGroupCategory.COMPUTING, color.getId()+500);
            set(LCCItems.stick_readers.get(color), LCCGroupCategory.COMPUTING, color.getId()+600);
            set(LCCItems.drive_bays.get(color), LCCGroupCategory.COMPUTING, color.getId()+700);
        }
        set(LCCItems.cpu, LCCGroupCategory.COMPUTING, 5000);
        set(LCCItems.ram, LCCGroupCategory.COMPUTING, 5100);
        set(LCCItems.gpu, LCCGroupCategory.COMPUTING, 5200);
        set(LCCItems.floppy_disk, LCCGroupCategory.COMPUTING, 10000);
        set(LCCItems.compact_disc, LCCGroupCategory.COMPUTING, 10100);
        set(LCCItems.memory_card, LCCGroupCategory.COMPUTING, 10200);
        set(LCCItems.memory_stick, LCCGroupCategory.COMPUTING, 10300);
        set(LCCItems.hard_disk_drive, LCCGroupCategory.COMPUTING, 10400);
        set(LCCItems.solid_state_drive, LCCGroupCategory.COMPUTING, 10500);
        set(LCCItems.m2, LCCGroupCategory.COMPUTING, 10600);

        //Nostalgia
        set(LCCBlocks.time_rift, LCCGroupCategory.NOSTALGIA, 0);
        set(LCCBlocks.classic_grass_block, LCCGroupCategory.NOSTALGIA, 1000);
        set(LCCBlocks.classic_cobblestone, LCCGroupCategory.NOSTALGIA, 1010);
        set(LCCBlocks.classic_planks, LCCGroupCategory.NOSTALGIA, 1020);
        set(LCCBlocks.classic_leaves, LCCGroupCategory.NOSTALGIA, 1030);
        set(LCCBlocks.classic_sapling, LCCGroupCategory.NOSTALGIA, 1040);
        set(LCCBlocks.classic_gravel, LCCGroupCategory.NOSTALGIA, 1050);
        set(LCCBlocks.classic_sponge, LCCGroupCategory.NOSTALGIA, 1060);
        set(LCCBlocks.classic_glass, LCCGroupCategory.NOSTALGIA, 1070);
        for (ClassicDyeColor color : ClassicDyeColor.values()) {
            set(LCCBlocks.classic_cloth.get(color), LCCGroupCategory.NOSTALGIA, 1080+color.ordinal());
        }
        set(LCCBlocks.classic_rose, LCCGroupCategory.NOSTALGIA, 1100);
        set(LCCBlocks.classic_cyan_flower, LCCGroupCategory.NOSTALGIA, 1110);
        set(LCCBlocks.classic_iron_block, LCCGroupCategory.NOSTALGIA, 1120);
        set(LCCBlocks.classic_smooth_iron_block, LCCGroupCategory.NOSTALGIA, 1125);
        set(LCCBlocks.classic_gold_block, LCCGroupCategory.NOSTALGIA, 1130);
        set(LCCBlocks.classic_smooth_gold_block, LCCGroupCategory.NOSTALGIA, 1135);
        set(LCCBlocks.classic_diamond_block, LCCGroupCategory.NOSTALGIA, 1140);
        set(LCCBlocks.classic_smooth_diamond_block, LCCGroupCategory.NOSTALGIA, 1145);
        set(LCCBlocks.classic_bricks, LCCGroupCategory.NOSTALGIA, 1150);
        set(LCCBlocks.classic_tnt, LCCGroupCategory.NOSTALGIA, 1160);
        set(LCCBlocks.classic_mossy_cobblestone, LCCGroupCategory.NOSTALGIA, 1170);
        set(LCCBlocks.classic_chest, LCCGroupCategory.NOSTALGIA, 1180);
        set(LCCBlocks.nether_reactor, LCCGroupCategory.NOSTALGIA, 2000);
        set(LCCBlocks.glowing_obsidian, LCCGroupCategory.NOSTALGIA, 2010);
        set(LCCBlocks.crying_obsidian, LCCGroupCategory.NOSTALGIA, 2020);
        set(LCCBlocks.cog, LCCGroupCategory.NOSTALGIA, 2030);

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
        WASTELAND,
        NUCLEAR,
        COMPUTING,
        NOSTALGIA,
        POWER,
        HEALTH,
        TESTING

    }

}
