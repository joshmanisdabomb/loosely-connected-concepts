package yam.items;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import yam.CustomPotion;

public class ItemPill extends ItemNoms {

	private static PillPotion[] potions = new PillPotion[] {
		new PillPotion(Potion.blindness, 8, 30, 0),
		new PillPotion(Potion.confusion, 6, 120, 0),
		new PillPotion(Potion.damageBoost, 16, 180, 3),
		new PillPotion(Potion.digSlowdown, 20, 120, 3),
		new PillPotion(Potion.digSpeed, 20, 180, 3),
		new PillPotion(Potion.field_76434_w, 60, 150, 1),
		new PillPotion(Potion.field_76444_x, 60, 180, 1),
		new PillPotion(Potion.field_76443_y, 1, 30, 3),
		new PillPotion(Potion.fireResistance, 12, 120, 3),
		new PillPotion(Potion.harm, 0, 0, 3),
		new PillPotion(Potion.heal, 0, 0, 3),
		new PillPotion(Potion.hunger, 1, 30, 3),
		new PillPotion(Potion.invisibility, 10, 40, 0),
		new PillPotion(Potion.jump, 10, 120, 3),
		new PillPotion(Potion.moveSlowdown, 12, 180, 3),
		new PillPotion(Potion.moveSpeed, 12, 180, 3),
		new PillPotion(Potion.nightVision, 16, 180, 0),
		new PillPotion(Potion.poison, 4, 30, 3),
		new PillPotion(Potion.regeneration, 4, 30, 3),
		new PillPotion(Potion.resistance, 12, 180, 3),
		new PillPotion(Potion.waterBreathing, 6, 180, 0),
		new PillPotion(Potion.weakness, 6, 180, 3),
		new PillPotion(Potion.wither, 4, 16, 3),
		new PillPotion(CustomPotion.amplify, 12, 180, 3),
		new PillPotion(CustomPotion.bleeding, 60, 150, 3)
	};
	
	private Random rand = new Random();
	
	public ItemPill(String string) {
		super("pills/" + string, 0, 0, false);
		this.setAlwaysEdible();
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par2World.isRemote) {
			par1ItemStack.stackSize--;
			
			int random = rand.nextInt(1);
			switch(random) {
				default: { //Potion Effect
					par3EntityPlayer.addPotionEffect(potions[rand.nextInt(potions.length)].getPotionEffect());
					return par1ItemStack;
				}
				case 1: { //Heal
					par3EntityPlayer.setHealth(par3EntityPlayer.getHealth()+(rand.nextFloat()*(par3EntityPlayer.getMaxHealth()-par3EntityPlayer.getHealth()))+1);
					return par1ItemStack;
				}
			}
		}
		return par1ItemStack;
    }
	
	private static class PillPotion {
		
		private Potion potion; private String potionPointer;
		private int minLength; private int maxLength;
		private int maxAmplifier;
		
		private Random rand = new Random();
		
		private PillPotion(Potion potion, int minLength, int maxLength, int maxAmplifier) {
			this.potion = potion;
			this.minLength = minLength;
			this.maxLength = maxLength;
			this.maxAmplifier = maxAmplifier;
		}
		
		public Potion getPotion() {
			return this.potion;
		}
		
		public int getRandomTime() {
			return this.potion.isInstant() ? 1 : (this.rand.nextInt((this.maxLength - this.minLength) + 1) + this.minLength) * 20;
		}
		
		public int getRandomAmplifier() {
			return this.rand.nextInt(this.maxAmplifier + 1);
		}
		
		public PotionEffect getPotionEffect() {
			System.out.println(this.potionPointer);
			return new PotionEffect(this.potion.id, this.getRandomTime(), this.getRandomAmplifier());
		}
		
	}
	
}
