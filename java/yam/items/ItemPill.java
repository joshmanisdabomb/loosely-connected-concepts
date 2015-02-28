package yam.items;

import java.util.List;
import java.util.Random;

import yam.CustomDamage;
import yam.CustomPotion;
import yam.entity.EntityFly;
import yam.entity.extensions.ExtendedPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ItemPill extends ItemNoms {
	
	private Random rand = new Random();
	private int id;
	
	public ItemPill(String string, int id) {
		super("pills/" + string, 0, 0, false);
		this.id = id;
		this.setAlwaysEdible();
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par2World.isRemote) {
			ExtendedPlayer.get(par3EntityPlayer).discoverPill(id);
			this.executePillEffect(this.getPillEffect(par3EntityPlayer.worldObj.getSeed()), par2World, par3EntityPlayer);
		}
		return par1ItemStack;
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		PillEffect pe = this.getPillEffect(par2EntityPlayer.worldObj.getSeed());
		if (ExtendedPlayer.get(par2EntityPlayer).isPillDiscovered(id)) {
			par3List.add(EnumChatFormatting.GRAY + "Effect: " + pe.color + pe.description);
		} else {
			par3List.add(EnumChatFormatting.GRAY + "Effect: " + EnumChatFormatting.DARK_BLUE + "???");
		}
	}
	
	public void executePillEffect(PillEffect pe, World world, EntityPlayer player) {
		player.addChatMessage(new ChatComponentText(pe.color + pe.description));
		switch(pe) {
			default:
				break;
			case FULLHEALTH:
				player.heal(player.getMaxHealth()-player.getHealth());
				break;
			case BADTRIP:
				player.attackEntityFrom(CustomDamage.pill, 2.0F);
				break;
			case BADGAS:
				Explosion e = new Explosion(world, player, player.posX, player.posY, player.posZ, 3.0F);
				e.isSmoking = true;
				e.isFlaming = false;
				e.doExplosionA();
				e.doExplosionB(true);
				break;
			case HEALTHISFOOD:
				float health = player.getHealth();
				float food = player.getFoodStats().getFoodLevel();
				player.setHealth(Math.min(Math.max(food, 1), player.getMaxHealth()));
				player.getFoodStats().setFoodLevel(Math.min(Math.max((int)health, 1), 20));
				break;
			case EXPLOSIVEDIARRHEA:
				ExtendedPlayer.get(player).startExplosiveDiarrhea();
				break;
			case IFOUNDPILLS:
				break;
			case PRETTYFLY:
				if (!world.isRemote && rand.nextInt(4) == 0) {
					int times = rand.nextInt(10) + 3;
					for (int i = 0; i < times; i++) {
						Entity fly = new EntityFly(world);
						fly.setPosition(player.posX + rand.nextDouble(), player.posY, player.posZ + rand.nextDouble());
						world.spawnEntityInWorld(fly);
					}
				}
				break;
			case PUBERTY:
				break;
			case TELEPILLS:
				break;
			case STARBURST:
				player.addPotionEffect(new PotionEffect(CustomPotion.starburst.id, 200, 0));
				break;
			case POISON:
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 150, 1));
				break;
		}
	}
	
	public PillEffect getPillEffect(long seed) {
		Random rand = new Random(seed);
		PillEffect[] effects = new PillEffect[9];
		
		for (int i = 0; i < 9; i++) {
			effects[i] = PillEffect.getEffectByID(rand.nextInt(PillEffect.values().length));
		}
		
		return effects[id];
	}
	
	public static enum PillEffect {
		FULLHEALTH(0, EnumChatFormatting.GREEN, "Full Health"),
		BADTRIP(1, EnumChatFormatting.RED, "Bad Trip"),
		BADGAS(2, EnumChatFormatting.YELLOW, "Bad Gas"),
		HEALTHISFOOD(3, EnumChatFormatting.YELLOW, "Health is Food"),
		EXPLOSIVEDIARRHEA(4, EnumChatFormatting.YELLOW, "Explosive Diarrhea"),
		IFOUNDPILLS(5, EnumChatFormatting.YELLOW, "I Found Pills"),
		ICANTSEEFOREVER(6, EnumChatFormatting.RED, "I Can't See Forever"),
		PRETTYFLY(7, EnumChatFormatting.RED, "Pretty Flies"),
		PUBERTY(8, EnumChatFormatting.YELLOW, "Puberty"),
		TELEPILLS(9, EnumChatFormatting.YELLOW, "Telepills"),
		STARBURST(10, EnumChatFormatting.GREEN, "Starburst"),
		POISON(11, EnumChatFormatting.RED, "Poisonous Pill");
		
		private int index;
		private EnumChatFormatting color;
		private String description;
		
		PillEffect(int index, EnumChatFormatting color, String description) {
			this.index = index;
			this.color = color;
			this.description = description;
		}
		
		public static PillEffect getEffectByID(int id) {
			for (PillEffect pe : PillEffect.values()) {
		        if (pe.index == id) return pe;
		    }
			return null;
		}
	}
	
}
