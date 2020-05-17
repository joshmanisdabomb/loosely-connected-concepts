package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ChanneliteSourceBlock;
import com.joshmanisdabomb.lcc.block.SparklingGrassBlock;
import com.joshmanisdabomb.lcc.block.SpreaderBlock;
import com.joshmanisdabomb.lcc.block.TerminalBlock;
import com.joshmanisdabomb.lcc.misc.ExtendedDyeColor;
import com.joshmanisdabomb.lcc.registry.*;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LangData extends LanguageProvider {

    public LangData(DataGenerator dg) {
        super(dg, LCC.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addAll(this::addBlock, this::basic, LCCBlocks.test_block);
        this.add(LCCBlocks.test_block_2, this.basic(LCCBlocks.test_block) + " (Horizontal Facing)");
        this.add(LCCBlocks.test_block_3, this.basic(LCCBlocks.test_block) + " (Any Facing)");
        this.add(LCCBlocks.test_block_4, this.basic(LCCBlocks.test_block) + " (Pillar)");
        this.add(LCCBlocks.test_block_5, this.basic(LCCBlocks.test_block) + " (Connected)");
        this.addAll(this::addItem, this::basic, LCCItems.test_item);

        this.addAll(this::addBlock, this::basic, LCCBlocks.ruby_ore, LCCBlocks.topaz_ore, LCCBlocks.sapphire_ore, LCCBlocks.amethyst_ore, LCCBlocks.uranium_ore, LCCBlocks.neon_ore);
        this.addAll(this::addBlock, block -> "Block of " + basic(block).replace(" Storage", ""), LCCBlocks.ruby_storage, LCCBlocks.topaz_storage, LCCBlocks.sapphire_storage, LCCBlocks.amethyst_storage, LCCBlocks.uranium_storage, LCCBlocks.enriched_uranium_storage, LCCBlocks.neon_storage);
        this.addAll(this::addItem, this::basic, LCCItems.ruby, LCCItems.topaz, LCCItems.sapphire, LCCItems.amethyst, LCCItems.uranium_nugget, LCCItems.uranium, LCCItems.enriched_uranium_nugget, LCCItems.enriched_uranium);
        this.add(LCCItems.neon, "Neon Orb");
        this.add(LCCItems.neon_nugget, "Neon Pixel");

        this.addAll(this::addItem, this::basic, LCCItems.ruby_sword, LCCItems.ruby_pickaxe, LCCItems.ruby_shovel, LCCItems.ruby_axe, LCCItems.ruby_hoe, LCCItems.ruby_helmet, LCCItems.ruby_chestplate, LCCItems.ruby_leggings, LCCItems.ruby_boots);
        this.addAll(this::addItem, this::basic, LCCItems.topaz_sword, LCCItems.topaz_pickaxe, LCCItems.topaz_shovel, LCCItems.topaz_axe, LCCItems.topaz_hoe, LCCItems.topaz_helmet, LCCItems.topaz_chestplate, LCCItems.topaz_leggings, LCCItems.topaz_boots);
        this.addAll(this::addItem, this::basic, LCCItems.emerald_sword, LCCItems.emerald_pickaxe, LCCItems.emerald_shovel, LCCItems.emerald_axe, LCCItems.emerald_hoe, LCCItems.emerald_helmet, LCCItems.emerald_chestplate, LCCItems.emerald_leggings, LCCItems.emerald_boots);
        this.addAll(this::addItem, this::basic, LCCItems.sapphire_sword, LCCItems.sapphire_pickaxe, LCCItems.sapphire_shovel, LCCItems.sapphire_axe, LCCItems.sapphire_hoe, LCCItems.sapphire_helmet, LCCItems.sapphire_chestplate, LCCItems.sapphire_leggings, LCCItems.sapphire_boots);
        this.addAll(this::addItem, this::basic, LCCItems.amethyst_sword, LCCItems.amethyst_pickaxe, LCCItems.amethyst_shovel, LCCItems.amethyst_axe, LCCItems.amethyst_hoe, LCCItems.amethyst_helmet, LCCItems.amethyst_chestplate, LCCItems.amethyst_leggings, LCCItems.amethyst_boots);

        this.addAll(this::addBlock, this::basic, LCCBlocks.road, LCCBlocks.hydrated_soul_sand, LCCBlocks.bounce_pad);
        this.addAll(this::addItem, this::basic, LCCItems.chromatic_core);

        this.addAll(this::addBlock, this::basic, LCCBlocks.cracked_mud);
        this.addAll(this::addItem, this::basic, LCCItems.oil_bucket);

        this.addAll(this::addItem, this::basic, LCCItems.spreader_essence);
        this.addAll(this::addBlock, this::basic, LCCBlocks.spreader_interface);
        this.addAll(this::addBlock, block -> mover(basic(block), 0, DyeColor.values()), LCCBlocks.spreaders.values().toArray(new SpreaderBlock[0]));
        this.addAll(this::basic, LCCBlocks.spreader_interface.getTranslationKey() + ".cost", LCCBlocks.spreader_interface.getTranslationKey() + ".speed", LCCBlocks.spreader_interface.getTranslationKey() + ".damage", LCCBlocks.spreader_interface.getTranslationKey() + ".decay", LCCBlocks.spreader_interface.getTranslationKey() + ".eating");
        this.addAll(key -> basic(key).substring(7), LCCBlocks.spreader_interface.getTranslationKey() + ".throughGround", LCCBlocks.spreader_interface.getTranslationKey() + ".throughLiquid", LCCBlocks.spreader_interface.getTranslationKey() + ".throughAir");
        this.add(LCCBlocks.spreader_interface.getTranslationKey() + ".enabled", "Active");
        this.add(LCCBlocks.spreader_interface.getTranslationKey() + ".speed.value", "%1$d - %2$d ticks");
        this.add(LCCBlocks.spreader_interface.getTranslationKey() + ".damage.value", "%1$d hearts");
        this.add(LCCBlocks.spreader_interface.getTranslationKey() + ".decay.value", "%1$d%%");

        this.addAll(this::addBlock, this::basic, LCCBlocks.atomic_bomb);
        this.add(LCCBlocks.nuclear_waste, "Nuclear Debris");

        this.add(LCCItems.gauntlet, "Doom Gauntlet");

        this.addAll(this::addItem, this::basic, LCCItems.red_heart, LCCItems.red_heart_container, LCCItems.red_heart_half, LCCItems.iron_heart, LCCItems.iron_heart_container, LCCItems.iron_heart_half, LCCItems.crystal_heart, LCCItems.crystal_heart_container, LCCItems.crystal_heart_half, LCCItems.temporary_heart, LCCItems.temporary_heart_half);

        this.addAll(this::addBlock, this::basic, LCCBlocks.time_rift, LCCBlocks.classic_grass_block, LCCBlocks.classic_cobblestone, LCCBlocks.classic_planks, LCCBlocks.classic_leaves, LCCBlocks.classic_sapling, LCCBlocks.classic_gravel, LCCBlocks.classic_sponge, LCCBlocks.classic_glass, LCCBlocks.classic_rose, LCCBlocks.classic_cyan_flower, LCCBlocks.classic_iron_block, LCCBlocks.classic_smooth_iron_block, LCCBlocks.classic_gold_block, LCCBlocks.classic_smooth_gold_block, LCCBlocks.classic_diamond_block, LCCBlocks.classic_smooth_diamond_block, LCCBlocks.classic_bricks, LCCBlocks.classic_mossy_cobblestone, LCCBlocks.classic_chest, LCCBlocks.nether_reactor, LCCBlocks.crying_obsidian, LCCBlocks.glowing_obsidian, LCCBlocks.cog);
        this.add(LCCBlocks.classic_tnt, "Classic TNT");
        this.addAll(this::addBlock, block -> mover(basic(block), 8, ExtendedDyeColor.ClassicDyeColor.values()), LCCBlocks.classic_cloth.values().toArray(new Block[0]));
        this.add(LCCBlocks.nether_reactor.getTranslationKey() + ".active", "Active!");
        this.add(LCCBlocks.nether_reactor.getTranslationKey() + ".incorrect", "Not the correct pattern!");
        this.add(LCCBlocks.nether_reactor.getTranslationKey() + ".players", "All players need to be close to the reactor.");
        this.add(LCCBlocks.nether_reactor.getTranslationKey() + ".y", "The reactor must be placed between y=4 and y=221");

        this.addAll(this::addBlock, this::basic, LCCBlocks.networking_cable, LCCBlocks.terminal_cable);
        this.addAll(this::addItem, this::basic, LCCItems.microchip, LCCItems.chipset, LCCItems.floppy_disk, LCCItems.compact_disc, LCCItems.memory_card, LCCItems.memory_stick, LCCItems.hard_disk_drive, LCCItems.solid_state_drive);
        this.add(LCCItems.cpu, "Central Processing Unit");
        this.add(LCCItems.ram, "Random Access Memory");
        this.add(LCCItems.gpu, "Graphics Card");
        this.add(LCCItems.m2, "M.2 Solid State Drive");
        this.add(LCCBlocks.computing, "Computing Block");
        this.add(LCCBlocks.computing.getTranslationKey() + ".casing", "Computer Casing");
        this.addAll(this::addItem, item -> mover(basic(item), 0, DyeColor.values()), LCCItems.computer_casings.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".computer", "Computer");
        this.addAll(this::addItem, item -> mover(basic(item), 0, DyeColor.values()), LCCItems.computers.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".floppy_drive", "Floppy Disk Drive");
        this.addAll(this::addItem, item -> mover(basic(item).replace("Floppy Drive", "Floppy Disk Drive"), 0, DyeColor.values()), LCCItems.floppy_drives.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".cd_drive", "Compact Disc Drive");
        this.addAll(this::addItem, item -> mover(basic(item).replace("Cd Drive", "Compact Disc Drive"), 0, DyeColor.values()), LCCItems.cd_drives.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".card_reader", "Memory Card Reader");
        this.addAll(this::addItem, item -> mover(basic(item).replace("Card Reader", "Memory Card Reader"), 0, DyeColor.values()), LCCItems.card_readers.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".stick_reader", "Memory Stick Reader");
        this.addAll(this::addItem, item -> mover(basic(item).replace("Stick Reader", "Memory Stick Reader"), 0, DyeColor.values()), LCCItems.stick_readers.values().toArray(new Item[0]));
        this.add(LCCBlocks.computing.getTranslationKey() + ".drive_bay", "Drive Bay");
        this.addAll(this::addItem, item -> mover(basic(item), 0, DyeColor.values()), LCCItems.drive_bays.values().toArray(new Item[0]));
        this.add("block.lcc.terminal", "Terminal");
        this.addAll(this::addBlock, block -> mover(basic(block), 0, DyeColor.values()), LCCBlocks.terminals.values().toArray(new TerminalBlock[0]));
        this.add(LCCItems.cpu.getTranslationKey() + ".level", "Speed:");
        this.add(LCCItems.cpu.getTranslationKey() + ".level.value", "%s");
        this.add(LCCItems.ram.getTranslationKey() + ".level", "Size:");
        this.add(LCCItems.ram.getTranslationKey() + ".level.value", "%s");
        this.add(LCCItems.gpu.getTranslationKey() + ".level", "Performance:");
        this.add(LCCItems.gpu.getTranslationKey() + ".level.value", "%s");
        this.add("item.lcc.computing_storage.size", "Size:");
        this.add("item.lcc.computing_storage.size.value", "%s");
        this.add("item.lcc.computing_storage.id", "ID:");
        this.add("item.lcc.computing_storage.id.value", "%s");
        this.add("item.lcc.computing_storage.partitions", "Partitions:");

        this.addAll(this::addBlock, this::basic, LCCBlocks.rainbow_gate, LCCBlocks.rainbow_portal, LCCBlocks.rainbow_grass_block, LCCBlocks.star_plating, LCCBlocks.sparkling_dirt, LCCBlocks.twilight_stone, LCCBlocks.twilight_cobblestone, LCCBlocks.vivid_log, LCCBlocks.vivid_wood, LCCBlocks.stripped_vivid_log, LCCBlocks.stripped_vivid_wood, LCCBlocks.vivid_sapling, LCCBlocks.vivid_leaves, LCCBlocks.vivid_planks, LCCBlocks.vivid_stairs, LCCBlocks.vivid_slab, LCCBlocks.vivid_fence, LCCBlocks.vivid_fence_gate, LCCBlocks.vivid_door, LCCBlocks.vivid_trapdoor, LCCBlocks.vivid_pressure_plate, LCCBlocks.vivid_button);
        this.add(LCCBlocks.sugar_grass_block, "Sugared Grass Block");
        this.addAll(this::addBlock, block -> mover(basic(block), 0, DyeColor.values()), LCCBlocks.sparkling_grass_block.values().toArray(new SparklingGrassBlock[0]));
        this.add(LCCBlocks.channelite.get(null), "Channelite");
        this.addAll(this::addBlock, block -> mover(basic(block), 0, DyeColor.values()), LCCBlocks.sparkling_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));
        this.addAll(this::addBlock, block -> mover(basic(block), 0, DyeColor.values()), LCCBlocks.twilight_channelite_source.values().toArray(new ChanneliteSourceBlock[0]));

        this.addAll(this::addBlock, block -> mover(basic(block), 0, "red", "green", "blue"), LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_blue, LCCBlocks.candy_cane_coating_red, LCCBlocks.candy_cane_coating_green, LCCBlocks.candy_cane_coating_blue);
        this.addAll(this::addBlock, block -> mover(basic(block), 8, "red", "green", "blue"), LCCBlocks.refined_candy_cane_red, LCCBlocks.refined_candy_cane_green, LCCBlocks.refined_candy_cane_blue, LCCBlocks.refined_candy_cane_coating_red, LCCBlocks.refined_candy_cane_coating_green, LCCBlocks.refined_candy_cane_coating_blue);
        this.addAll(this::addBlock, this::basic, LCCBlocks.stripped_candy_cane, LCCBlocks.stripped_candy_cane_coating, LCCBlocks.refined_stripped_candy_cane, LCCBlocks.refined_stripped_candy_cane_coating, LCCBlocks.candy_cane_block);

        this.addAll(this::addItem, this::basic, LCCItems.plastic);

        this.addAll(this::addEffect, this::basic, LCCEffects.stun, LCCEffects.vulnerable, LCCEffects.flammable);
        this.add(LCCEffects.radiation, "Radiation Sickness");

        this.add("deathScreen.lcc.respawn_crying_obsidian", "Respawn at Crying Obsidian");

        this.add("itemGroup.lcc", "Loosely Connected Concepts");

        this.addAll(this::addBiome, this::basic, LCCBiomes.wasteland, LCCBiomes.rainbow_candyland, LCCBiomes.rainbow_colorful, LCCBiomes.rainbow_starlight, LCCBiomes.rainbow_terrene);

        this.add("death.attack.lcc.gauntlet_uppercut", "%1$s was struck by %2$s's uppercut");
        this.add("death.attack.lcc.gauntlet_uppercut.item", "%1$s was struck by %2$s's uppercut using %3$s");
        this.add("death.attack.lcc.gauntlet_punch", "%1$s was obliterated by %2$s's punch");
        this.add("death.attack.lcc.gauntlet_punch.item", "%1$s was obliterated by %2$s's punch using %3$s");
        this.add("death.attack.lcc.gauntlet_punch_wall", "%1$s was launched into the wall");
        this.add("death.attack.lcc.gauntlet_punch_wall.player", "%1$s was launched into the wall by %2$s's punch");

        this.add("death.attack.lcc.nuke", "%1$s was nuked");
        this.add("death.attack.lcc.nuke.player", "%1$s was nuked by %2$s");

        this.addAll(this::addEntityType, this::basic, LCCEntities.nuclear_explosion, LCCEntities.classic_zombie_pigman, LCCEntities.atomic_bomb);
        this.add(LCCEntities.classic_tnt, "Classic TNT");
        this.add(LCCEntities.hitbox, "Internal Hitbox");

        this.add("advancements.lcc.main.root.title", "Loosely Connected Concepts");
        this.add("advancements.lcc.main.root.description", "Main progression line of Loosely Connected Concepts");
        this.add("advancements.lcc.main.all_gems.title", "Gem Collector");
        this.add("advancements.lcc.main.all_gems.description", "Available in 6 designer colours!");
        this.add("advancements.lcc.main.rainbow_dimension.title", "One Way Trip");
        this.add("advancements.lcc.main.rainbow_dimension.description", "Use the rainbow gate, 6 gem blocks and a chromatic core to enter the Rainbow Dimension");
        this.add("advancements.lcc.main.enrich.title", "Enrichment Activities");
        this.add("advancements.lcc.main.enrich.description", "Our words are backed with nuclear weapons!");
        this.add("advancements.lcc.main.enrich_first.title", "Nuclear Arms Race");
        this.add("advancements.lcc.main.enrich_first.description", "Be the first player in multiplayer to enrich uranium.");

        this.add("advancements.lcc.rainbow.root.title", "LCC: Rainbow Dimension");
        this.add("advancements.lcc.rainbow.root.description", "minecraft; the way home");
        this.add("advancements.lcc.rainbow.stone.title", "This Seems Familiar");
        this.add("advancements.lcc.rainbow.stone.description", "Mine twilight stone with a Rainbow Wood or Candy Cane Pickaxe");
        this.add("advancements.lcc.rainbow.neon.title", "RTX On");
        this.add("advancements.lcc.rainbow.neon.description", "Finally some lighting options!");
        this.add("advancements.lcc.rainbow.temple.title", "Temple Run");
        this.add("advancements.lcc.rainbow.temple.description", "Discover the Rainbow Temple. Dare to enter?");
        this.add("advancements.lcc.rainbow.temple_boss.title", "Guard This!");
        this.add("advancements.lcc.rainbow.temple_boss.description", "Destroy the Rainbow Guardian");
        this.add("advancements.lcc.rainbow.leave.title", "Technology Repaired");
        this.add("advancements.lcc.rainbow.leave.description", "Repair the original rainbow gate (TODO goal)");

        this.add("subtitle.lcc.block.hydrated_soul_sand.jump", "Hydrated Soul Sand squelches");
        this.add("subtitle.lcc.block.bounce_pad.jump", "Bounce Pad protracts");
        this.add("gui.lcc.computer.on", "Turn On");
        this.add("gui.lcc.computer.off", "Turn Off");
        this.add("gui.lcc.atomic_bomb.detonate", "Detonate");
        this.add("computing.lcc.bios", "BIOS");
        this.add("computing.lcc.console", "Console OS");
        this.add("computing.lcc.graphical", "Graphical OS");
        this.add("computing.lcc.items", "Item Storage");
        this.add("computing.lcc.terminal.no_input", "No Input Detected");
        this.add("computing.lcc.terminal.input_conflict", "Conflicting Inputs Detected");
        this.add("computing.lcc.bios.no_os", "No operating system detected!\nPress any key to retry...");
        this.add("computing.lcc.bios.partial_os", "Please insert the next disk of %s (seeking sector %s) and press any key...");
        this.add("computing.lcc.bios.partial_os.option", "%s - seeking sector %s");
        this.add("computing.lcc.bios.partial_os.multiple", "Please insert the next disk for an operating system and press any key...");
        this.add("computing.lcc.console.unknown", "Unknown command! Type \"help\" for a list of commands.");
        this.add("computing.lcc.console.few_args", "Too few arguments specified. Type \"help %s\" for usage.");
        this.add("computing.lcc.console.many_args", "Too many arguments specified. Type \"help %s\" for usage.");
        this.add("computing.lcc.console.buffer", "(%s/%s) use arrow keys and enter");
        this.add("computing.lcc.console.prompt", "Y to confirm, N to cancel");
        this.add("computing.lcc.console.cancel", "User cancelled action.");
        this.add("computing.lcc.console.help.available", "Available commands:\n%s");
        this.add("computing.lcc.console.help.aliases", "Aliases: %s");
        this.add("computing.lcc.console.map.no_partitions", "No partitions");
        this.add("computing.lcc.console.use.success", "Using \"%1$s\" (#%2$s) on disk \"%3$s\" (#%4$s).");
        this.add("computing.lcc.console.use.no_results", "Could not find partition \"%1$s\".\nType \"map\" to see available partitions.");
        this.add("computing.lcc.console.use.no_results.disk", "Could not find partition \"%1$s\" on a disk matching \"%2$s\".\nType \"map\" to see available partitions and disks.");
        this.add("computing.lcc.console.use.many_results", "More than one partition matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.use.many_results.disk", "More than one partition matches \"%1$s\" on a disk matching \"%2$s\".\nPlease refine your argument.");
        this.add("computing.lcc.console.use.none", "No partition in use.");
        this.add("computing.lcc.console.cd.success", "Current directory changed to \"%s\".");
        this.add("computing.lcc.console.mkpart.success", "Added %s partition \"%s\" (#%s) with size %s on disk \"%s\" (#%s)\nUsing new partition.");
        this.add("computing.lcc.console.mkpart.invalid_type", "Valid partition types:\n%s");
        this.add("computing.lcc.console.mkpart.invalid_size", "Please use a positive integer for size.");
        this.add("computing.lcc.console.mkpart.low_space", "The given partition size is too large for this disk.\nMaximum size allowed: %s");
        this.add("computing.lcc.console.mkpart.no_space", "No free partitionable space on disk.");
        this.add("computing.lcc.console.mkpart.no_disk", "No disk specified or in use. Please specify a disk.");
        this.add("computing.lcc.console.mkpart.invalid_disk", "Could not find disk \"%1$s\".\nType \"map\" to see available disks.");
        this.add("computing.lcc.console.mkpart.many_disk", "More than one disk matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.mkpart.existing", "A partition named \"%s\" already exists on disk \"%s\" (#%s)");
        this.add("computing.lcc.console.rmpart.prompt", "\"%s\" (#%s) on disk \"%s\" (#%s)\nALL DATA WILL BE LOST!\nAre you sure you wish to delete this partition?");
        this.add("computing.lcc.console.rmpart.success", "Partition \"%s\" deleted.");
        this.add("computing.lcc.console.rmpart.no_results", "Could not find partition \"%1$s\".\nType \"map\" to see available partitions.");
        this.add("computing.lcc.console.rmpart.no_results.disk", "Could not find partition \"%1$s\" on a disk matching \"%2$s\".\nType \"map\" to see available partitions and disks.");
        this.add("computing.lcc.console.rmpart.many_results", "More than one partition matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.rmpart.many_results.disk", "More than one partition matches \"%1$s\" on a disk matching \"%2$s\".\nPlease refine your argument.");
        this.add("computing.lcc.console.rmpart.invalid_use", "No partition specified or in use. Please specify a partition.");
        this.add("computing.lcc.console.rmpart.missing", "Partition now missing or invalid. No action taken.");
        this.add("computing.lcc.console.install.success", "Full install successful on \"%s\" (#%s) on disk \"%s\" (#%s).");
        this.add("computing.lcc.console.install.success.partial", "Partial install successful on \"%s\" (#%s) on disk \"%s\" (#%s).");
        this.add("computing.lcc.console.install.success.partial.continue", "Type \"install %s\" to continue install on a disk with free space of %s.");
        this.add("computing.lcc.console.install.invalid_sector", "Please use a positive integer or zero for sector.");
        this.add("computing.lcc.console.install.invalid_size", "Please use a positive integer for size.");
        this.add("computing.lcc.console.install.low_space", "The given partition size is too large for this disk.\nMaximum size allowed: %s");
        this.add("computing.lcc.console.install.no_space", "No free partitionable space on disk.");
        this.add("computing.lcc.console.install.no_disk", "No disk specified or in use. Please specify a disk.");
        this.add("computing.lcc.console.install.invalid_disk", "Could not find disk \"%1$s\".\nType \"map\" to see available disks.");
        this.add("computing.lcc.console.install.many_disk", "More than one disk matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.install.os_installed", "A full operating system is already installed on this disk.");
        this.add("computing.lcc.console.install.max_sector", "The given sector is larger than the console operating system (size %s).");
        this.add("computing.lcc.console.install.max_size", "The given size is larger than the console operating system (size left %s).");
        this.add("computing.lcc.console.install.existing", "A partition named \"%s\" already exists on disk \"%s\" (#%s)");
        this.add("computing.lcc.console.label.success.disk", "Label of disk #%s changed to \"%s\"");
        this.add("computing.lcc.console.label.success.partition", "Label of partition #%s changed to \"%s\"");
        this.add("computing.lcc.console.label.no_partition", "No partition in use. Please specify a disk or partition.");
        this.add("computing.lcc.console.label.no_results", "Could not find disk or partition \"%1$s\".\nType \"map\" to see available items.");
        this.add("computing.lcc.console.label.many_results", "More than one disk or partition matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.label.no_results.disk", "Could not find partition \"%1$s\" on a disk matching \"%2$s\".\nType \"map\" to see available partitions and disks.");
        this.add("computing.lcc.console.label.many_results.disk", "More than one partition matches \"%1$s\" on a disk matching \"%2$s\".\nPlease refine your argument.");
        this.add("computing.lcc.console.resize.success", "Resized partition \"%s\" (#%s) on disk \"%s\" (#%s) from %s to %s.");
        this.add("computing.lcc.console.resize.os", "Cannot resize an operating system partition.");
        this.add("computing.lcc.console.resize.used_space", "The new size is not enough for the currently used space on the partition.\nMinimum size allowed: %s");
        this.add("computing.lcc.console.resize.low_space", "The given partition size is too large for this disk.\nMaximum size allowed: %s");
        this.add("computing.lcc.console.resize.no_space", "No free partitionable space on disk.");
        this.add("computing.lcc.console.resize.no_partition", "No partition in use. Please specify a disk or partition.");
        this.add("computing.lcc.console.resize.no_results", "Could not find partition \"%1$s\".\nType \"map\" to see available partitions.");
        this.add("computing.lcc.console.resize.many_results", "More than one disk or partition matches \"%1$s\" on the system.\nPlease refine your argument.");
        this.add("computing.lcc.console.resize.no_results.disk", "Could not find partition \"%1$s\" on a disk matching \"%2$s\".\nType \"map\" to see available partitions and disks.");
        this.add("computing.lcc.console.resize.many_results.disk", "More than one partition matches \"%1$s\" on a disk matching \"%2$s\".\nPlease refine your argument.");
        this.add("computing.lcc.console.resize.invalid_size", "Please use a positive integer for size.");
        this.add("computing.lcc.console.resize.no_change", "The partition has not changed in size.");
        this.add("computing.lcc.console.meta.help", "help,?,man,usage,describe");
        this.add("computing.lcc.console.meta.help.usage", "help [command]");
        this.add("computing.lcc.console.meta.help.description", "Gives a list of commands that can be used. Specify a command to get its usage and description.");
        this.add("computing.lcc.console.meta.clear", "clear,cls,clr,clean");
        this.add("computing.lcc.console.meta.clear.usage", "clear");
        this.add("computing.lcc.console.meta.clear.description", "Clears the terminal output.");
        this.add("computing.lcc.console.meta.map", "map,usable,usables,parts,partitions,disks,drives");
        this.add("computing.lcc.console.meta.map.usage", "map [filter]");
        this.add("computing.lcc.console.meta.map.description", "View all the partitions available to the system. Optionally filter by name or prefix with hash for ID.");
        this.add("computing.lcc.console.meta.use", "use,using,load,loaded,part,partition,disk,drive,mount");
        this.add("computing.lcc.console.meta.use.usage", "use [partition] [disk]");
        this.add("computing.lcc.console.meta.use.description", "Start working with the partition that matches the given name or ID, optionally under the given disk name or ID. Give no arguments to see the current partition in use.");
        this.add("computing.lcc.console.meta.ls", "ls,list,dir");
        this.add("computing.lcc.console.meta.ls.usage", "ls");
        this.add("computing.lcc.console.meta.ls.description", "Lists the items in the current directory.");
        this.add("computing.lcc.console.meta.cd", "cd,change,goto,here,where,location,locate");
        this.add("computing.lcc.console.meta.cd.usage", "cd [directory]");
        this.add("computing.lcc.console.meta.cd.description", "Change the current directory to the specified destination, relative to the current directory unless prefixed with a slash. Give no arguments to see the current directory.");
        this.add("computing.lcc.console.meta.mkdir", "mkdir,mk");
        this.add("computing.lcc.console.meta.mkdir.usage", "mkdir <directory>");
        this.add("computing.lcc.console.meta.mkdir.description", "Create the given directory relative to the current directory.");
        this.add("computing.lcc.console.meta.hold", "hold,take,retrieve");
        this.add("computing.lcc.console.meta.hold.usage", "hold <item>");
        this.add("computing.lcc.console.meta.hold.description", "Grab the given item from the current directory.");
        this.add("computing.lcc.console.meta.mkpart", "mkpart,createpart,mp");
        this.add("computing.lcc.console.meta.mkpart.usage", "mkpart <name> <type> [disk] [size]");
        this.add("computing.lcc.console.meta.mkpart.description", "Create a named partition on the current or specified disk of the given type.\nSpecifying a size will limit the partition to that amount of space on the disk.");
        this.add("computing.lcc.console.meta.rmpart", "rmpart,delpart,deletepart,rp,dp");
        this.add("computing.lcc.console.meta.rmpart.usage", "rmpart [partition] [disk]");
        this.add("computing.lcc.console.meta.rmpart.description", "Delete the partition that matches the given name, optionally under the given disk name. Give no arguments to delete the current partition in use.");
        this.add("computing.lcc.console.meta.label", "label,labelpart,rnpart,mvpart,renamepart,movepart,lp");
        this.add("computing.lcc.console.meta.label.usage", "label <label> [partition|disk]");
        this.add("computing.lcc.console.meta.label.description", "Rename the current partition or the specified partition or disk to the given label.");
        this.add("computing.lcc.console.meta.resize", "resize,rspart,repart,resizepart,sizepart,sp,rep");
        this.add("computing.lcc.console.meta.resize.usage", "resize [size] [partition] [disk]");
        this.add("computing.lcc.console.meta.resize.description", "Resize the current or specified partition to the specified size, optionally under the given disk name. No given size defaults to the maximum available space on the disk.");
        this.add("computing.lcc.console.meta.install", "install,image,os,console,consoleos");
        this.add("computing.lcc.console.meta.install.usage", "install [sector[:size]] [disk] [name]");
        this.add("computing.lcc.console.meta.install.description", "Installs the console operating system on the specified or current disk. The starting sector can be specified to start writing the OS after the beginning, useful when multiple disks are required to store the OS.");
        this.add("computing.lcc.console.meta.reboot", "reboot,restart,reset");
        this.add("computing.lcc.console.meta.reboot.usage", "reboot");
        this.add("computing.lcc.console.meta.reboot.description", "Restarts the machine.");
        this.add("computing.lcc.console.meta.shutdown", "shutdown,end,exit,close,off");
        this.add("computing.lcc.console.meta.shutdown.usage", "shutdown");
        this.add("computing.lcc.console.meta.shutdown.description", "Shuts down the machine.");
        this.add("computing.lcc.graphical.time", "Day %d, %s");
    }

    private String basic(ForgeRegistryEntry<?> e) {
        StringBuilder builder = new StringBuilder();
        for (String s : e.getRegistryName().getPath().replace('_', ' ').split(" ")) {
            builder.append(s.substring(0,1).toUpperCase()).append(s.substring(1)).append(' ');
        }
        return builder.toString().trim();
    }

    private String basic(String key) {
        String[] components = key.split("\\.");
        StringBuilder builder = new StringBuilder();
        for (String s : components[components.length - 1].replace('_', ' ').split(" ")) {
            builder.append(s.substring(0,1).toUpperCase()).append(s.substring(1)).append(' ');
        }
        return builder.toString().trim();
    }

    private String mover(String translated, int insert, String... values) {
        for (String s : Arrays.stream(values).sorted(Comparator.comparing(String::length).reversed()).toArray(String[]::new)) {
            String name = this.basic(s);
            if (translated.endsWith(name)) {
                StringBuilder builder = new StringBuilder(translated.substring(0, translated.length() - name.length()));
                return builder.insert(insert, name + " ").toString().trim();
            }
        }
        return translated;
    }

    private String mover(String translated, int insert, IStringSerializable... values) {
        return this.mover(translated, insert, Arrays.stream(values).map(IStringSerializable::getName).toArray(String[]::new));
    }

    private <T extends ForgeRegistryEntry<?>> void addAll(BiConsumer<Supplier<T>, String> adder, Function<T, String> translator, T... values) {
        for (T value : values) adder.accept(() -> value, translator.apply(value));
    }

    private void addAll(Function<String, String> translator, String... keys) {
        for (String k : keys) this.add(k, translator.apply(k));
    }

}
