package com.joshmanisdabomb.lcc.item.group;

import com.joshmanisdabomb.lcc.LCC;
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
        set(LCCBlocks.neon_ore, LCCGroupCategory.RESOURCES, 190);
        set(LCCItems.neon_nugget, LCCGroupCategory.RESOURCES, 200);
        set(LCCItems.neon, LCCGroupCategory.RESOURCES, 210);
        set(LCCBlocks.neon_storage, LCCGroupCategory.RESOURCES, 220);

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

        set(LCCItems.vivid_sword, LCCGroupCategory.TOOLS, 500);
        set(LCCItems.vivid_pickaxe, LCCGroupCategory.TOOLS, 510);
        set(LCCItems.vivid_shovel, LCCGroupCategory.TOOLS, 520);
        set(LCCItems.vivid_axe, LCCGroupCategory.TOOLS, 530);
        set(LCCItems.vivid_hoe, LCCGroupCategory.TOOLS, 540);

        set(LCCItems.red_candy_cane_sword, LCCGroupCategory.TOOLS, 600);
        set(LCCItems.red_candy_cane_pickaxe, LCCGroupCategory.TOOLS, 610);
        set(LCCItems.red_candy_cane_shovel, LCCGroupCategory.TOOLS, 620);
        set(LCCItems.red_candy_cane_axe, LCCGroupCategory.TOOLS, 630);
        set(LCCItems.red_candy_cane_hoe, LCCGroupCategory.TOOLS, 640);

        set(LCCItems.green_candy_cane_sword, LCCGroupCategory.TOOLS, 700);
        set(LCCItems.green_candy_cane_pickaxe, LCCGroupCategory.TOOLS, 710);
        set(LCCItems.green_candy_cane_shovel, LCCGroupCategory.TOOLS, 720);
        set(LCCItems.green_candy_cane_axe, LCCGroupCategory.TOOLS, 730);
        set(LCCItems.green_candy_cane_hoe, LCCGroupCategory.TOOLS, 740);

        set(LCCItems.blue_candy_cane_sword, LCCGroupCategory.TOOLS, 800);
        set(LCCItems.blue_candy_cane_pickaxe, LCCGroupCategory.TOOLS, 810);
        set(LCCItems.blue_candy_cane_shovel, LCCGroupCategory.TOOLS, 820);
        set(LCCItems.blue_candy_cane_axe, LCCGroupCategory.TOOLS, 830);
        set(LCCItems.blue_candy_cane_hoe, LCCGroupCategory.TOOLS, 840);

        set(LCCItems.neon_sword, LCCGroupCategory.TOOLS, 900);
        set(LCCItems.neon_pickaxe, LCCGroupCategory.TOOLS, 910);
        set(LCCItems.neon_shovel, LCCGroupCategory.TOOLS, 920);
        set(LCCItems.neon_axe, LCCGroupCategory.TOOLS, 930);
        set(LCCItems.neon_hoe, LCCGroupCategory.TOOLS, 940);
        set(LCCItems.neon_helmet, LCCGroupCategory.TOOLS, 950);
        set(LCCItems.neon_chestplate, LCCGroupCategory.TOOLS, 960);
        set(LCCItems.neon_leggings, LCCGroupCategory.TOOLS, 970);
        set(LCCItems.neon_boots, LCCGroupCategory.TOOLS, 980);

        //Rainbow
        set(LCCItems.chromatic_core, LCCGroupCategory.RAINBOW, 0);
        set(LCCBlocks.rainbow_gate, LCCGroupCategory.RAINBOW, 10);

        set(LCCBlocks.rainbow_grass_block, LCCGroupCategory.RAINBOW, 1000);
        set(LCCBlocks.sugar_grass_block, LCCGroupCategory.RAINBOW, 1001);
        set(LCCBlocks.star_plating, LCCGroupCategory.RAINBOW, 1002);
        group(LCCBlocks.sparkling_grass_block, LCCGroupCategory.RAINBOW, k -> k.getId()+1003);
        set(LCCBlocks.sparkling_dirt, LCCGroupCategory.RAINBOW, 1020);

        set(LCCBlocks.twilight_stone, LCCGroupCategory.RAINBOW, 2000);
        set(LCCBlocks.twilight_cobblestone, LCCGroupCategory.RAINBOW, 2001);

        set(LCCBlocks.vivid_log, LCCGroupCategory.RAINBOW, 3000);
        set(LCCBlocks.vivid_wood, LCCGroupCategory.RAINBOW, 3001);
        set(LCCBlocks.stripped_vivid_log, LCCGroupCategory.RAINBOW, 3002);
        set(LCCBlocks.stripped_vivid_wood, LCCGroupCategory.RAINBOW, 3003);
        set(LCCBlocks.vivid_sapling, LCCGroupCategory.RAINBOW, 3004);
        set(LCCBlocks.vivid_leaves, LCCGroupCategory.RAINBOW, 3010);
        set(LCCBlocks.vivid_planks, LCCGroupCategory.RAINBOW, 3100);
        set(LCCBlocks.vivid_stairs, LCCGroupCategory.RAINBOW, 3101);
        set(LCCBlocks.vivid_slab, LCCGroupCategory.RAINBOW, 3102);
        set(LCCBlocks.vivid_fence, LCCGroupCategory.RAINBOW, 3103);
        set(LCCBlocks.vivid_fence_gate, LCCGroupCategory.RAINBOW, 3104);
        set(LCCBlocks.vivid_door, LCCGroupCategory.RAINBOW, 3200);
        set(LCCBlocks.vivid_trapdoor, LCCGroupCategory.RAINBOW, 3201);
        set(LCCBlocks.vivid_pressure_plate, LCCGroupCategory.RAINBOW, 3202);
        set(LCCBlocks.vivid_button, LCCGroupCategory.RAINBOW, 3203);

        set(LCCBlocks.candy_cane_red, LCCGroupCategory.RAINBOW, 4000);
        set(LCCBlocks.candy_cane_green, LCCGroupCategory.RAINBOW, 4001);
        set(LCCBlocks.candy_cane_blue, LCCGroupCategory.RAINBOW, 4002);
        set(LCCBlocks.stripped_candy_cane, LCCGroupCategory.RAINBOW, 4003);
        set(LCCBlocks.candy_cane_coating_red, LCCGroupCategory.RAINBOW, 4004);
        set(LCCBlocks.candy_cane_coating_green, LCCGroupCategory.RAINBOW, 4005);
        set(LCCBlocks.candy_cane_coating_blue, LCCGroupCategory.RAINBOW, 4006);
        set(LCCBlocks.stripped_candy_cane_coating, LCCGroupCategory.RAINBOW, 4007);
        set(LCCBlocks.refined_candy_cane_red, LCCGroupCategory.RAINBOW, 4008);
        set(LCCBlocks.refined_candy_cane_green, LCCGroupCategory.RAINBOW, 4009);
        set(LCCBlocks.refined_candy_cane_blue, LCCGroupCategory.RAINBOW, 4010);
        set(LCCBlocks.refined_stripped_candy_cane, LCCGroupCategory.RAINBOW, 4011);
        set(LCCBlocks.refined_candy_cane_coating_red, LCCGroupCategory.RAINBOW, 4012);
        set(LCCBlocks.refined_candy_cane_coating_green, LCCGroupCategory.RAINBOW, 4013);
        set(LCCBlocks.refined_candy_cane_coating_blue, LCCGroupCategory.RAINBOW, 4014);
        set(LCCBlocks.refined_stripped_candy_cane_coating, LCCGroupCategory.RAINBOW, 4015);
        set(LCCBlocks.candy_cane_block, LCCGroupCategory.RAINBOW, 4016);

        set(LCCBlocks.channelite.get(null), LCCGroupCategory.RAINBOW, 5000);
        group(LCCBlocks.sparkling_channelite_source, LCCGroupCategory.RAINBOW, k -> k.getId()+5100);
        group(LCCBlocks.twilight_channelite_source, LCCGroupCategory.RAINBOW, k -> k.getId()+5200);

        //Spreaders
        set(LCCItems.spreader_essence, LCCGroupCategory.SPREADERS, 0);
        set(LCCBlocks.spreader_interface, LCCGroupCategory.SPREADERS, 1);
        group(LCCBlocks.spreaders, LCCGroupCategory.SPREADERS, k -> k.getId()+2);

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
        group(LCCItems.computer_casings, LCCGroupCategory.COMPUTING, DyeColor::getId);
        group(LCCItems.computers, LCCGroupCategory.COMPUTING, k -> k.getId()+100);
        group(LCCBlocks.terminals, LCCGroupCategory.COMPUTING, k -> k.getId()+200);
        group(LCCItems.floppy_drives, LCCGroupCategory.COMPUTING, k -> k.getId()+300);
        group(LCCItems.cd_drives, LCCGroupCategory.COMPUTING, k -> k.getId()+400);
        group(LCCItems.card_readers, LCCGroupCategory.COMPUTING, k -> k.getId()+500);
        group(LCCItems.stick_readers, LCCGroupCategory.COMPUTING, k -> k.getId()+600);
        group(LCCItems.drive_bays, LCCGroupCategory.COMPUTING, k -> k.getId()+700);
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
        group(LCCBlocks.classic_cloth, LCCGroupCategory.NOSTALGIA, k -> k.ordinal()+1080);
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
}