package com.joshmanisdabomb.lcc.item.group;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.creative2.Creative2Category;
import com.joshmanisdabomb.lcc.creative2.Creative2Group;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LCCGroup extends Creative2Group {

    public LCCGroup() {
        super(LCC.MODID);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(LCCBlocks.test_block, 1);
    }

    @Override
    public void initSorting() {
        //Test Blocks
        set(LCCBlocks.test_block, Category.TESTING, 0);
        set(LCCBlocks.test_block_2, Category.TESTING, 10);
        set(LCCBlocks.test_block_3, Category.TESTING, 20);
        set(LCCBlocks.test_block_4, Category.TESTING, 30);
        set(LCCBlocks.test_block_5, Category.TESTING, 40);
        set(LCCItems.test_item, Category.TESTING, 50);

        //Gizmos
        set(LCCItems.plastic, Category.GIZMOS, 0);
        set(LCCBlocks.road, Category.GIZMOS, 1000);
        set(LCCBlocks.hydrated_soul_sand, Category.GIZMOS, 2000);
        set(LCCBlocks.bounce_pad, Category.GIZMOS, 2010);

        //Resources
        set(LCCBlocks.ruby_ore, Category.RESOURCES, 0);
        set(LCCItems.ruby, Category.RESOURCES, 10);
        set(LCCBlocks.ruby_storage, Category.RESOURCES, 20);
        set(LCCBlocks.topaz_ore, Category.RESOURCES, 30);
        set(LCCItems.topaz, Category.RESOURCES, 40);
        set(LCCBlocks.topaz_storage, Category.RESOURCES, 50);
        set(LCCBlocks.sapphire_ore, Category.RESOURCES, 60);
        set(LCCItems.sapphire, Category.RESOURCES, 70);
        set(LCCBlocks.sapphire_storage, Category.RESOURCES, 80);
        set(LCCBlocks.amethyst_ore, Category.RESOURCES, 90);
        set(LCCItems.amethyst, Category.RESOURCES, 100);
        set(LCCBlocks.amethyst_storage, Category.RESOURCES, 110);
        set(LCCBlocks.uranium_ore, Category.RESOURCES, 120);
        set(LCCItems.uranium_nugget, Category.RESOURCES, 130);
        set(LCCItems.uranium, Category.RESOURCES, 140);
        set(LCCBlocks.uranium_storage, Category.RESOURCES, 150);
        set(LCCItems.enriched_uranium_nugget, Category.RESOURCES, 160);
        set(LCCItems.enriched_uranium, Category.RESOURCES, 170);
        set(LCCBlocks.enriched_uranium_storage, Category.RESOURCES, 180);
        set(LCCBlocks.neon_ore, Category.RESOURCES, 190);
        set(LCCItems.neon_nugget, Category.RESOURCES, 200);
        set(LCCItems.neon, Category.RESOURCES, 210);
        set(LCCBlocks.neon_storage, Category.RESOURCES, 220);

        //Tools
        set(LCCItems.ruby_sword, Category.TOOLS, 0);
        set(LCCItems.ruby_pickaxe, Category.TOOLS, 10);
        set(LCCItems.ruby_shovel, Category.TOOLS, 20);
        set(LCCItems.ruby_axe, Category.TOOLS, 30);
        set(LCCItems.ruby_hoe, Category.TOOLS, 40);
        set(LCCItems.ruby_helmet, Category.TOOLS, 50);
        set(LCCItems.ruby_chestplate, Category.TOOLS, 60);
        set(LCCItems.ruby_leggings, Category.TOOLS, 70);
        set(LCCItems.ruby_boots, Category.TOOLS, 80);

        set(LCCItems.topaz_sword, Category.TOOLS, 100);
        set(LCCItems.topaz_pickaxe, Category.TOOLS, 110);
        set(LCCItems.topaz_shovel, Category.TOOLS, 120);
        set(LCCItems.topaz_axe, Category.TOOLS, 130);
        set(LCCItems.topaz_hoe, Category.TOOLS, 140);
        set(LCCItems.topaz_helmet, Category.TOOLS, 150);
        set(LCCItems.topaz_chestplate, Category.TOOLS, 160);
        set(LCCItems.topaz_leggings, Category.TOOLS, 170);
        set(LCCItems.topaz_boots, Category.TOOLS, 180);

        set(LCCItems.emerald_sword, Category.TOOLS, 200);
        set(LCCItems.emerald_pickaxe, Category.TOOLS, 210);
        set(LCCItems.emerald_shovel, Category.TOOLS, 220);
        set(LCCItems.emerald_axe, Category.TOOLS, 230);
        set(LCCItems.emerald_hoe, Category.TOOLS, 240);
        set(LCCItems.emerald_helmet, Category.TOOLS, 250);
        set(LCCItems.emerald_chestplate, Category.TOOLS, 260);
        set(LCCItems.emerald_leggings, Category.TOOLS, 270);
        set(LCCItems.emerald_boots, Category.TOOLS, 280);

        set(LCCItems.sapphire_sword, Category.TOOLS, 300);
        set(LCCItems.sapphire_pickaxe, Category.TOOLS, 310);
        set(LCCItems.sapphire_shovel, Category.TOOLS, 320);
        set(LCCItems.sapphire_axe, Category.TOOLS, 330);
        set(LCCItems.sapphire_hoe, Category.TOOLS, 340);
        set(LCCItems.sapphire_helmet, Category.TOOLS, 350);
        set(LCCItems.sapphire_chestplate, Category.TOOLS, 360);
        set(LCCItems.sapphire_leggings, Category.TOOLS, 370);
        set(LCCItems.sapphire_boots, Category.TOOLS, 380);

        set(LCCItems.amethyst_sword, Category.TOOLS, 400);
        set(LCCItems.amethyst_pickaxe, Category.TOOLS, 410);
        set(LCCItems.amethyst_shovel, Category.TOOLS, 420);
        set(LCCItems.amethyst_axe, Category.TOOLS, 430);
        set(LCCItems.amethyst_hoe, Category.TOOLS, 440);
        set(LCCItems.amethyst_helmet, Category.TOOLS, 450);
        set(LCCItems.amethyst_chestplate, Category.TOOLS, 460);
        set(LCCItems.amethyst_leggings, Category.TOOLS, 470);
        set(LCCItems.amethyst_boots, Category.TOOLS, 480);

        set(LCCItems.vivid_sword, Category.TOOLS, 500);
        set(LCCItems.vivid_pickaxe, Category.TOOLS, 510);
        set(LCCItems.vivid_shovel, Category.TOOLS, 520);
        set(LCCItems.vivid_axe, Category.TOOLS, 530);
        set(LCCItems.vivid_hoe, Category.TOOLS, 540);

        set(LCCItems.red_candy_cane_sword, Category.TOOLS, 600);
        set(LCCItems.red_candy_cane_pickaxe, Category.TOOLS, 610);
        set(LCCItems.red_candy_cane_shovel, Category.TOOLS, 620);
        set(LCCItems.red_candy_cane_axe, Category.TOOLS, 630);
        set(LCCItems.red_candy_cane_hoe, Category.TOOLS, 640);

        set(LCCItems.green_candy_cane_sword, Category.TOOLS, 700);
        set(LCCItems.green_candy_cane_pickaxe, Category.TOOLS, 710);
        set(LCCItems.green_candy_cane_shovel, Category.TOOLS, 720);
        set(LCCItems.green_candy_cane_axe, Category.TOOLS, 730);
        set(LCCItems.green_candy_cane_hoe, Category.TOOLS, 740);

        set(LCCItems.blue_candy_cane_sword, Category.TOOLS, 800);
        set(LCCItems.blue_candy_cane_pickaxe, Category.TOOLS, 810);
        set(LCCItems.blue_candy_cane_shovel, Category.TOOLS, 820);
        set(LCCItems.blue_candy_cane_axe, Category.TOOLS, 830);
        set(LCCItems.blue_candy_cane_hoe, Category.TOOLS, 840);

        set(LCCItems.neon_sword, Category.TOOLS, 900);
        set(LCCItems.neon_pickaxe, Category.TOOLS, 910);
        set(LCCItems.neon_shovel, Category.TOOLS, 920);
        set(LCCItems.neon_axe, Category.TOOLS, 930);
        set(LCCItems.neon_hoe, Category.TOOLS, 940);
        set(LCCItems.neon_helmet, Category.TOOLS, 950);
        set(LCCItems.neon_chestplate, Category.TOOLS, 960);
        set(LCCItems.neon_leggings, Category.TOOLS, 970);
        set(LCCItems.neon_boots, Category.TOOLS, 980);

        //Rainbow
        set(LCCItems.chromatic_core, Category.RAINBOW, 0);
        set(LCCBlocks.rainbow_gate, Category.RAINBOW, 10);

        set(LCCBlocks.rainbow_grass_block, Category.RAINBOW, 1000);
        set(LCCBlocks.sugar_grass_block, Category.RAINBOW, 1001);
        set(LCCBlocks.star_plating, Category.RAINBOW, 1002);
        group(LCCBlocks.sparkling_grass_block, Category.RAINBOW, k -> k.getId()+1003);
        set(LCCBlocks.sparkling_dirt, Category.RAINBOW, 1020);

        set(LCCBlocks.twilight_stone, Category.RAINBOW, 2000);
        set(LCCBlocks.twilight_cobblestone, Category.RAINBOW, 2001);

        set(LCCBlocks.vivid_log, Category.RAINBOW, 3000);
        set(LCCBlocks.vivid_wood, Category.RAINBOW, 3001);
        set(LCCBlocks.stripped_vivid_log, Category.RAINBOW, 3002);
        set(LCCBlocks.stripped_vivid_wood, Category.RAINBOW, 3003);
        set(LCCBlocks.vivid_sapling, Category.RAINBOW, 3004);
        set(LCCBlocks.vivid_leaves, Category.RAINBOW, 3010);
        set(LCCBlocks.vivid_planks, Category.RAINBOW, 3100);
        set(LCCBlocks.vivid_stairs, Category.RAINBOW, 3101);
        set(LCCBlocks.vivid_slab, Category.RAINBOW, 3102);
        set(LCCBlocks.vivid_fence, Category.RAINBOW, 3103);
        set(LCCBlocks.vivid_fence_gate, Category.RAINBOW, 3104);
        set(LCCBlocks.vivid_door, Category.RAINBOW, 3200);
        set(LCCBlocks.vivid_trapdoor, Category.RAINBOW, 3201);
        set(LCCBlocks.vivid_pressure_plate, Category.RAINBOW, 3202);
        set(LCCBlocks.vivid_button, Category.RAINBOW, 3203);

        set(LCCBlocks.candy_cane_red, Category.RAINBOW, 4000);
        set(LCCBlocks.candy_cane_green, Category.RAINBOW, 4001);
        set(LCCBlocks.candy_cane_blue, Category.RAINBOW, 4002);
        set(LCCBlocks.stripped_candy_cane, Category.RAINBOW, 4003);
        set(LCCBlocks.candy_cane_coating_red, Category.RAINBOW, 4004);
        set(LCCBlocks.candy_cane_coating_green, Category.RAINBOW, 4005);
        set(LCCBlocks.candy_cane_coating_blue, Category.RAINBOW, 4006);
        set(LCCBlocks.stripped_candy_cane_coating, Category.RAINBOW, 4007);
        set(LCCBlocks.refined_candy_cane_red, Category.RAINBOW, 4008);
        set(LCCBlocks.refined_candy_cane_green, Category.RAINBOW, 4009);
        set(LCCBlocks.refined_candy_cane_blue, Category.RAINBOW, 4010);
        set(LCCBlocks.refined_stripped_candy_cane, Category.RAINBOW, 4011);
        set(LCCBlocks.refined_candy_cane_coating_red, Category.RAINBOW, 4012);
        set(LCCBlocks.refined_candy_cane_coating_green, Category.RAINBOW, 4013);
        set(LCCBlocks.refined_candy_cane_coating_blue, Category.RAINBOW, 4014);
        set(LCCBlocks.refined_stripped_candy_cane_coating, Category.RAINBOW, 4015);
        set(LCCBlocks.candy_cane_block, Category.RAINBOW, 4016);

        set(LCCBlocks.channelite.get(null), Category.RAINBOW, 5000);
        group(LCCBlocks.sparkling_channelite_source, Category.RAINBOW, k -> k.getId()+5100);
        group(LCCBlocks.twilight_channelite_source, Category.RAINBOW, k -> k.getId()+5200);

        //Spreaders
        set(LCCItems.spreader_essence, Category.SPREADERS, 0);
        set(LCCBlocks.spreader_interface, Category.SPREADERS, 1);
        group(LCCBlocks.spreaders, Category.SPREADERS, k -> k.getId()+2);

        //Wasteland
        set(LCCBlocks.cracked_mud, Category.WASTELAND, 0);
        set(LCCItems.oil_bucket, Category.WASTELAND, 10);

        //Nuclear TODO: lump in with explosives
        set(LCCBlocks.atomic_bomb, Category.NUCLEAR, 0);
        set(LCCBlocks.nuclear_waste, Category.NUCLEAR, 100);

        //Computing
        set(LCCItems.microchip, Category.COMPUTING, -1000);
        set(LCCItems.chipset, Category.COMPUTING, -990);
        set(LCCBlocks.networking_cable, Category.COMPUTING, -100);
        set(LCCBlocks.terminal_cable, Category.COMPUTING, -90);
        group(LCCItems.computer_casings, Category.COMPUTING, DyeColor::getId);
        group(LCCItems.computers, Category.COMPUTING, k -> k.getId()+100);
        group(LCCBlocks.terminals, Category.COMPUTING, k -> k.getId()+200);
        group(LCCItems.floppy_drives, Category.COMPUTING, k -> k.getId()+300);
        group(LCCItems.cd_drives, Category.COMPUTING, k -> k.getId()+400);
        group(LCCItems.card_readers, Category.COMPUTING, k -> k.getId()+500);
        group(LCCItems.stick_readers, Category.COMPUTING, k -> k.getId()+600);
        group(LCCItems.drive_bays, Category.COMPUTING, k -> k.getId()+700);
        set(LCCItems.cpu, Category.COMPUTING, 5000);
        set(LCCItems.ram, Category.COMPUTING, 5100);
        set(LCCItems.gpu, Category.COMPUTING, 5200);
        set(LCCItems.floppy_disk, Category.COMPUTING, 10000);
        set(LCCItems.compact_disc, Category.COMPUTING, 10100);
        set(LCCItems.memory_card, Category.COMPUTING, 10200);
        set(LCCItems.memory_stick, Category.COMPUTING, 10300);
        set(LCCItems.hard_disk_drive, Category.COMPUTING, 10400);
        set(LCCItems.solid_state_drive, Category.COMPUTING, 10500);
        set(LCCItems.m2, Category.COMPUTING, 10600);

        //Nostalgia
        set(LCCBlocks.time_rift, Category.NOSTALGIA, 0);
        set(LCCBlocks.classic_grass_block, Category.NOSTALGIA, 1000);
        set(LCCBlocks.classic_cobblestone, Category.NOSTALGIA, 1010);
        set(LCCBlocks.classic_planks, Category.NOSTALGIA, 1020);
        set(LCCBlocks.classic_leaves, Category.NOSTALGIA, 1030);
        set(LCCBlocks.classic_sapling, Category.NOSTALGIA, 1040);
        set(LCCBlocks.classic_gravel, Category.NOSTALGIA, 1050);
        set(LCCBlocks.classic_sponge, Category.NOSTALGIA, 1060);
        set(LCCBlocks.classic_glass, Category.NOSTALGIA, 1070);
        group(LCCBlocks.classic_cloth, Category.NOSTALGIA, k -> k.ordinal()+1080);
        set(LCCBlocks.classic_rose, Category.NOSTALGIA, 1100);
        set(LCCBlocks.classic_cyan_flower, Category.NOSTALGIA, 1110);
        set(LCCBlocks.classic_iron_block, Category.NOSTALGIA, 1120);
        set(LCCBlocks.classic_smooth_iron_block, Category.NOSTALGIA, 1125);
        set(LCCBlocks.classic_gold_block, Category.NOSTALGIA, 1130);
        set(LCCBlocks.classic_smooth_gold_block, Category.NOSTALGIA, 1135);
        set(LCCBlocks.classic_diamond_block, Category.NOSTALGIA, 1140);
        set(LCCBlocks.classic_smooth_diamond_block, Category.NOSTALGIA, 1145);
        set(LCCBlocks.classic_bricks, Category.NOSTALGIA, 1150);
        set(LCCBlocks.classic_tnt, Category.NOSTALGIA, 1160);
        set(LCCBlocks.classic_mossy_cobblestone, Category.NOSTALGIA, 1170);
        set(LCCBlocks.classic_chest, Category.NOSTALGIA, 1180);
        set(LCCBlocks.nether_reactor, Category.NOSTALGIA, 2000);
        set(LCCBlocks.glowing_obsidian, Category.NOSTALGIA, 2010);
        set(LCCBlocks.crying_obsidian, Category.NOSTALGIA, 2020);
        set(LCCBlocks.cog, Category.NOSTALGIA, 2030);

        //Power TODO: rename
        set(LCCItems.gauntlet, Category.POWER, 0);

        //Health
        set(LCCItems.red_heart_half, Category.HEALTH, 0);
        set(LCCItems.red_heart, Category.HEALTH, 10);
        set(LCCItems.red_heart_container, Category.HEALTH, 20);
        set(LCCItems.iron_heart_half, Category.HEALTH, 30);
        set(LCCItems.iron_heart, Category.HEALTH, 40);
        set(LCCItems.iron_heart_container, Category.HEALTH, 50);
        set(LCCItems.crystal_heart_half, Category.HEALTH, 60);
        set(LCCItems.crystal_heart, Category.HEALTH, 70);
        set(LCCItems.crystal_heart_container, Category.HEALTH, 80);
        set(LCCItems.temporary_heart_half, Category.HEALTH, 90);
        set(LCCItems.temporary_heart, Category.HEALTH, 100);
    }

    public enum Category implements Creative2Category {

        RESOURCES(0xFFFF00FF),
        TOOLS(0xFFFF00FF),
        GIZMOS(0xFFFF00FF),
        RAINBOW(0xFFAD72AD),
        SPREADERS(0xFF5D473E),
        WASTELAND(0xFFFF00FF),
        NUCLEAR(0xFFFF00FF),
        COMPUTING(0xFF194a19),
        NOSTALGIA(0xFF69854E),
        POWER(0xFFFF00FF),
        HEALTH(0xFFFF00FF),
        TESTING(0xFFFF00FF);

        public final int groupColor;

        Category(int groupColor) {
            this.groupColor = groupColor;
        }

        @Override
        public int getGroupColor() {
            return groupColor;
        }

        @Override
        public int getSortValue() {
            return this.ordinal();
        }

    }

}
