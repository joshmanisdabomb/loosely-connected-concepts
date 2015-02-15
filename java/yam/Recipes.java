package yam;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void loadRecipes() {
		//Mud and Quicksand Recipes
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.mud), Items.water_bucket, Item.getItemFromBlock(Blocks.dirt));
		GameRegistry.addSmelting(YetAnotherMod.mud, new ItemStack(YetAnotherMod.crackedMud, 1), 0.1F);
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.mud), Items.water_bucket, Item.getItemFromBlock(YetAnotherMod.crackedMud));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.quicksand), Items.water_bucket, Item.getItemFromBlock(Blocks.sand));
		
		//Pressure Plate Recipes
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"11X", "XXX", "XXX", '1', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"X11", "XXX", "XXX", '1', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"XXX", "11X", "XXX", '1', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"XXX", "X11", "XXX", '1', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"XXX", "XXX", "11X", '1', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.diamondPressurePlate, 1), new Object[] {"XXX", "XXX", "X11", '1', Items.diamond});
		
		//Tile Entity Recipes
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.trashCan, 1), new Object[] {"111", "1X1", "111", '1', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.tickField, 1), new Object[] {"121", "232", "121", '1', Blocks.diamond_block, '2', Items.ender_eye, '3', Items.clock});
	
		//Nuke and Atom Splitter Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.atomSplitter), new Object[] {Items.diamond, YetAnotherMod.uranium});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.nuke), new Object[] {Blocks.tnt, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium, YetAnotherMod.uranium});
		
		//Bouncepad Recipes
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceBlue, 16), new Object[] {"111", "222", "343", '1', new ItemStack(Blocks.wool,1,9), '2', Blocks.wooden_pressure_plate, '3', Items.repeater, '4', Blocks.piston});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceGreen, 16), new Object[] {"111", "222", "343", '1', new ItemStack(Blocks.wool,1,5), '2', Blocks.stone_pressure_plate, '3', Items.repeater, '4', Blocks.piston});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceYellow, 16), new Object[] {"111", "222", "343", '1', new ItemStack(Blocks.wool,1,4), '2', Blocks.heavy_weighted_pressure_plate, '3', Items.repeater, '4', Blocks.piston});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceOrange, 16), new Object[] {"111", "222", "343", '1', new ItemStack(Blocks.wool,1,1), '2', Blocks.light_weighted_pressure_plate, '3', Items.repeater, '4', Blocks.piston});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceRed, 16), new Object[] {"111", "222", "343", '1', new ItemStack(Blocks.wool,1,14), '2', YetAnotherMod.diamondPressurePlate, '3', Items.repeater, '4', Blocks.piston});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.bounceCushion, 4), new Object[] {"111", "222", "222", '1', new ItemStack(Blocks.wool,1,11), '2', Blocks.wool});
		
		//Spreader Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.antispreader, 8), new Object[] {Blocks.cobblestone, Blocks.glowstone, Items.ender_pearl});
		newSpreaderRecipe(YetAnotherMod.spreader1, new ItemStack(Items.dye, 1, 13));
		newSpreaderRecipe(YetAnotherMod.spreader2, new ItemStack(Items.dye, 1, 10), new ItemStack(Blocks.stone_slab, 1, 3));
		newSpreaderRecipe(YetAnotherMod.spreader3, new ItemStack(Items.dye, 1, 14), new ItemStack(Blocks.cobblestone, 1, 0));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eater1), Items.ender_eye, Item.getItemFromBlock(YetAnotherMod.spreader1));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eater2), Items.ender_eye, Item.getItemFromBlock(YetAnotherMod.spreader2));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eater3), Items.ender_eye, Item.getItemFromBlock(YetAnotherMod.spreader3));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.spreaderc1), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader1));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.spreaderc2), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader2));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.spreaderc3), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader3));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc1), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.eater1));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc2), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.eater2));
		newOneToEightRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc3), YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.eater3));
		newOneToSevenRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc1), Items.ender_eye, YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader1));
		newOneToSevenRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc2), Items.ender_eye, YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader2));
		newOneToSevenRecipe(Item.getItemFromBlock(YetAnotherMod.eaterc3), Items.ender_eye, YetAnotherMod.crystal, Item.getItemFromBlock(YetAnotherMod.spreader3));
		
		//Lapis Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.lapisGel, 2), new Object[] {Items.slime_ball, new ItemStack(Items.dye, 1, 4), Items.diamond});
		GameRegistry.addSmelting(new ItemStack(YetAnotherMod.lapisGel), new ItemStack(YetAnotherMod.lapisIngot), 0.7F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.crystalEnergy, 1), new Object[] {Items.nether_star, YetAnotherMod.crystal});
		newBlasterRecipe(YetAnotherMod.blaster, YetAnotherMod.crystalEnergy, Item.getItemFromBlock(Blocks.obsidian));
		
		//Bricks
		newBrickRecipes(YetAnotherMod.bricksCoal, new ItemStack(Items.coal, 1));
		newBrickRecipes(YetAnotherMod.bricksIron, new ItemStack(Items.iron_ingot, 1));
		newBrickRecipes(YetAnotherMod.bricksGold, new ItemStack(Items.gold_ingot, 1));
		newBrickRecipes(YetAnotherMod.bricksDiamond, new ItemStack(Items.diamond, 1));
		newBrickRecipes(YetAnotherMod.bricksObsidian, new ItemStack(Blocks.obsidian, 1));
		newBrickRecipes(YetAnotherMod.bricksRedstone, new ItemStack(Items.redstone, 1));
		newBrickRecipes(YetAnotherMod.bricksRuby, new ItemStack(YetAnotherMod.ruby, 1));
		newBrickRecipes(YetAnotherMod.bricksEmerald, new ItemStack(Items.emerald, 1));
		newBrickRecipes(YetAnotherMod.bricksLapis, new ItemStack(Items.dye, 1, 4));
		newBrickRecipes(YetAnotherMod.bricksCrystal, new ItemStack(YetAnotherMod.crystal, 1));
		newBrickRecipes(YetAnotherMod.bricksPearl, new ItemStack(YetAnotherMod.pearl, 1));
		newBrickRecipes(YetAnotherMod.bricksUranium, new ItemStack(YetAnotherMod.uranium, 1));
		
		//Storage Block & Item Recipes
		newStorageRecipes(YetAnotherMod.crystalShard, YetAnotherMod.crystal);
		newStorageRecipes(YetAnotherMod.crystal, YetAnotherMod.crystalIngot);
		newStorageRecipes(YetAnotherMod.crystalIngot, Item.getItemFromBlock(YetAnotherMod.crystalBlock));
		newStorageRecipes(YetAnotherMod.ruby, Item.getItemFromBlock(YetAnotherMod.rubyBlock));
		newStorageRecipes(YetAnotherMod.pearl, Item.getItemFromBlock(YetAnotherMod.pearlBlock));
		newStorageRecipes(YetAnotherMod.uranium, Item.getItemFromBlock(YetAnotherMod.uraniumBlock));
		newStorageRecipes(YetAnotherMod.cheese, Item.getItemFromBlock(YetAnotherMod.cheeseBlock));
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.rainbowBlock, 64), new Object[] {YetAnotherMod.rubyBlock, YetAnotherMod.crystalBlock, YetAnotherMod.pearlBlock, Blocks.redstone_block, Blocks.gold_block, Blocks.emerald_block, Blocks.lapis_block, Blocks.diamond_block, Blocks.iron_block});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.rustBlock, 1), new Object[] {"111", "121", "111", '1', YetAnotherMod.rust, '2', Blocks.iron_block});
	
		//Tool Recipes
		newToolRecipes(YetAnotherMod.ruby, Items.stick, YetAnotherMod.rubySword, YetAnotherMod.rubyPickaxe, YetAnotherMod.rubyShovel, YetAnotherMod.rubyAxe, YetAnotherMod.rubyHoe);
		newToolRecipes(Items.emerald, Items.stick, YetAnotherMod.emeraldSword, YetAnotherMod.emeraldPickaxe, YetAnotherMod.emeraldShovel, YetAnotherMod.emeraldAxe, YetAnotherMod.emeraldHoe);
		newToolRecipes(YetAnotherMod.lapisIngot, Items.stick, YetAnotherMod.lapisSword, YetAnotherMod.lapisPickaxe, YetAnotherMod.lapisShovel, YetAnotherMod.lapisAxe, YetAnotherMod.lapisHoe);
		newToolRecipes(YetAnotherMod.crystalIngot, YetAnotherMod.crystal, YetAnotherMod.crystalSword, YetAnotherMod.crystalPickaxe, YetAnotherMod.crystalShovel, YetAnotherMod.crystalAxe, YetAnotherMod.crystalHoe);
		newRepeaterRecipes(YetAnotherMod.crystalIngot, Items.string, Items.repeater, YetAnotherMod.crystalRepeater);
		newToolRecipes(Item.getItemFromBlock(YetAnotherMod.cloud), YetAnotherMod.lollipopStick, YetAnotherMod.cloudSword, YetAnotherMod.cloudPickaxe, YetAnotherMod.cloudShovel, YetAnotherMod.cloudAxe, YetAnotherMod.cloudHoe);
		
		//Armor Recipes
		newArmorRecipes(YetAnotherMod.ruby, YetAnotherMod.rubyHelmet, YetAnotherMod.rubyChestplate, YetAnotherMod.rubyLeggings, YetAnotherMod.rubyBoots);
		newArmorRecipes(Items.emerald, YetAnotherMod.emeraldHelmet, YetAnotherMod.emeraldChestplate, YetAnotherMod.emeraldLeggings, YetAnotherMod.emeraldBoots);
		newArmorRecipes(YetAnotherMod.lapisIngot, YetAnotherMod.lapisHelmet, YetAnotherMod.lapisChestplate, YetAnotherMod.lapisLeggings, YetAnotherMod.lapisBoots);
		newArmorRecipes(YetAnotherMod.crystalIngot, YetAnotherMod.crystalHelmet, YetAnotherMod.crystalChestplate, YetAnotherMod.crystalLeggings, YetAnotherMod.crystalBoots);
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pearlNecklace, 1), new Object[]{"111", "222", "XXX", '1', YetAnotherMod.pearl, '2', Items.string});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pearlNecklace, 1), new Object[]{"222", "111", "XXX", '1', YetAnotherMod.pearl, '2', Items.string});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pearlNecklace, 1), new Object[]{"XXX", "222", "111", '1', YetAnotherMod.pearl, '2', Items.string});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pearlNecklace, 1), new Object[]{"XXX", "111", "222", '1', YetAnotherMod.pearl, '2', Items.string});
		newArmorCosmeticRecipes(YetAnotherMod.pearlNecklace, YetAnotherMod.pearlNecklaceCosmetic);
		newArmorRecipes(Items.leather, Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, YetAnotherMod.hazmatHelmet, YetAnotherMod.hazmatChestplate, YetAnotherMod.hazmatLeggings, YetAnotherMod.hazmatBoots);
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.hazmatHelmet, 1), new Object[] {Items.leather_helmet, Items.iron_helmet});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.hazmatChestplate, 1), new Object[] {Items.leather_chestplate, Items.iron_chestplate});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.hazmatLeggings, 1), new Object[] {Items.leather_leggings, Items.iron_leggings});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.hazmatBoots, 1), new Object[] {Items.leather_boots, Items.iron_boots});
		newArmorRecipes(Item.getItemFromBlock(YetAnotherMod.cloud), YetAnotherMod.cloudHelmet, YetAnotherMod.cloudChestplate, YetAnotherMod.cloudLeggings, YetAnotherMod.cloudBoots);
				
		//Remote Recipes
		newRemoteRecipes(Item.getItemFromBlock(Blocks.planks), YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteWood);
		newRemoteRecipes(Item.getItemFromBlock(Blocks.cobblestone), YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteStone);
		newRemoteRecipes(Items.iron_ingot, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteIron);
		newRemoteRecipes(Items.gold_ingot, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteGold);
		newRemoteRecipes(Items.diamond, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteDiamond);
		newRemoteRecipes(YetAnotherMod.ruby, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteRuby);
		newRemoteRecipes(Items.emerald, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteEmerald);
		newRemoteRecipes(YetAnotherMod.lapisIngot, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteLapis);
		newRemoteRecipes(YetAnotherMod.crystalIngot, YetAnotherMod.peripheralSpreader, YetAnotherMod.spreaderRemoteCrystal);
		newRemoteRecipes(Item.getItemFromBlock(Blocks.planks), YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteWood);
		newRemoteRecipes(Item.getItemFromBlock(Blocks.cobblestone), YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteStone);
		newRemoteRecipes(Items.iron_ingot, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteIron);
		newRemoteRecipes(Items.gold_ingot, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteGold);
		newRemoteRecipes(Items.diamond, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteDiamond);
		newRemoteRecipes(YetAnotherMod.ruby, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteRuby);
		newRemoteRecipes(Items.emerald, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteEmerald);
		newRemoteRecipes(YetAnotherMod.lapisIngot, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteLapis);
		newRemoteRecipes(YetAnotherMod.crystalIngot, YetAnotherMod.peripheralAntispreader, YetAnotherMod.antispreaderRemoteCrystal);

		newRemoteRecipes(Items.iron_ingot, YetAnotherMod.peripheralNuclear, YetAnotherMod.nukeRemote);
		
		//Glasses
		newGlassesRecipe(YetAnotherMod.glasses, Blocks.glass);
		newGlassesRecipe(YetAnotherMod.glassesWhite, new ItemStack(Blocks.stained_glass, 1, 0), new ItemStack(Items.dye, 1, 15));
		newGlassesRecipe(YetAnotherMod.glassesOrange, new ItemStack(Blocks.stained_glass, 1, 1), new ItemStack(Items.dye, 1, 14));
		newGlassesRecipe(YetAnotherMod.glassesMagenta, new ItemStack(Blocks.stained_glass, 1, 2), new ItemStack(Items.dye, 1, 13));
		newGlassesRecipe(YetAnotherMod.glassesLBlue, new ItemStack(Blocks.stained_glass, 1, 3), new ItemStack(Items.dye, 1, 12));
		newGlassesRecipe(YetAnotherMod.glassesYellow, new ItemStack(Blocks.stained_glass, 1, 4), new ItemStack(Items.dye, 1, 11));
		newGlassesRecipe(YetAnotherMod.glassesLime, new ItemStack(Blocks.stained_glass, 1, 5), new ItemStack(Items.dye, 1, 10));
		newGlassesRecipe(YetAnotherMod.glassesPink, new ItemStack(Blocks.stained_glass, 1, 6), new ItemStack(Items.dye, 1, 9));
		newGlassesRecipe(YetAnotherMod.glassesGray, new ItemStack(Blocks.stained_glass, 1, 7), new ItemStack(Items.dye, 1, 8));
		newGlassesRecipe(YetAnotherMod.glassesLGray, new ItemStack(Blocks.stained_glass, 1, 8), new ItemStack(Items.dye, 1, 7));
		newGlassesRecipe(YetAnotherMod.glassesCyan, new ItemStack(Blocks.stained_glass, 1, 9), new ItemStack(Items.dye, 1, 6));
		newGlassesRecipe(YetAnotherMod.glassesPurple, new ItemStack(Blocks.stained_glass, 1, 10), new ItemStack(Items.dye, 1, 5));
		newGlassesRecipe(YetAnotherMod.glassesBlue, new ItemStack(Blocks.stained_glass, 1, 11), new ItemStack(Items.dye, 1, 4));
		newGlassesRecipe(YetAnotherMod.glassesBrown, new ItemStack(Blocks.stained_glass, 1, 12), new ItemStack(Items.dye, 1, 3));
		newGlassesRecipe(YetAnotherMod.glassesGreen, new ItemStack(Blocks.stained_glass, 1, 13), new ItemStack(Items.dye, 1, 2));
		newGlassesRecipe(YetAnotherMod.glassesRed, new ItemStack(Blocks.stained_glass, 1, 14), new ItemStack(Items.dye, 1, 1));
		newGlassesRecipe(YetAnotherMod.glassesBlack, new ItemStack(Blocks.stained_glass, 1, 15), new ItemStack(Items.dye, 1, 0));
		
		//Peripherals
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreader1));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreader2));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreader3));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreaderc1));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreaderc2));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.spreaderc3));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eater1));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eater2));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eater3));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eaterc1));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eaterc2));
		newPeripheralRecipe(YetAnotherMod.peripheralSpreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.eaterc3));
		newPeripheralRecipe(YetAnotherMod.peripheralAntispreader, Blocks.redstone_torch, Item.getItemFromBlock(YetAnotherMod.antispreader));
		newPeripheralRecipe(YetAnotherMod.peripheralNuclear, Blocks.redstone_torch, YetAnotherMod.uranium);
		
		//Foodies
		newOneToNineRecipe(YetAnotherMod.cheese, YetAnotherMod.cheeseBucket);
		GameRegistry.addSmelting(new ItemStack(Items.milk_bucket), new ItemStack(YetAnotherMod.cheeseBucket), 0.15F);
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.flour, 1), new Object[] {Items.wheat});
		newOneToFourRecipe(YetAnotherMod.dough, Items.water_bucket, YetAnotherMod.flour, YetAnotherMod.salt);
		GameRegistry.addSmelting(new ItemStack(YetAnotherMod.dough), new ItemStack(YetAnotherMod.doughCooked), 0.15F);
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pizza, 1), new Object[]{"111", "222", "XXX", '1', YetAnotherMod.cheeseBucket, '2', YetAnotherMod.doughCooked});
		GameRegistry.addRecipe(new ItemStack(YetAnotherMod.pizza, 1), new Object[]{"XXX", "111", "222", '1', YetAnotherMod.cheeseBucket, '2', YetAnotherMod.doughCooked});
	
		//Rainbow Dimension
		newSquareRecipes(YetAnotherMod.rainbowStone, YetAnotherMod.rainbowStoneRefined, 4);
		newRainbowGrassRecipes();
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.candyCanePlanksRed, 4), new Object[] {YetAnotherMod.candyCaneRed});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.candyCanePlanksGreen, 4), new Object[] {YetAnotherMod.candyCaneGreen});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.candyCanePlanksBlue, 4), new Object[] {YetAnotherMod.candyCaneBlue});
		GameRegistry.addShapelessRecipe(new ItemStack(YetAnotherMod.cloudBottle, 1), new Object[] {YetAnotherMod.cloud, Items.glass_bottle});
		
		//Sheol Dimension
		GameRegistry.addSmelting(new ItemStack(Blocks.coal_block), new ItemStack(YetAnotherMod.hotCoal), 0.2F);
	}
	
	private static void newBrickRecipes(Block output, ItemStack modifier) {
		GameRegistry.addRecipe(new ItemStack(output, 8), new Object[] {"11X", "11X", "22X", '1', Blocks.stonebrick, '2', modifier});
		GameRegistry.addRecipe(new ItemStack(output, 8), new Object[] {"X11", "X11", "X22", '1', Blocks.stonebrick, '2', modifier});
		GameRegistry.addRecipe(new ItemStack(output, 8), new Object[] {"22X", "11X", "11X", '1', Blocks.stonebrick, '2', modifier});
		GameRegistry.addRecipe(new ItemStack(output, 8), new Object[] {"X22", "X11", "X11", '1', Blocks.stonebrick, '2', modifier});
	}
	
	private static void newSquareRecipes(Block input, Block output, int amount) {
		GameRegistry.addRecipe(new ItemStack(output, amount), new Object[] {"11X", "11X", "XXX", '1', input});
		GameRegistry.addRecipe(new ItemStack(output, amount), new Object[] {"X11", "X11", "XXX", '1', input});
		GameRegistry.addRecipe(new ItemStack(output, amount), new Object[] {"XXX", "11X", "11X", '1', input});
		GameRegistry.addRecipe(new ItemStack(output, amount), new Object[] {"XXX", "X11", "X11", '1', input});
	}

	private static void newOneToNineRecipe(Item output, Item one) {
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {one, one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {one, one, one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {one, one, one, one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {one, one, one, one, one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 8), new Object[] {one, one, one, one, one, one, one, one});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 9), new Object[] {one, one, one, one, one, one, one, one, one});
	}
	
	private static void newOneToEightRecipe(Item output, Item one, Item eight) {
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {one, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {one, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {one, eight, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {one, eight, eight, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {one, eight, eight, eight, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {one, eight, eight, eight, eight, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {one, eight, eight, eight, eight, eight, eight, eight});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 8), new Object[] {one, eight, eight, eight, eight, eight, eight, eight, eight});
	}
	
	private static void newOneToSevenRecipe(Item output, Item one, Item two, Item seven) {
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {one, two, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {one, two, seven, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {one, two, seven, seven, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {one, two, seven, seven, seven, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {one, two, seven, seven, seven, seven, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {one, two, seven, seven, seven, seven, seven, seven});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {one, two, seven, seven, seven, seven, seven, seven, seven});
	}
	
	private static void newOneToFourRecipe(Item output, Item one, Item four, Item four2) {
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {one, four, four2});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {one, four, four2, four, four2});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {one, four, four2, four, four2, four, four2});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {one, four, four2, four, four2, four, four2, four, four2});
	}


	private static void newSpreaderRecipe(Block spreader, ItemStack itemStack) {
		GameRegistry.addRecipe(new ItemStack(spreader, 1), new Object[]{"121", "202", "121", '1', YetAnotherMod.antispreader, '2', itemStack});
	}
	
	private static void newSpreaderRecipe(Block spreader, ItemStack itemStack, ItemStack itemStack2) {
		GameRegistry.addRecipe(new ItemStack(spreader, 1), new Object[]{"121", "232", "121", '1', YetAnotherMod.antispreader, '2', itemStack, '3', itemStack2});
	}

	private static void newStorageRecipes(Item material, Item storage) {
		GameRegistry.addRecipe(new ItemStack(storage, 1), new Object[] {"111", "111", "111", '1', material});
		GameRegistry.addShapelessRecipe(new ItemStack(material, 9), new Object[] {storage});
	}
	
	private static void newRemoteRecipes(Item material, Item peripheral, Item remote) {
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"1XX", "222", "322", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"1XX", "222", "232", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"1XX", "222", "223", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"X1X", "222", "322", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"X1X", "222", "232", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"X1X", "222", "223", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"XX1", "222", "322", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"XX1", "222", "232", '1', peripheral, '2', material, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(remote, 1), new Object[] {"XX1", "222", "223", '1', peripheral, '2', material, '3', Blocks.stone_button});
	}
	
	private static void newToolRecipes(Item material, Item stick, Item sword, Item pickaxe, Item shovel, Item axe, Item hoe) {
		GameRegistry.addRecipe(new ItemStack(sword, 1), new Object[] {"1XX", "1XX", "2XX", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(sword, 1), new Object[] {"X1X", "X1X", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(sword, 1), new Object[] {"XX1", "XX1", "XX2", '1', material, '2', stick});

		GameRegistry.addRecipe(new ItemStack(pickaxe, 1), new Object[] {"111", "X2X", "X2X", '1', material, '2', stick});
		
		GameRegistry.addRecipe(new ItemStack(shovel, 1), new Object[] {"1XX", "2XX", "2XX", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(shovel, 1), new Object[] {"X1X", "X2X", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(shovel, 1), new Object[] {"XX1", "XX2", "XX2", '1', material, '2', stick});

		GameRegistry.addRecipe(new ItemStack(axe, 1), new Object[] {"11X", "12X", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(axe, 1), new Object[] {"11X", "21X", "2XX", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(axe, 1), new Object[] {"X11", "X21", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(axe, 1), new Object[] {"X11", "X12", "XX2", '1', material, '2', stick});
		
		GameRegistry.addRecipe(new ItemStack(hoe, 1), new Object[] {"11X", "X2X", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(hoe, 1), new Object[] {"11X", "2XX", "2XX", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(hoe, 1), new Object[] {"X11", "X2X", "X2X", '1', material, '2', stick});
		GameRegistry.addRecipe(new ItemStack(hoe, 1), new Object[] {"X11", "X12", "XX2", '1', material, '2', stick});
	}
	
	private static void newRepeaterRecipes(Item material, Item string, Item repeater, Item bow) {
		GameRegistry.addRecipe(new ItemStack(bow, 1), new Object[] {"21X", "231", "21X", '1', material, '2', string, '3', repeater});
		GameRegistry.addRecipe(new ItemStack(bow, 1), new Object[] {"X12", "132", "X12", '1', material, '2', string, '3', repeater});
	}
	
	private static void newArmorRecipes(Item material, Item helmet, Item chestplate, Item leggings, Item boots) {
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"111", "1X1", "XXX", '1', material});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"XXX", "111", "1X1", '1', material});

		GameRegistry.addRecipe(new ItemStack(chestplate, 1), new Object[] {"1X1", "111", "111", '1', material});

		GameRegistry.addRecipe(new ItemStack(leggings, 1), new Object[] {"111", "1X1", "1X1", '1', material});

		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"1X1", "1X1", "XXX", '1', material});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"XXX", "1X1", "1X1", '1', material});
	}
	
	private static void newArmorRecipes(Item material, Item other, Item other2, Item other3, Item other4, Item helmet, Item chestplate, Item leggings, Item boots) {
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"111", "121", "XXX", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"XXX", "111", "121", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"111", "1X1", "2XX", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"2XX", "111", "1X1", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"111", "1X1", "X2X", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"X2X", "111", "1X1", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"111", "1X1", "XX2", '1', material, '2', other});
		GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[] {"XX2", "111", "1X1", '1', material, '2', other});

		GameRegistry.addRecipe(new ItemStack(chestplate, 1), new Object[] {"121", "111", "111", '1', material, '2', other2});

		GameRegistry.addRecipe(new ItemStack(leggings, 1), new Object[] {"111", "121", "1X1", '1', material, '2', other3});
		GameRegistry.addRecipe(new ItemStack(leggings, 1), new Object[] {"111", "1X1", "121", '1', material, '2', other3});

		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"121", "1X1", "XXX", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"XXX", "121", "1X1", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"1X1", "121", "XXX", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"XXX", "1X1", "121", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"1X1", "1X1", "2XX", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"2XX", "1X1", "1X1", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"1X1", "1X1", "X2X", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"X2X", "1X1", "1X1", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"1X1", "1X1", "XX2", '1', material, '2', other4});
		GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[] {"XX2", "1X1", "1X1", '1', material, '2', other4});
	}
	
	private static void newArmorCosmeticRecipes(Item armor, Item cosmetic) {
		GameRegistry.addShapelessRecipe(new ItemStack(cosmetic, 1), new Object[] {armor});
		GameRegistry.addShapelessRecipe(new ItemStack(armor, 1), new Object[] {cosmetic});
	}
	
	private static void newBlasterRecipe(Item gun, Item powerSource, Item material) {
		GameRegistry.addRecipe(new ItemStack(gun, 1), new Object[] {"121", "13X", "XXX", '1', material, '2', powerSource, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(gun, 1), new Object[] {"121", "X31", "XXX", '1', material, '2', powerSource, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(gun, 1), new Object[] {"XXX", "121", "13X", '1', material, '2', powerSource, '3', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(gun, 1), new Object[] {"XXX", "121", "X31", '1', material, '2', powerSource, '3', Blocks.stone_button});
	}
	
	private static void newGlassesRecipe(Item glasses, Block glass) {
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "22X", "XXX", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "X22", "XXX", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "22X", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "X22", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glasses, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesWhite, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesLGray, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesGray, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesBlack, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesRed, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesOrange, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesYellow, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesGreen, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesLime, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesLBlue, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesCyan, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesBlue, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesPurple, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesMagenta, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesPink, Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glassesBrown, Items.water_bucket});
	}
	
	private static void newGlassesRecipe(Item glasses, ItemStack glass, ItemStack dye) {
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "22X", "XXX", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "X22", "XXX", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "22X", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "X22", '1', Blocks.obsidian, '2', glass});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "223", "XXX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "322", "XXX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "223", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "322", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "22X", "3XX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "X22", "3XX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"3XX", "111", "22X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"3XX", "111", "X22", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "22X", "X3X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "X22", "X3X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"X3X", "111", "22X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"X3X", "111", "X22", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "22X", "XX3", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "X22", "XX3", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XX3", "111", "22X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XX3", "111", "X22", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glasses, dye});
	}
	
	private static void newGlassesRecipe(Item glasses, Item glasses1, ItemStack glass, ItemStack dye, Item peripheral) {
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "224", "XXX", '1', Blocks.obsidian, '2', glass, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "422", "XXX", '1', Blocks.obsidian, '2', glass, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "224", '1', Blocks.obsidian, '2', glass, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XXX", "111", "422", '1', Blocks.obsidian, '2', glass, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "224", "3XX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "422", "3XX", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"3XX", "111", "224", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"3XX", "111", "422", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "224", "X3X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "422", "X3X", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"X3X", "111", "224", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"X3X", "111", "422", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "224", "XX3", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"111", "422", "XX3", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XX3", "111", "224", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addRecipe(new ItemStack(glasses, 1), new Object[] {"XX3", "111", "422", '1', Blocks.obsidian, '2', Blocks.glass, '3', dye, '4', peripheral});
		GameRegistry.addShapelessRecipe(new ItemStack(glasses, 1), new Object[] {YetAnotherMod.glasses, dye, peripheral});
	}
	
	private static void newPeripheralRecipe(Item peripheral, Block torch, Item addon) {
		GameRegistry.addRecipe(new ItemStack(peripheral, 1), new Object[] {"1XX", "2XX", "3XX", '1', addon, '2', Blocks.obsidian, '3', torch});
		GameRegistry.addRecipe(new ItemStack(peripheral, 1), new Object[] {"X1X", "X2X", "X3X", '1', addon, '2', Blocks.obsidian, '3', torch});
		GameRegistry.addRecipe(new ItemStack(peripheral, 1), new Object[] {"XX1", "XX2", "XX3", '1', addon, '2', Blocks.obsidian, '3', torch});
	}
	
	private static void newRainbowGrassRecipes() {
		Block output;
		ItemStack dye;

		output = YetAnotherMod.rainbowGrassWhite; dye = new ItemStack(Items.dye, 1, 15);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassOrange; dye = new ItemStack(Items.dye, 1, 14);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassMagenta; dye = new ItemStack(Items.dye, 1, 13);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassLBlue; dye = new ItemStack(Items.dye, 1, 12);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassYellow; dye = new ItemStack(Items.dye, 1, 11);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassLime; dye = new ItemStack(Items.dye, 1, 10);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassPink; dye = new ItemStack(Items.dye, 1, 9);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassGray; dye = new ItemStack(Items.dye, 1, 8);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassLGray; dye = new ItemStack(Items.dye, 1, 7);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassCyan; dye = new ItemStack(Items.dye, 1, 6);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassPurple; dye = new ItemStack(Items.dye, 1, 5);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassBlue; dye = new ItemStack(Items.dye, 1, 4);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassBrown; dye = new ItemStack(Items.dye, 1, 3);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassGreen; dye = new ItemStack(Items.dye, 1, 2);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassRed; dye = new ItemStack(Items.dye, 1, 1);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});

		output = YetAnotherMod.rainbowGrassBlack; dye = new ItemStack(Items.dye, 1, 0);
		GameRegistry.addShapelessRecipe(new ItemStack(output, 1), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 2), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 3), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 4), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 5), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 6), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
		GameRegistry.addShapelessRecipe(new ItemStack(output, 7), new Object[] {dye, YetAnotherMod.rainbowSeeds, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt, YetAnotherMod.rainbowDirt});
	}

}
