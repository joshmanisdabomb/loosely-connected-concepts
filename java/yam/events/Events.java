package yam.events;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent.BiomeSize;
import net.minecraftforge.event.terraingen.WorldTypeEvent.InitBiomeGens;
import net.minecraftforge.event.world.BlockEvent;
import yam.CustomDamage;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.biome.BiomeWasteland;
import yam.entity.EntityRainbot;
import yam.gen.GenLayerBiomeCustom;
import yam.items.tools.ItemCustomArmor;
import yam.items.tools.ItemRepeater;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Events {
	
	public Random rand = new Random();
	
	@SubscribeEvent
	public void whenBucketUsed(FillBucketEvent event) {
		if (!event.entityPlayer.capabilities.isCreativeMode && event.entityPlayer.dimension == YetAnotherMod.rainbowDimID) {event.setCanceled(true);}
	}
	
	@SubscribeEvent
	public void whenDamageTaken(LivingHurtEvent event) {
		PotionEffect pe = event.entityLiving.getActivePotionEffect(CustomPotion.amplify);
		if (pe != null && event.ammount > 0.0F) {
			event.ammount += Math.max((event.ammount/2)*(pe.getAmplifier()+1), 1.0F);
		}
	}
	
	@SubscribeEvent
	public void whenEntityHit(LivingAttackEvent event) {
		if (event.source.getEntity() != null && (event.entityLiving.dimension == YetAnotherMod.moonDimID || event.entityLiving.dimension == YetAnotherMod.asmiaDimID)) {
			spaceKnockback(event.entityLiving, event.source.getEntity().posX - event.entityLiving.posX, event.source.getEntity().posZ - event.entityLiving.posZ);
			if (event.entityLiving instanceof EntityPlayerMP) {
				((EntityPlayerMP)event.entityLiving).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(event.entityLiving));
			}
		}
		
		if (event.source.getEntity() instanceof EntityLivingBase && !event.source.isExplosion()) {
			EntityLivingBase entity = ((EntityLivingBase)event.source.getEntity());
			ItemStack i = entity.getEquipmentInSlot(0);
			if (i != null && event.entityLiving != null) {
				int e;
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.poisonAspect.effectId, i);
				if (e > 0) {
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 80-(e*10), e-1));
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.frozenAspect.effectId, i);
				if (e > 0) {
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, (e-1)*2));
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.jump.id, 70, 127));
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 70, (e-1)*2));
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.lifesteal.effectId, i);
				if (e > 0) {
					entity.setHealth(entity.getHealth()+(event.ammount/(9-e)));
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.mandible.effectId, i);
				if (e > 0 && entity instanceof EntityPlayer && rand.nextInt(12-(e*2)) == 0) {
					((EntityPlayer)entity).getFoodStats().setFoodLevel(Math.min(((EntityPlayer)entity).getFoodStats().getFoodLevel()+1, 20));
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.spring.effectId, i);
				if (e > 0) {
					event.entityLiving.motionY = e/0.6D;
					if (event.entityLiving instanceof EntityPlayerMP) {
						((EntityPlayerMP)event.entityLiving).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(event.entityLiving));
					}
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.wisdom.effectId, i);
				if (e > 0 && entity instanceof EntityPlayer && event.entityLiving.hurtTime <= 0) {
					((EntityPlayer)entity).addExperience((event.entityLiving instanceof IBossDisplayData) ? e+5 : (event.entityLiving instanceof EntityPlayer) ? e+2 : (event.entityLiving instanceof EntityMob) ? e+1 : e);
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.charged.effectId, i);
				if (e > 0) {
					event.entityLiving.worldObj.createExplosion(entity, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, e*0.4F, false);
					event.setCanceled(true);
				}
				e = EnchantmentHelper.getEnchantmentLevel(YetAnotherMod.lacerate.effectId, i);
				if (e > 0) {
					event.entityLiving.addPotionEffect(new PotionEffect(CustomPotion.bleeding.id, 360+(e*60), (e-1)));
				}
			}
		}
	}
	
	private void spaceKnockback(EntityLivingBase entityLiving, double par3, double par5) {
		entityLiving.isAirBorne = true;
        float f1 = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
        float f2 = 1.3F;
        entityLiving.motionX /= 2.0D;
        entityLiving.motionY /= 2.0D;
        entityLiving.motionZ /= 2.0D;
        entityLiving.motionX -= par3 / (double)f1 * (double)f2;
        entityLiving.motionY += 0.4F;
        entityLiving.motionZ -= par5 / (double)f1 * (double)f2;
	}
	
	@SubscribeEvent
	public void whenKilled(LivingDeathEvent event) {
		PotionEffect pe = event.entityLiving.getActivePotionEffect(CustomPotion.mlg);
		if (pe != null) {
			event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, YetAnotherMod.MODID + ":mob.mlg.shutdown", 1.0F, 1.0F);
		}
		
		if (event.source.getEntity() instanceof EntityRainbot && event.entityLiving.getHealth() <= 0.0F && event.entityLiving.deathTime == 0) {
			((EntityRainbot)event.source.getEntity()).playKillSound();
		}
	}
	
	@SubscribeEvent
	public void whenLivingUpdated(LivingEvent.LivingUpdateEvent event) {
		int cactusDamage = ItemCustomArmor.getCactusDamage(event.entityLiving.getEquipmentInSlot(0),event.entityLiving.getEquipmentInSlot(1),event.entityLiving.getEquipmentInSlot(2),event.entityLiving.getEquipmentInSlot(3),event.entityLiving.getEquipmentInSlot(4));
		if (cactusDamage > 0) {
			event.entityLiving.attackEntityFrom(CustomDamage.cactus2, cactusDamage);
		}
		if (event.entityLiving.dimension == YetAnotherMod.moonDimID || event.entityLiving.dimension == YetAnotherMod.asmiaDimID) {
			if (!ItemCustomArmor.isFullSpacesuit(event.entityLiving.getEquipmentInSlot(1),event.entityLiving.getEquipmentInSlot(2),event.entityLiving.getEquipmentInSlot(3),event.entityLiving.getEquipmentInSlot(4))) {
				event.entityLiving.attackEntityFrom(CustomDamage.breath, 1);
			}
			if (!event.entity.onGround) {
				event.entity.motionX *= 1.02;
				event.entity.motionZ *= 1.02;
			} else {
				event.entity.motionX /= 2.5;
				event.entity.motionZ /= 2.5;
			}
			if (event.entity.motionY > 0) {
				event.entity.motionY *= 1.25;
			} else {
				event.entity.motionY /= 1.05;
			}
		}
		if (event.entity.isInWater() && event.entity.worldObj.getBiomeGenForCoords((int)Math.floor(event.entity.posX),(int)Math.floor(event.entity.posZ)) == YetAnotherMod.biomeWasteland) {
			if (((BiomeWasteland)YetAnotherMod.biomeWasteland).isWastelandMonster(event.entity)) {
				event.entity.motionX *= 1.2;
				event.entity.motionY *= 1.2;
				event.entity.motionZ *= 1.2;
			} else {
				if (!(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).capabilities.isCreativeMode)) {
					event.entity.motionX /= 1.3;
					event.entity.motionY /= 1.3;
					event.entity.motionZ /= 1.3;
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, 80, 0));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		if ((event.entityLiving.dimension == YetAnotherMod.moonDimID || event.entityLiving.dimension == YetAnotherMod.asmiaDimID)) {
			event.entityLiving.motionX = 0;
			event.entityLiving.motionZ = 0;
		}
	}
	
	@SubscribeEvent
	public void onFall(LivingFallEvent event) {
		if ((event.entityLiving.dimension == YetAnotherMod.moonDimID || event.entityLiving.dimension == YetAnotherMod.asmiaDimID)) {
			event.distance = 0;
		}
	}
	
	@SubscribeEvent
	public void whenBlockBroken(BlockEvent.HarvestDropsEvent event) {
		if (!event.world.isRemote && (event.block == Blocks.leaves || event.block == Blocks.leaves2) && (event.harvester == null || !event.harvester.capabilities.isCreativeMode) && !event.isSilkTouching) {
			event.drops.clear();
			event.drops.add(new ItemStack(rand.nextInt(4) == 0 ? YetAnotherMod.cherry : rand.nextInt(3) == 0 ? YetAnotherMod.orange : rand.nextInt(2) == 0 ? YetAnotherMod.lemon : YetAnotherMod.lime));
			event.dropChance = 0.006F;
		}	
	}
	
	@SubscribeEvent
	public void whenBowFire(PlayerUseItemEvent.Stop event) {
		if (event.item.getItem() == Items.bow) {
			PotionEffect pe = event.entityPlayer.getActivePotionEffect(CustomPotion.mlg);
			if (pe != null) {
				ItemRepeater.fireMLGArrow(event.entityPlayer.worldObj, event.entityPlayer);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void whenMilkDrank(PlayerUseItemEvent.Finish event) {
		if (event.item.getItem() == Items.milk_bucket) {
			event.entityPlayer.refreshDisplayName();
		}
	}
	
	@SubscribeEvent
	public void whenNameGet(PlayerEvent.NameFormat event) {
		PotionEffect pe = event.entityPlayer.getActivePotionEffect(CustomPotion.mlg);
		if (pe == null) {
			event.displayname = event.username;
		} else if (pe.getDuration() <= 1) {
			event.displayname = event.username;
		} else {
			event.displayname = "xXx_" + event.username + "_xXx";
		}
	}
	
	@SubscribeEvent
	public void interpretBlockPlace(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.entityPlayer.getCurrentEquippedItem() != null ? Block.getBlockFromItem(event.entityPlayer.getCurrentEquippedItem().getItem()) : null;
			int m = event.entityPlayer.getCurrentEquippedItem() != null ? event.entityPlayer.getCurrentEquippedItem().getItemDamage() : 0;
			int x = event.x + (event.face == 4 ? -1 : event.face == 5 ? +1 : 0);
			int y = event.y + (event.face == 0 ? -1 : event.face == 1 ? +1 : 0);
			int z = event.z + (event.face == 2 ? -1 : event.face == 3 ? +1 : 0);
			if (b != null && b != Blocks.air) {
				PlaceBlock event2 = new PlaceBlock(x, y, z, event.world, b, m, event.entityPlayer);
				MinecraftForge.EVENT_BUS.post(event2);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockPlace(PlaceBlock event) {
		if ((event.getBlock() == Blocks.torch || event.getBlock() == Blocks.redstone_torch) && (event.world.provider.dimensionId == YetAnotherMod.moonDimID || event.world.provider.dimensionId == YetAnotherMod.asmiaDimID)) {
			event.setBlock(YetAnotherMod.torchOff);
		} else if ((event.getBlock() == Blocks.yellow_flower || event.getBlock() == Blocks.red_flower) && (event.world.provider.dimensionId == YetAnotherMod.moonDimID || event.world.provider.dimensionId == YetAnotherMod.asmiaDimID)) {
			event.setBlock(Blocks.deadbush);
		}
	}
	
	@SubscribeEvent
	public void registerBiomes(InitBiomeGens event){
		for (int i = 0; i < 3; i++) {
			GenLayer ret = new GenLayerBiomeCustom(event.seed, event.originalBiomeGens[i], event.worldType);
	        ret = GenLayerZoom.magnify(event.seed, ret, 8);
	        ret = new GenLayerBiomeEdge(event.seed, ret);
	        event.newBiomeGens[i] = ret;
		}
        
	}
	
	@SubscribeEvent
	public void changeBiomeSize(BiomeSize event){
		//event.newSize = 1;
	}
}