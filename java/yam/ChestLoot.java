package yam;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;

public class ChestLoot {
	
	private static Random rand = new Random();
	
	private static int randomDurability(Item item) {
		return (int)(item.getMaxDamage()*(rand.nextDouble()*0.8));
	}
	
	private static void enchantSword(ItemStack itemstack, int maxlevel, int chance) {
		if (rand.nextInt(chance) == 0) {itemstack.addEnchantment(Enchantment.sharpness, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
		if (rand.nextInt(chance) == 0) {itemstack.addEnchantment(Enchantment.smite, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
		if (rand.nextInt(chance) == 0) {itemstack.addEnchantment(Enchantment.baneOfArthropods, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
		if (rand.nextInt(chance) == 0) {itemstack.addEnchantment(Enchantment.looting, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
		if (rand.nextInt(chance) == 0) {itemstack.addEnchantment(Enchantment.fireAspect, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
		if (rand.nextInt(chance/2) == 0) {itemstack.addEnchantment(Enchantment.unbreaking, rand.nextInt(maxlevel)+1); if (rand.nextInt(2) == 0) {return;}}
	}
	
	public static void diamonds(TileEntityChest chest) {
		chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond, rand.nextInt(3)+1));
		for (int i = 0; i < 2; i++) {
			if (rand.nextInt(3) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond, rand.nextInt(3)+1));}
		}
		if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Blocks.diamond_block, 1));}
	}

	public static void halfPlayerTier1(TileEntityChest chest) {
		chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.wooden_sword, 1, randomDurability(Items.wooden_sword)));
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.wooden_sword, 1, randomDurability(Items.wooden_sword)));}
		}
		for (int i = 0; i < 2; i++) {
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.leather_helmet, 1, randomDurability(Items.leather_helmet)));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.leather_chestplate, 1, randomDurability(Items.leather_chestplate)));}
			if (rand.nextInt(9) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.leather_leggings, 1, randomDurability(Items.leather_leggings)));}
			if (rand.nextInt(7) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.leather_boots, 1, randomDurability(Items.leather_boots)));}
		}
	}
	
	public static void halfPlayerTier1(TileEntityFurnace furnace) {
		int slot = rand.nextInt(3);
		switch(slot) {
			default: {
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.cobblestone, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.iron_ore, rand.nextInt(4)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.porkchop, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.beef, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.chicken, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.potato, rand.nextInt(8)+1)); return;}
			}
			case 1: {
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Items.coal, rand.nextInt(4)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.log, rand.nextInt(4)+1, rand.nextInt(4))); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.log2, rand.nextInt(4)+1, rand.nextInt(2))); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.sapling, rand.nextInt(4)+1, rand.nextInt(6))); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.planks, rand.nextInt(4)+1, rand.nextInt(6))); return;}
			}
			case 2: {
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Blocks.stone, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.iron_ingot, rand.nextInt(4)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.coal, rand.nextInt(8)+1, 1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, rand.nextInt(3)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_beef, rand.nextInt(3)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_chicken, rand.nextInt(3)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.baked_potato, rand.nextInt(3)+1)); return;}
			}
		}
	}

	public static void halfPlayerTier2(TileEntityChest chest) {
		chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.stone_sword, 1, randomDurability(Items.stone_sword)));
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.stone_sword, 1, randomDurability(Items.stone_sword)));}
		}
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.chainmail_helmet, 1, randomDurability(Items.chainmail_helmet)));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.chainmail_chestplate, 1, randomDurability(Items.chainmail_chestplate)));}
			if (rand.nextInt(9) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.chainmail_leggings, 1, randomDurability(Items.chainmail_leggings)));}
			if (rand.nextInt(7) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.chainmail_boots, 1, randomDurability(Items.chainmail_boots)));}
		}
	}
	
	public static void halfPlayerTier2(TileEntityFurnace furnace) {
		int slot = rand.nextInt(3);
		switch(slot) {
			default: {
				if (rand.nextInt(8) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.iron_ore, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.gold_ore, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.cobblestone, rand.nextInt(48)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.porkchop, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.beef, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.chicken, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.potato, rand.nextInt(16)+1)); return;}
			}
			case 1: {
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Items.coal, rand.nextInt(4)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.log, rand.nextInt(6)+1, rand.nextInt(4))); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.log2, rand.nextInt(6)+1, rand.nextInt(2))); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.sapling, rand.nextInt(16)+1, rand.nextInt(6))); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Blocks.planks, rand.nextInt(24)+1, rand.nextInt(6))); return;}
			}
			case 2: {
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Blocks.stone, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.iron_ingot, rand.nextInt(4)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, rand.nextInt(7)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_beef, rand.nextInt(7)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_chicken, rand.nextInt(7)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.baked_potato, rand.nextInt(7)+1)); return;}
			}
		}
	}

	public static void halfPlayerTier3(TileEntityChest chest) {
		chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_sword, 1, randomDurability(Items.iron_sword)));
		for (int i = 0; i < 6; i++) {
			if (rand.nextInt(3) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_sword, 1, randomDurability(Items.iron_sword)));}
		}
		for (int i = 0; i < 2; i++) {
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_ingot, rand.nextInt(8)+1));}
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Blocks.planks, rand.nextInt(8)+1, rand.nextInt(6)));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.paper, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bread, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.sugar, rand.nextInt(6)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.book, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.boat, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bone, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bowl, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.brick, rand.nextInt(64)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bucket, rand.nextInt(3)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.egg, rand.nextInt(6)+1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_cat, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_blocks, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.emerald, 1));}
		}
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(5) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_helmet, 1, randomDurability(Items.iron_helmet)));}
			if (rand.nextInt(9) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_chestplate, 1, randomDurability(Items.iron_chestplate)));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_leggings, 1, randomDurability(Items.iron_leggings)));}
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_boots, 1, randomDurability(Items.iron_boots)));}
		}
	}
	
	public static void halfPlayerTier3(TileEntityFurnace furnace) {
		int slot = rand.nextInt(3);
		switch(slot) {
			default: {
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.iron_ore, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(12) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.diamond_ore, 1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.cobblestone, rand.nextInt(64)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.porkchop, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.beef, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.chicken, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.potato, rand.nextInt(8)+1)); return;}
			}
			case 1: {
				furnace.setInventorySlotContents(1, new ItemStack(Items.coal, rand.nextInt(8)+1)); return;
			}
			case 2: {
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.iron_ingot, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.gold_ingot, rand.nextInt(8)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_beef, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_chicken, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.baked_potato, rand.nextInt(16)+1)); return;}
			}
		}
	}
	
	public static void halfPlayerTier4(TileEntityChest chest) {
		ItemStack enchantedSword = new ItemStack(Items.diamond_sword, 1, randomDurability(Items.diamond_sword)); enchantSword(enchantedSword, 1, 20);
		chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), enchantedSword);
		
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(3) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_sword, 1, randomDurability(Items.diamond_sword)));}
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_pickaxe, 1, randomDurability(Items.diamond_pickaxe)));}
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_shovel, 1, randomDurability(Items.diamond_shovel)));}
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_axe, 1, randomDurability(Items.diamond_axe)));}
			if (rand.nextInt(2) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_hoe, 1, randomDurability(Items.diamond_hoe)));}
		}
		for (int i = 0; i < 5; i++) {
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.iron_ingot, rand.nextInt(8)+1));}
			if (rand.nextInt(4) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Blocks.planks, rand.nextInt(8)+1, rand.nextInt(6)));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.paper, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bread, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.cooked_porkchop, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.cooked_beef, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.cooked_chicken, rand.nextInt(8)+1));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.sugar, rand.nextInt(6)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.book, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.boat, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bone, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bowl, rand.nextInt(8)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.brick, rand.nextInt(64)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.bucket, rand.nextInt(3)+1));}
			if (rand.nextInt(10) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.egg, rand.nextInt(6)+1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_chirp, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_mall, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_stal, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_cat, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.record_blocks, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.emerald, 1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond, rand.nextInt(6)+1));}
			if (rand.nextInt(20) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(YetAnotherMod.crystalShard, rand.nextInt(64)+1));}
		}
		for (int i = 0; i < 3; i++) {
			if (rand.nextInt(5) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_helmet, 1, randomDurability(Items.diamond_helmet)));}
			if (rand.nextInt(9) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_chestplate, 1, randomDurability(Items.diamond_chestplate)));}
			if (rand.nextInt(8) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_leggings, 1, randomDurability(Items.diamond_leggings)));}
			if (rand.nextInt(6) == 0) {chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), new ItemStack(Items.diamond_boots, 1, randomDurability(Items.diamond_boots)));}
		}
	}

	public static void halfPlayerTier4(TileEntityFurnace furnace) {
		int slot = rand.nextInt(3);
		switch(slot) {
			default: {
				if (rand.nextInt(2) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.cobblestone, rand.nextInt(64)+1)); return;}
				if (rand.nextInt(3) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.iron_ore, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Blocks.diamond_ore, rand.nextInt(5)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.porkchop, rand.nextInt(20)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.beef, rand.nextInt(20)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.chicken, rand.nextInt(20)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(0, new ItemStack(Items.potato, rand.nextInt(20)+1)); return;}
			}
			case 1: {
				if (rand.nextInt(2) == 0) {furnace.setInventorySlotContents(1, new ItemStack(Items.coal, rand.nextInt(16)+1)); return;}
				furnace.setInventorySlotContents(1, new ItemStack(Items.lava_bucket, 1)); return;
			}
			case 2: {
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.iron_ingot, rand.nextInt(24)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.gold_ingot, rand.nextInt(12)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.diamond, rand.nextInt(6)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_porkchop, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_beef, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.cooked_chicken, rand.nextInt(16)+1)); return;}
				if (rand.nextInt(6) == 0) {furnace.setInventorySlotContents(2, new ItemStack(Items.baked_potato, rand.nextInt(16)+1)); return;}
			}
		}
	}
	
}
