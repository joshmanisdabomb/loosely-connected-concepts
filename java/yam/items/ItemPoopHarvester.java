package yam.items;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockPoop;
import yam.entity.extensions.ExtendedPlayer;

public class ItemPoopHarvester extends ItemGeneric {

	public ItemPoopHarvester(String texture) {
		super(texture);
		this.setMaxStackSize(1);
		this.setMaxDamage(5);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par3World.getBlock(par4, par5, par6) == YetAnotherMod.poop) {
			par3World.func_147480_a(par4, par5, par6, false);
			ArrayList<ItemStack> allStacks = this.getRewards(par3World.rand, ExtendedPlayer.get(par2EntityPlayer).getLuck());
			
			if (!par3World.isRemote && par3World.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
				for (ItemStack is : allStacks) {
		            float f = 0.7F;
		            double d0 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		            double d1 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		            double d2 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		            EntityItem entityitem = new EntityItem(par3World, (double)par4 + d0, (double)par5 + d1, (double)par6 + d2, is);
		            entityitem.delayBeforeCanPickup = 10;
		            par3World.spawnEntityInWorld(entityitem);
				}
	        }
		}

        par1ItemStack.damageItem(1, par2EntityPlayer);		
		return false;
	}

	private ArrayList<ItemStack> getRewards(Random rand, int luckStat) {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		if (rand.nextInt(10 - Math.max(luckStat / 3, 4)) == 0) {
			stacks.add(new ItemStack(Items.gold_ingot, rand.nextInt(2 + Math.max(luckStat / 4, 4)) + 1));
		}
		if (rand.nextInt(9 - Math.max(luckStat / 2, 4)) == 0) {
			stacks.add(new ItemStack(Items.iron_ingot, rand.nextInt(2 + Math.max(luckStat / 2, 8)) + 1));
		}
		if (rand.nextInt(8 - Math.max(luckStat / 3, 4)) == 0) {
			stacks.add(new ItemStack(Items.gold_nugget, rand.nextInt(2 + Math.max(luckStat / 3, 8)) + 1));
		}
		if (rand.nextInt(30 - Math.max(luckStat / 2, 9)) == 0) {
			stacks.add(new ItemStack(YetAnotherMod.poopHarvester, 1));
		}
		if (rand.nextInt(30 - Math.max(luckStat / 3, 7)) == 0) {
			stacks.add(new ItemStack(YetAnotherMod.flySwat, 1));
		}
		if (rand.nextInt(100 - Math.max(luckStat / 5, 4)) == 0) {
			stacks.add(new ItemStack(YetAnotherMod.flySwatElectric, 1));
		}
		if (rand.nextInt(9 - Math.max(luckStat / 2, 4)) == 0) {
			stacks.add(new ItemStack(Items.brick, rand.nextInt(2 + Math.max(luckStat / 2, 3)) + 1));
		}
		if (rand.nextInt(30 - Math.max(luckStat / 3, 7)) == 0) {
			ItemStack sword = (rand.nextInt(Math.max(6 - luckStat, 1)) == 0)
			? new ItemStack(YetAnotherMod.crystalSword, 1, Math.max((YetAnotherMod.crystalSword.getMaxDamage() - 5) - rand.nextInt(YetAnotherMod.crystalSword.getMaxDamage() / 3) - (rand.nextInt(Math.max(luckStat * YetAnotherMod.crystalSword.getMaxDamage() / 25, 1))), 15))
			: new ItemStack(YetAnotherMod.cactusSword, 1, Math.max((YetAnotherMod.cactusSword.getMaxDamage() - 5) - rand.nextInt(YetAnotherMod.cactusSword.getMaxDamage() / 3) - (rand.nextInt(Math.max(luckStat * YetAnotherMod.cactusSword.getMaxDamage() / 25, 1))), 4));
			
			if (luckStat > 3) {
				sword.addEnchantment(Enchantment.sharpness, (luckStat - 1) / 2);
			}
			
			stacks.add(sword);
		}
		if (rand.nextInt(5 - Math.max(luckStat / 3, 3)) == 0) {
			stacks.add(new ItemStack(YetAnotherMod.heart, rand.nextInt(2 + Math.max(luckStat / 2, 4)) + 1));
			if (rand.nextBoolean()) {
				stacks.add(new ItemStack(YetAnotherMod.hearthalf, 1));
			}
		}
		if (rand.nextInt(40 - Math.max(luckStat / 5, 4)) == 0) {
			stacks.add(new ItemStack(YetAnotherMod.crystalheart, 1));
		}
		if (rand.nextInt(20 - Math.max(luckStat / 2, 9)) == 0) {
			stacks.add(new ItemStack(BlockPoop.pills[rand.nextInt(BlockPoop.pills.length)], 1));
		}
		if (rand.nextInt(20 - Math.max(luckStat / 2, 9)) == 0) {
			stacks.add(new ItemStack(BlockPoop.pills[rand.nextInt(BlockPoop.pills.length)], 1));
		}
		if (rand.nextInt(20 - Math.max(luckStat / 2, 9)) == 0) {
			stacks.add(new ItemStack(BlockPoop.pills[rand.nextInt(BlockPoop.pills.length)], 1));
		}
		
		return stacks;
	}

}
