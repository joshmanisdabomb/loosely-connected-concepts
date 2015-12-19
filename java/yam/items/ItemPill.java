package yam.items;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import yam.CustomDamage;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.entity.EntityFly;
import yam.entity.extensions.ExtendedPlayer;

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
			this.executePillEffect(this.getPillEffect(par3EntityPlayer, par3EntityPlayer.worldObj.getSeed()), par2World, par3EntityPlayer);
			ExtendedPlayer.get(par3EntityPlayer).discoverPill(id);
		}
		return par1ItemStack;
    }
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (ExtendedPlayer.get(par2EntityPlayer).isPillDiscovered(id)) {
			PillEffect pe = this.getPillEffect(par2EntityPlayer, MinecraftServer.getServer().getEntityWorld().getSeed());
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
			case ICANSEEFOREVER:
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 6000, 0));
				break;
			case ICANTSEEFOREVER:
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 1200, 0));
				break;
			case PRETTYFLY:
				if (!world.isRemote) {
					int times = rand.nextInt(10) + 3;
					for (int i = 0; i < times; i++) {
						EntityFly fly = new EntityFly(world);
						fly.setPosition(player.posX + rand.nextDouble(), player.posY, player.posZ + rand.nextDouble());
						world.spawnEntityInWorld(fly);
					}
				}
				break;
			case PUBERTY:
				break;
			case TELEPILLS:
				//hmmm
				break;
			case STARBURST:
				player.addPotionEffect(new PotionEffect(CustomPotion.starburst.id, 200, 0));
				break;
			case BALLSOFSTEEL:
				player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
				break;
			case POISON:
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 150, 1));
				break;
			case AMNESIA:
				ExtendedPlayer.get(player).forgetAllPills();
				break;
			case HEALTHUP:
				IAttributeInstance a = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
				a.applyModifier(new AttributeModifier(UUID.randomUUID(), "pillHealthUp", 2.0D, 0));
				break;
			case HEALTHDOWN:
				a = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
				a.applyModifier(new AttributeModifier(UUID.randomUUID(), "pillHealthDown", -2.0D, 0));
				break;
			case HOURENERGY:
				for (ItemStack i : player.inventory.mainInventory) {
					if (i != null && i.getItem() instanceof ItemBlaster) {
						i.setItemDamage(0);
					}
				}
				break;
			case HEMATEMESIS:
				player.setHealth(2.0F);
				int amount = rand.nextInt(7) + 2;
				for (int i = 0; i < amount; i++) {
					player.func_146097_a(new ItemStack(YetAnotherMod.heart, 1), true, false);
				}
				break;
			case LEMONPARTY:
				amount = rand.nextInt(7) + 2;
				for (int i = 0; i < amount; i++) {
					player.func_146097_a(new ItemStack(YetAnotherMod.lemon, 1), true, false);
				}
				break;
			case LUCKUP:
				ExtendedPlayer.get(player).addLuck(1);
				break;
			case LUCKDOWN:
				ExtendedPlayer.get(player).addLuck(-1);
				break;
			case PARALYSIS:
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 5));
				break;
			case RUAWIZARD:
				player.addPotionEffect(new PotionEffect(CustomPotion.kineticallychallenged.id, 600, 0));
				break;
			case PERCS:
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 600, 2));
				break;
			case ADDICTED:
				player.addPotionEffect(new PotionEffect(CustomPotion.amplify.id, 600, 1));
				break;
			case INFESTED:
				for (int i = -10; i <= 10; i++) {
					for (int j = -10; j <= 10; j++) {
						for (int k = -10; k <= 10; k++) {
							if (world.getBlock((int)(player.posX + i), (int)(player.posY + j), (int)(player.posZ + k)) == YetAnotherMod.poop) {
								world.func_147480_a((int)(player.posX + i), (int)(player.posY + j), (int)(player.posZ + k), true);
							}
						}
					}
				}
				break;
			case RELAX:
				if (!world.isRemote) {
					int times = 1;
					for (int i = 0; i < times; i++) {
						EntityFallingBlock flyingpoop = new EntityFallingBlock(world, player.posX, player.posY + 3, player.posZ, Blocks.sand);
						flyingpoop.motionX = rand.nextDouble() - 0.5D;
						flyingpoop.motionY = rand.nextDouble();
						flyingpoop.motionZ = rand.nextDouble() - 0.5D;
						flyingpoop.field_145813_c = false;
						world.spawnEntityInWorld(flyingpoop);
					}
				}
				break;
			case SPEEDUP:
				a = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
				a.applyModifier(new AttributeModifier(UUID.randomUUID(), "pillSpeedUp", 0.05D, 0));
				break;
			case SPEEDDOWN:
				a = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
				a.applyModifier(new AttributeModifier(UUID.randomUUID(), "pillSpeedDown", -0.05D, 0));
				break;
			case PHEROMONES:
				AxisAlignedBB aabb = player.boundingBox;
				if (aabb != null) {
					List entities = world.getEntitiesWithinAABBExcludingEntity(player, aabb.expand(9.0D, 9.0D, 9.0D));
					for (Object ent : entities) {
						if (ent instanceof EntityMob && !(ent instanceof IBossDisplayData)) {
							((EntityLivingBase)ent).addPotionEffect(new PotionEffect(CustomPotion.charm.id, Integer.MAX_VALUE, 0));
						}
					}
				}
				break;
			case APRILFOOLS:
				player.setLocationAndAngles(world.getSpawnPoint().posX, world.getSpawnPoint().posY, world.getSpawnPoint().posZ, player.cameraPitch, player.cameraYaw);
				break;
			case DOUEVENLIFT:
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
				break;
			case TIMMYTIMEKEEP:
				world.setWorldTime(world.getWorldTime() + 12000);
				break;
			case FORGETMENOW:
				player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).removeAllModifiers();
				player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).removeAllModifiers();
				player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).removeAllModifiers();
				player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).removeAllModifiers();
				ExtendedPlayer.get(player).forgetAllPills();
				break;
		}
	}
	
	public PillEffect getPillEffect(EntityPlayer player, long seed) {
		Random rand = new Random(seed);
		PillEffect[] effects = new PillEffect[9];
		
		for (int i = 0; i < 9; i++) {
			effects[i] = PillEffect.getEffectByID(rand.nextInt(PillEffect.values().length));
			if (effects[i] == PillEffect.BADTRIP && player.getHealth() <= 2.0D) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.ICANTSEEFOREVER && player.isPotionActive(Potion.blindness)) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.POISON && player.isPotionActive(Potion.poison)) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.HEALTHDOWN && player.getMaxHealth() <= 10.0D) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.LUCKDOWN && ExtendedPlayer.get(player).getLuck() <= -7) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.ADDICTED && player.isPotionActive(CustomPotion.amplify)) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.SPEEDDOWN && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue() <= 0.1D) {
				effects[i] = effects[i].fallback;
			} else if (effects[i] == PillEffect.DAMAGEDOWN && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).getAttributeValue() <= 0.5D) {
				effects[i] = effects[i].fallback;
			}
		}
		
		return effects[id];
	}
	
	public static enum PillEffect {
		FULLHEALTH(0, EnumChatFormatting.GREEN, "Full Health", null),
		BADTRIP(1, EnumChatFormatting.RED, "Bad Trip", FULLHEALTH),
		BADGAS(2, EnumChatFormatting.YELLOW, "Bad Gas", null),
		HEALTHISFOOD(3, EnumChatFormatting.YELLOW, "Health is Food", null),
		EXPLOSIVEDIARRHEA(4, EnumChatFormatting.YELLOW, "Explosive Diarrhea", null),
		IFOUNDPILLS(5, EnumChatFormatting.YELLOW, "I Found Pills", null),
		ICANSEEFOREVER(6, EnumChatFormatting.GREEN, "I Can See Forever", null), //night vision
		ICANTSEEFOREVER(7, EnumChatFormatting.RED, "I Can't See Forever", ICANSEEFOREVER), //blindness
		PRETTYFLY(8, EnumChatFormatting.RED, "Pretty Flies", null), //spawns flies
		PUBERTY(9, EnumChatFormatting.YELLOW, "Puberty", null),
		TELEPILLS(10, EnumChatFormatting.YELLOW, "Telepills", null),
		STARBURST(11, EnumChatFormatting.GREEN, "Starburst", null),
		BALLSOFSTEEL(12, EnumChatFormatting.GREEN, "Balls of Steel", null), //absorption for 2 minutes
		POISON(13, EnumChatFormatting.RED, "Poisonous Pill", BALLSOFSTEEL),
		AMNESIA(14, EnumChatFormatting.RED, "Amnesia", null), //forget all pill effects
		HEALTHUP(15, EnumChatFormatting.GREEN, "Health Up", null),
		HEALTHDOWN(16, EnumChatFormatting.RED, "Health Down", HEALTHUP),
		HOURENERGY(17, EnumChatFormatting.GREEN, "48 Hour Energy", null), //recharges items that run on energy
		HEMATEMESIS(18, EnumChatFormatting.YELLOW, "Hematemesis", null),
		LEMONPARTY(19, EnumChatFormatting.GREEN, "Lemon Party", null), //spawns lemons
		LUCKUP(20, EnumChatFormatting.GREEN, "Luck Up", null),
		LUCKDOWN(21, EnumChatFormatting.RED, "Luck Down", LUCKUP),
		PARALYSIS(22, EnumChatFormatting.RED, "Paralysis", null), //stun potion effect
		RUAWIZARD(23, EnumChatFormatting.RED, "R U a Wizard?", null), //confusion potion effect
		PERCS(24, EnumChatFormatting.GREEN, "Percs!", null), //resistance
		ADDICTED(25, EnumChatFormatting.RED, "aDDiCteD!?", PERCS), //amplify damage
		INFESTED(26, EnumChatFormatting.YELLOW, "Infested!", null), //breaks poop everywhere
		RELAX(27, EnumChatFormatting.YELLOW, "Re-Lax", null), //spawns poop everywhere
		SPEEDUP(28, EnumChatFormatting.GREEN, "Speed Up", null),
		SPEEDDOWN(29, EnumChatFormatting.RED, "Speed Down", SPEEDUP),
		DAMAGEUP(30, EnumChatFormatting.GREEN, "Damage Up", null),
		DAMAGEDOWN(31, EnumChatFormatting.RED, "Damage Down", DAMAGEUP),
		PHEROMONES(32, EnumChatFormatting.GREEN, "Pheromones", null), //charms enemies to kill eachother
		APRILFOOLS(33, EnumChatFormatting.YELLOW, "April Fools", null), //back to spawn
		DOUEVENLIFT(34, EnumChatFormatting.GREEN, "Do You Even Lift?", null),
		TIMMYTIMEKEEP(35, EnumChatFormatting.YELLOW, "Timmy the Timekeep", null), //adds 12 hours
		FORGETMENOW(36, EnumChatFormatting.YELLOW, "Forget Me Now", null);
		
		private int index;
		private EnumChatFormatting color;
		private String description;
		private PillEffect fallback;
		
		PillEffect(int index, EnumChatFormatting color, String description, PillEffect fallback) {
			this.index = index;
			this.color = color;
			this.description = description;
			this.fallback = fallback;
		}
		
		public static PillEffect getEffectByID(int id) {
			for (PillEffect pe : PillEffect.values()) {
		        if (pe.index == id) return pe;
		    }
			return null;
		}
	}
	
}
