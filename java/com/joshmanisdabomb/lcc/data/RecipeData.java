package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.misc.ExtendedDyeColor;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.registry.LCCRecipes;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeData extends RecipeProvider {

    public RecipeData(DataGenerator dg) {
        super(dg);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //Ores, Ingots and Storage
        this.storage(LCCBlocks.ruby_storage, LCCItems.ruby, consumer);
        this.storage(LCCBlocks.topaz_storage, LCCItems.topaz, consumer);
        this.storage(LCCBlocks.sapphire_storage, LCCItems.sapphire, consumer);
        this.storage(LCCBlocks.amethyst_storage, LCCItems.amethyst, consumer);
        this.storage(LCCBlocks.uranium_storage, LCCItems.uranium, consumer);
        this.storage(LCCBlocks.enriched_uranium_storage, LCCItems.enriched_uranium, consumer);
        this.nugget(LCCItems.uranium_nugget, LCCItems.uranium, consumer);
        this.nugget(LCCItems.enriched_uranium_nugget, LCCItems.enriched_uranium, consumer);
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(LCCBlocks.ruby_ore.asItem()), LCCItems.ruby, 1.0F, 100).addCriterion(has(LCCBlocks.ruby_ore), this.hasItem(LCCBlocks.ruby_ore)).build(consumer, new ResourceLocation(LCC.MODID, name(LCCItems.ruby) + "_from_blasting"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(LCCBlocks.topaz_ore.asItem()), LCCItems.topaz, 1.0F, 100).addCriterion(has(LCCBlocks.topaz_ore), this.hasItem(LCCBlocks.topaz_ore)).build(consumer, new ResourceLocation(LCC.MODID, name(LCCItems.topaz) + "_from_blasting"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(LCCBlocks.sapphire_ore.asItem()), LCCItems.sapphire, 1.0F, 100).addCriterion(has(LCCBlocks.sapphire_ore), this.hasItem(LCCBlocks.sapphire_ore)).build(consumer, new ResourceLocation(LCC.MODID, name(LCCItems.sapphire) + "_from_blasting"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(LCCBlocks.amethyst_ore.asItem()), LCCItems.amethyst, 1.0F, 100).addCriterion(has(LCCBlocks.amethyst_ore), this.hasItem(LCCBlocks.amethyst_ore)).build(consumer, new ResourceLocation(LCC.MODID, name(LCCItems.amethyst) + "_from_blasting"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(LCCBlocks.uranium_ore.asItem()), LCCItems.uranium, 1.0F, 100).addCriterion(has(LCCBlocks.uranium_ore), this.hasItem(LCCBlocks.uranium_ore)).build(consumer, new ResourceLocation(LCC.MODID, name(LCCItems.uranium) + "_from_blasting"));

        //Tools & Armour
        this.toolset(LCCItems.ruby_sword, LCCItems.ruby_pickaxe, LCCItems.ruby_shovel, LCCItems.ruby_axe, LCCItems.ruby_hoe, LCCItems.ruby, Items.STICK, consumer);
        this.armorset(LCCItems.ruby_helmet, LCCItems.ruby_chestplate, LCCItems.ruby_leggings, LCCItems.ruby_boots, LCCItems.ruby, consumer);
        this.toolset(LCCItems.topaz_sword, LCCItems.topaz_pickaxe, LCCItems.topaz_shovel, LCCItems.topaz_axe, LCCItems.topaz_hoe, LCCItems.topaz, Items.STICK, consumer);
        this.armorset(LCCItems.topaz_helmet, LCCItems.topaz_chestplate, LCCItems.topaz_leggings, LCCItems.topaz_boots, LCCItems.topaz, consumer);
        this.toolset(LCCItems.emerald_sword, LCCItems.emerald_pickaxe, LCCItems.emerald_shovel, LCCItems.emerald_axe, LCCItems.emerald_hoe, Items.EMERALD, Items.STICK, consumer);
        this.armorset(LCCItems.emerald_helmet, LCCItems.emerald_chestplate, LCCItems.emerald_leggings, LCCItems.emerald_boots, Items.EMERALD, consumer);
        this.toolset(LCCItems.sapphire_sword, LCCItems.sapphire_pickaxe, LCCItems.sapphire_shovel, LCCItems.sapphire_axe, LCCItems.sapphire_hoe, LCCItems.sapphire, Items.STICK, consumer);
        this.armorset(LCCItems.sapphire_helmet, LCCItems.sapphire_chestplate, LCCItems.sapphire_leggings, LCCItems.sapphire_boots, LCCItems.sapphire, consumer);
        this.toolset(LCCItems.amethyst_sword, LCCItems.amethyst_pickaxe, LCCItems.amethyst_shovel, LCCItems.amethyst_axe, LCCItems.amethyst_hoe, LCCItems.amethyst, Items.STICK, consumer);
        this.armorset(LCCItems.amethyst_helmet, LCCItems.amethyst_chestplate, LCCItems.amethyst_leggings, LCCItems.amethyst_boots, LCCItems.amethyst, consumer);
        this.toolset(LCCItems.vivid_sword, LCCItems.vivid_pickaxe, LCCItems.vivid_shovel, LCCItems.vivid_axe, LCCItems.vivid_hoe, LCCBlocks.vivid_planks, Items.STICK, consumer);
        this.toolset(LCCItems.red_candy_cane_sword, LCCItems.red_candy_cane_pickaxe, LCCItems.red_candy_cane_shovel, LCCItems.red_candy_cane_axe, LCCItems.red_candy_cane_hoe, LCCTags.RED_CANDY_CANES.item, Items.STICK, consumer);
        this.toolset(LCCItems.green_candy_cane_sword, LCCItems.green_candy_cane_pickaxe, LCCItems.green_candy_cane_shovel, LCCItems.green_candy_cane_axe, LCCItems.green_candy_cane_hoe, LCCTags.GREEN_CANDY_CANES.item, Items.STICK, consumer);
        this.toolset(LCCItems.blue_candy_cane_sword, LCCItems.blue_candy_cane_pickaxe, LCCItems.blue_candy_cane_shovel, LCCItems.blue_candy_cane_axe, LCCItems.blue_candy_cane_hoe, LCCTags.BLUE_CANDY_CANES.item, Items.STICK, consumer);
        this.toolset(LCCItems.neon_sword, LCCItems.neon_pickaxe, LCCItems.neon_shovel, LCCItems.neon_axe, LCCItems.neon_hoe, LCCItems.neon, Items.STICK, consumer);
        this.armorset(LCCItems.neon_helmet, LCCItems.neon_chestplate, LCCItems.neon_leggings, LCCItems.neon_boots, LCCItems.neon, consumer);

        //Misc
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.atomic_bomb)
            .patternLine("ccc")
            .patternLine("bdc")
            .patternLine("ccc")
            .key('c', Blocks.IRON_BLOCK)
            .key('b', ItemTags.BUTTONS)
            .key('d', Blocks.DISPENSER)
            .addCriterion(has(LCCItems.enriched_uranium_nugget), this.hasItem(LCCItems.enriched_uranium_nugget))
            .addCriterion(has(LCCItems.enriched_uranium), this.hasItem(LCCItems.enriched_uranium))
            .addCriterion(has(LCCBlocks.enriched_uranium_storage), this.hasItem(LCCBlocks.enriched_uranium_storage))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.bounce_pad, 6)
            .patternLine("rwr")
            .patternLine("ipi")
            .patternLine("sss")
            .key('r', Items.REPEATER)
            .key('w', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
            .key('i', Tags.Items.STORAGE_BLOCKS_IRON)
            .key('p', Blocks.PISTON)
            .key('s', LCCBlocks.hydrated_soul_sand)
            .addCriterion(has(LCCBlocks.hydrated_soul_sand), this.hasItem(LCCBlocks.hydrated_soul_sand))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCItems.chromatic_core)
            .patternLine("123")
            .patternLine("4X5")
            .patternLine("678")
            .key('1', Tags.Items.DYES_RED)
            .key('2', Tags.Items.DYES_ORANGE)
            .key('3', Tags.Items.DYES_YELLOW)
            .key('4', Tags.Items.DYES_LIME)
            .key('5', Tags.Items.DYES_CYAN)
            .key('6', Tags.Items.DYES_BLUE)
            .key('7', Tags.Items.DYES_PURPLE)
            .key('8', Tags.Items.DYES_PINK)
            .key('X', Blocks.GLASS)
            .addCriterion(has(Blocks.GLASS), this.hasItem(Blocks.GLASS))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCItems.gauntlet) //TODO temporary, maybe from rainbow temples, gemstones may need to be enhanced and added one by one?
            .patternLine("12 ")
            .patternLine("453")
            .patternLine("GP6")
            .key('1', LCCItems.ruby)
            .key('2', LCCItems.topaz)
            .key('3', Items.EMERALD)
            .key('4', Items.DIAMOND)
            .key('5', LCCItems.sapphire)
            .key('6', LCCItems.amethyst)
            .key('G', Tags.Items.STORAGE_BLOCKS_GOLD)
            .key('P', Tags.Items.NETHER_STARS)
            .addCriterion(has(Tags.Items.NETHER_STARS), this.hasItem(Tags.Items.NETHER_STARS))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.rainbow_gate, 8)
            .patternLine("123")
            .patternLine("B B")
            .patternLine("456")
            .key('1', Tags.Items.STORAGE_BLOCKS_COAL)
            .key('2', Tags.Items.STORAGE_BLOCKS_REDSTONE)
            .key('3', Tags.Items.STORAGE_BLOCKS_GOLD)
            .key('4', LCCBlocks.uranium_storage)
            .key('5', Tags.Items.STORAGE_BLOCKS_LAPIS)
            .key('6', Tags.Items.STORAGE_BLOCKS_IRON)
            .key('B', Blocks.DARK_PRISMARINE)
            .addCriterion(has(LCCItems.chromatic_core), this.hasItem(LCCItems.chromatic_core))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.road, 8) //TODO temporary, petroleum or something bit more techy in recipe
            .addIngredient(Tags.Items.SLIMEBALLS)
            .addIngredient(Items.WATER_BUCKET)
            .addIngredient(Blocks.GRAY_CONCRETE_POWDER, 7)
            .addCriterion(has(Tags.Items.SLIMEBALLS), this.hasItem(Tags.Items.SLIMEBALLS))
            .build(consumer);

        //Spreaders
        ShapedRecipeBuilder.shapedRecipe(LCCItems.spreader_essence)
            .patternLine("DDD")
            .patternLine("DFD")
            .patternLine("DDD")
            .key('D', Blocks.DIRT)
            .key('F', Blocks.CHORUS_FLOWER)
            .addCriterion(has(Blocks.CHORUS_FLOWER), this.hasItem(Blocks.CHORUS_FLOWER))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.spreader_interface)
            .patternLine(" C ")
            .patternLine(" B ")
            .patternLine("LSL")
            .key('C', LCCItems.chromatic_core)
            .key('B', Tags.Items.STORAGE_BLOCKS_IRON)
            .key('S', LCCItems.spreader_essence)
            .key('L', Tags.Items.INGOTS_IRON)
            .addCriterion(has(LCCItems.chromatic_core), this.hasItem(LCCItems.chromatic_core))
            .build(consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.WHITE), Blocks.WHITE_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.ORANGE), Blocks.ORANGE_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.MAGENTA), Blocks.MAGENTA_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.LIGHT_BLUE), Blocks.LIGHT_BLUE_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.YELLOW), Blocks.YELLOW_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.LIME), Blocks.LIME_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.PINK), Blocks.PINK_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.GRAY), Blocks.GRAY_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.LIGHT_GRAY), Blocks.LIGHT_GRAY_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.CYAN), Blocks.CYAN_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.PURPLE), Blocks.PURPLE_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.BLUE), Blocks.BLUE_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.BROWN), Blocks.BROWN_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.GREEN), Blocks.GREEN_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.RED), Blocks.RED_CONCRETE_POWDER, consumer);
        spreader(LCCBlocks.spreaders.get(DyeColor.BLACK), Blocks.BLACK_CONCRETE_POWDER, consumer);

        //Candy Canes
        this.candyCane(LCCBlocks.candy_cane_red, LCCBlocks.candy_cane_coating_red, LCCBlocks.refined_candy_cane_red, LCCBlocks.refined_candy_cane_coating_red, consumer);
        this.candyCane(LCCBlocks.candy_cane_blue, LCCBlocks.candy_cane_coating_blue, LCCBlocks.refined_candy_cane_blue, LCCBlocks.refined_candy_cane_coating_blue, consumer);
        this.candyCane(LCCBlocks.candy_cane_green, LCCBlocks.candy_cane_coating_green, LCCBlocks.refined_candy_cane_green, LCCBlocks.refined_candy_cane_coating_green, consumer);
        this.candyCane(LCCBlocks.stripped_candy_cane, LCCBlocks.stripped_candy_cane_coating, LCCBlocks.refined_stripped_candy_cane, LCCBlocks.refined_stripped_candy_cane_coating, consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.candy_cane_block)
            .addIngredient(LCCBlocks.refined_stripped_candy_cane)
            .addCriterion(has(LCCBlocks.refined_stripped_candy_cane), this.hasItem(LCCBlocks.refined_stripped_candy_cane))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.candy_cane_block)
            .addIngredient(LCCBlocks.refined_stripped_candy_cane_coating)
            .addCriterion(has(LCCBlocks.refined_stripped_candy_cane_coating), this.hasItem(LCCBlocks.refined_stripped_candy_cane_coating))
            .build(consumer, new ResourceLocation(LCC.MODID, name(LCCBlocks.candy_cane_block) + "_coating"));

        //Time Rift Recipes TODO New dye colors.
        this.timerift(Blocks.BRICKS, LCCBlocks.classic_bricks, 1, consumer);
        this.timerift(Tags.Items.CHESTS_WOODEN, LCCBlocks.classic_chest, 1, consumer);
        this.timerift(Tags.Items.DYES_RED, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.RED), 1, consumer);
        this.timerift(Tags.Items.DYES_ORANGE, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.ORANGE), 1, consumer);
        this.timerift(Tags.Items.DYES_YELLOW, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.YELLOW), 1, consumer);
        this.timerift(Tags.Items.DYES_LIME, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.LIME), 1, consumer);
        this.timerift(Tags.Items.DYES_GREEN, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.GREEN), 1, consumer);
        this.timerift(Tags.Items.DYES_CYAN, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.TURQUOISE), 1, consumer);
        //this.timerift(Tags.Items.DYES_AQUA, LCCBlocks.classic_cloth.get(Colors.ClassicDyeColor.AQUA), consumer);
        this.timerift(Tags.Items.DYES_LIGHT_BLUE, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.LIGHT_BLUE), 1, consumer);
        //this.timerift(Tags.Items.DYES_LAVENDER, LCCBlocks.classic_cloth.get(Colors.ClassicDyeColor.LAVENDER), consumer);
        this.timerift(Tags.Items.DYES_PURPLE, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.PURPLE), 1, consumer);
        //this.timerift(Tags.Items.DYES_LIGHT_PURPLE, LCCBlocks.classic_cloth.get(Colors.ClassicDyeColor.LIGHT_PURPLE), consumer);
        this.timerift(Tags.Items.DYES_MAGENTA, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.MAGENTA), 1, consumer);
        //this.timerift(Tags.Items.DYES_HOT_PINK, LCCBlocks.classic_cloth.get(Colors.ClassicDyeColor.HOT_PINK), consumer);
        this.timerift(Tags.Items.DYES_GRAY, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.GRAY), 1, consumer);
        this.timerift(Tags.Items.DYES_LIGHT_GRAY, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.LIGHT_GRAY), 1, consumer);
        this.timerift(Tags.Items.DYES_WHITE, LCCBlocks.classic_cloth.get(ExtendedDyeColor.ClassicDyeColor.WHITE), 1, consumer);
        this.timerift(Blocks.COBBLESTONE, LCCBlocks.classic_cobblestone, 1, consumer);
        this.timerift(Items.CORNFLOWER, LCCBlocks.classic_cyan_flower, 1, consumer);
        this.timerift(Items.BLUE_ORCHID, LCCBlocks.classic_cyan_flower, 1, consumer);
        this.timerift(Tags.Items.STORAGE_BLOCKS_DIAMOND, LCCBlocks.classic_diamond_block, 1, consumer);
        this.timerift(Tags.Items.STORAGE_BLOCKS_GOLD, LCCBlocks.classic_gold_block, 1, consumer);
        this.timerift(Tags.Items.STORAGE_BLOCKS_IRON, LCCBlocks.classic_iron_block, 1, consumer);
        this.timerift(Tags.Items.GLASS, LCCBlocks.classic_glass, 1, consumer);
        this.timerift(Blocks.GRASS_BLOCK, LCCBlocks.classic_grass_block, 1, consumer);
        this.timerift(Blocks.GRAVEL, LCCBlocks.classic_gravel, 1, consumer);
        this.timerift(ItemTags.LEAVES, LCCBlocks.classic_leaves, 1, consumer);
        this.timerift(Blocks.MOSSY_COBBLESTONE, LCCBlocks.classic_mossy_cobblestone, 1, consumer);
        this.timerift(ItemTags.PLANKS, LCCBlocks.classic_planks, 1, consumer);
        this.timerift(Blocks.POPPY, LCCBlocks.classic_rose, 1, consumer);
        this.timerift(Blocks.ROSE_BUSH, LCCBlocks.classic_rose, 2, consumer);
        this.timerift(ItemTags.SAPLINGS, LCCBlocks.classic_sapling, 1, consumer);
        this.timerift(Blocks.SPONGE, LCCBlocks.classic_sponge, 1, consumer);
        this.timerift(Blocks.WET_SPONGE, LCCBlocks.classic_sponge, 1, consumer);
        this.timerift(Blocks.TNT, LCCBlocks.classic_tnt, 1, consumer);
        this.timerift(Tags.Items.DUSTS_REDSTONE, LCCBlocks.cog, 1, consumer);
        //this.timerift(Blocks.CRYING_OBSIDIAN, LCCBlocks.crying_obsidian, 1, consumer);
        this.timerift(Tags.Items.NETHER_STARS, LCCBlocks.nether_reactor, 1, consumer);
        this.timerift(Blocks.OBSIDIAN, LCCBlocks.glowing_obsidian, 1, consumer);

        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_smooth_iron_block)
            .addIngredient(LCCBlocks.classic_iron_block)
            .addCriterion(has(LCCBlocks.classic_iron_block), this.hasItem(LCCBlocks.classic_iron_block))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_iron_block)
            .addIngredient(LCCBlocks.classic_smooth_iron_block)
            .addCriterion(has(LCCBlocks.classic_smooth_iron_block), this.hasItem(LCCBlocks.classic_smooth_iron_block))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_smooth_gold_block)
            .addIngredient(LCCBlocks.classic_gold_block)
            .addCriterion(has(LCCBlocks.classic_gold_block), this.hasItem(LCCBlocks.classic_gold_block))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_gold_block)
            .addIngredient(LCCBlocks.classic_smooth_gold_block)
            .addCriterion(has(LCCBlocks.classic_smooth_gold_block), this.hasItem(LCCBlocks.classic_smooth_gold_block))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_smooth_diamond_block)
            .addIngredient(LCCBlocks.classic_diamond_block)
            .addCriterion(has(LCCBlocks.classic_diamond_block), this.hasItem(LCCBlocks.classic_diamond_block))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.classic_diamond_block)
            .addIngredient(LCCBlocks.classic_smooth_diamond_block)
            .addCriterion(has(LCCBlocks.classic_smooth_diamond_block), this.hasItem(LCCBlocks.classic_smooth_diamond_block))
            .build(consumer);

        //Vivid Wood
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.vivid_planks, 4)
            .addIngredient(LCCTags.VIVID_LOGS.item)
            .addCriterion(has(LCCBlocks.vivid_log), this.hasItem(LCCTags.VIVID_LOGS.item))
            .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_wood, 3)
            .patternLine("ww")
            .patternLine("ww")
            .key('w', LCCBlocks.vivid_log)
            .addCriterion(has(LCCBlocks.vivid_log), this.hasItem(LCCTags.VIVID_LOGS.item))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_stairs, 4)
            .patternLine("w  ")
            .patternLine("ww ")
            .patternLine("www")
            .key('w', LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_slab, 6)
            .patternLine("www")
            .key('w', LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_door, 3)
            .patternLine("ww")
            .patternLine("ww")
            .patternLine("ww")
            .key('w', LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_pressure_plate)
            .patternLine("ww")
            .key('w', LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(LCCBlocks.vivid_button)
            .addIngredient(LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_fence)
            .patternLine("wsw")
            .patternLine("wsw")
            .key('w', LCCBlocks.vivid_planks)
            .key('s', Items.STICK)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_fence_gate)
            .patternLine("sws")
            .patternLine("sws")
            .key('w', LCCBlocks.vivid_planks)
            .key('s', Items.STICK)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(LCCBlocks.vivid_trapdoor)
            .patternLine("www")
            .patternLine("www")
            .key('w', LCCBlocks.vivid_planks)
            .addCriterion(has(LCCBlocks.vivid_planks), this.hasItem(LCCBlocks.vivid_planks))
            .build(consumer);
    }

    //Happy helpers.

    private void storage(IItemProvider storage, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(storage)
            .patternLine("iii")
            .patternLine("iii")
            .patternLine("iii")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ingot, 9)
            .addIngredient(storage)
            .addCriterion(has(storage), this.hasItem(storage))
            .build(consumer, new ResourceLocation(LCC.MODID, name(ingot) + "_from_storage"));
    }

    private void nugget(IItemProvider nugget, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ingot)
            .patternLine("nnn")
            .patternLine("nnn")
            .patternLine("nnn")
            .key('n', nugget)
            .addCriterion(has(nugget), this.hasItem(nugget))
            .build(consumer, new ResourceLocation(LCC.MODID, name(ingot) + "_from_nuggets"));
        ShapelessRecipeBuilder.shapelessRecipe(nugget, 9)
            .addIngredient(ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void sword(IItemProvider sword, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(sword)
            .patternLine("i")
            .patternLine("i")
            .patternLine("s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void sword(IItemProvider sword, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(sword)
            .patternLine("i")
            .patternLine("i")
            .patternLine("s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void pickaxe(IItemProvider pickaxe, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(pickaxe)
            .patternLine("iii")
            .patternLine(" s ")
            .patternLine(" s ")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void pickaxe(IItemProvider pickaxe, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(pickaxe)
            .patternLine("iii")
            .patternLine(" s ")
            .patternLine(" s ")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void shovel(IItemProvider shovel, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(shovel)
            .patternLine("i")
            .patternLine("s")
            .patternLine("s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void shovel(IItemProvider shovel, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(shovel)
            .patternLine("i")
            .patternLine("s")
            .patternLine("s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void axe(IItemProvider axe, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(axe)
            .patternLine("ii")
            .patternLine("is")
            .patternLine(" s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void axe(IItemProvider axe, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(axe)
            .patternLine("ii")
            .patternLine("is")
            .patternLine(" s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void hoe(IItemProvider hoe, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(hoe)
            .patternLine("ii")
            .patternLine(" s")
            .patternLine(" s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void hoe(IItemProvider hoe, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(hoe)
            .patternLine("ii")
            .patternLine(" s")
            .patternLine(" s")
            .key('i', ingot)
            .key('s', stick)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void helmet(IItemProvider helmet, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(helmet)
            .patternLine("iii")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void helmet(IItemProvider helmet, Tag<Item> ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(helmet)
            .patternLine("iii")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void chestplate(IItemProvider chestplate, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(chestplate)
            .patternLine("i i")
            .patternLine("iii")
            .patternLine("iii")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void chestplate(IItemProvider chestplate, Tag<Item> ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(chestplate)
            .patternLine("i i")
            .patternLine("iii")
            .patternLine("iii")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void leggings(IItemProvider leggings, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(leggings)
            .patternLine("iii")
            .patternLine("i i")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void leggings(IItemProvider leggings, Tag<Item> ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(leggings)
            .patternLine("iii")
            .patternLine("i i")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void boots(IItemProvider boots, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(boots)
            .patternLine("i i")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void boots(IItemProvider boots, Tag<Item> ingot, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(boots)
            .patternLine("i i")
            .patternLine("i i")
            .key('i', ingot)
            .addCriterion(has(ingot), this.hasItem(ingot))
            .build(consumer);
    }

    private void toolset(IItemProvider sword, IItemProvider pickaxe, IItemProvider shovel, IItemProvider axe, IItemProvider hoe, IItemProvider ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        if (sword != null) this.sword(sword, ingot, stick, consumer);
        if (pickaxe != null) this.pickaxe(pickaxe, ingot, stick, consumer);
        if (shovel != null) this.shovel(shovel, ingot, stick, consumer);
        if (axe != null) this.axe(axe, ingot, stick, consumer);
        if (hoe != null) this.hoe(hoe, ingot, stick, consumer);
    }

    private void toolset(IItemProvider sword, IItemProvider pickaxe, IItemProvider shovel, IItemProvider axe, IItemProvider hoe, Tag<Item> ingot, IItemProvider stick, Consumer<IFinishedRecipe> consumer) {
        if (sword != null) this.sword(sword, ingot, stick, consumer);
        if (pickaxe != null) this.pickaxe(pickaxe, ingot, stick, consumer);
        if (shovel != null) this.shovel(shovel, ingot, stick, consumer);
        if (axe != null) this.axe(axe, ingot, stick, consumer);
        if (hoe != null) this.hoe(hoe, ingot, stick, consumer);
    }

    private void armorset(IItemProvider helmet, IItemProvider chestplate, IItemProvider leggings, IItemProvider boots, IItemProvider ingot, Consumer<IFinishedRecipe> consumer) {
        if (helmet != null) this.helmet(helmet, ingot, consumer);
        if (chestplate != null) this.chestplate(chestplate, ingot, consumer);
        if (leggings != null) this.leggings(leggings, ingot, consumer);
        if (boots != null) this.boots(boots, ingot, consumer);
    }

    private void armorset(IItemProvider helmet, IItemProvider chestplate, IItemProvider leggings, IItemProvider boots, Tag<Item> ingot, Consumer<IFinishedRecipe> consumer) {
        if (helmet != null) this.helmet(helmet, ingot, consumer);
        if (chestplate != null) this.chestplate(chestplate, ingot, consumer);
        if (leggings != null) this.leggings(leggings, ingot, consumer);
        if (boots != null) this.boots(boots, ingot, consumer);
    }

    private void candyCane(IItemProvider cane, IItemProvider coating, IItemProvider refined, IItemProvider refinedCoating, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(coating, 3)
            .patternLine("cc")
            .patternLine("cc")
            .key('c', cane)
            .addCriterion(has(cane), this.hasItem(cane))
            .setGroup("lcc:candy_cane_coating")
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(cane, 3)
            .patternLine("cc")
            .patternLine("cc")
            .key('c', coating)
            .addCriterion(has(coating), this.hasItem(coating))
            .setGroup("lcc:candy_cane_uncoating")
            .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(refined, 2)
            .addIngredient(cane)
            .addCriterion(has(cane), this.hasItem(cane))
            .setGroup("lcc:candy_cane_refine")
            .build(consumer, new ResourceLocation(LCC.MODID, "refine_" + name(cane)));
        ShapelessRecipeBuilder.shapelessRecipe(refinedCoating, 2)
            .addIngredient(coating)
            .addCriterion(has(coating), this.hasItem(coating))
            .setGroup("lcc:candy_cane_coating_refine")
            .build(consumer, new ResourceLocation(LCC.MODID, "refine_" + name(coating)));
        ShapedRecipeBuilder.shapedRecipe(refinedCoating, 3)
            .patternLine("rr")
            .patternLine("rr")
            .key('r', refined)
            .addCriterion(has(refined), this.hasItem(refined))
            .setGroup("lcc:refined_candy_cane_coating")
            .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(refined, 3)
            .patternLine("rr")
            .patternLine("rr")
            .key('r', refinedCoating)
            .addCriterion(has(refinedCoating), this.hasItem(refinedCoating))
            .setGroup("lcc:refined_candy_cane_uncoating")
            .build(consumer);
    }

    private void spreader(IItemProvider spreader, IItemProvider powder, Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(spreader).addIngredient(LCCItems.spreader_essence).addIngredient(powder).setGroup("lcc:spreader").addCriterion(has(LCCItems.spreader_essence), this.hasItem(LCCItems.spreader_essence)).build(consumer);
    }

    private static SingleItemRecipeBuilder timeriftRecipe(Ingredient input, IItemProvider output, int amount) {
        return new SingleItemRecipeBuilder(LCCRecipes.time_rift, input, output, amount);
    }

    private void timerift(Tag<Item> input, IItemProvider output, int count, Consumer<IFinishedRecipe> consumer) {
        timeriftRecipe(Ingredient.fromTag(input), output, count)
            .addCriterion(has(input), this.hasItem(input))
            .build(consumer, new ResourceLocation(LCC.MODID, name(output) + "_from_" + name(input) + "_rift"));
    }

    private void timerift(IItemProvider input, IItemProvider output, int count, Consumer<IFinishedRecipe> consumer) {
        timeriftRecipe(Ingredient.fromItems(input), output, count)
            .addCriterion(has(input), this.hasItem(input))
            .build(consumer, new ResourceLocation(LCC.MODID, name(output) + "_from_" + name(input) + "_rift"));
    }

    private static String has(IItemProvider ip) {
        return "has_" + name(ip);
    }

    private static String has(Tag<Item> t) {
        return "has_" + name(t);
    }

    private static String name(IItemProvider ip) {
        return ip.asItem().getRegistryName().getPath();
    }

    private static String name(Tag<Item> t) {
        return t.getId().getPath().replace('/', '_');
    }

}
