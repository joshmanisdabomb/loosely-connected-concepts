package com.joshmanisdabomb.aimagg.event;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggDamage;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpikes.SpikesType;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;
import com.joshmanisdabomb.aimagg.data.world.AimaggSeedData;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityPlayerRainbowPadRender;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AimaggEventHandler {
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (!event.getWorld().isRemote) {
			AimaggSeedData aimaggSeed = AimaggSeedData.getInstance(event.getWorld());
			aimaggSeed.setAimaggSeed(new Random(Math.abs((int)(event.getWorld().getSeed() + 1337 + 420 + 69))).nextLong());
			aimaggSeed.save(event.getWorld());
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		//TODO Send aimagg seed to client via packet.
	}
	
	@SubscribeEvent
	public void onExplode(ExplosionEvent.Detonate event) {
		/*List<Entity> entityList = event.getAffectedEntities();
		for (Entity e : entityList) {
			if (e instanceof AimaggEntityMissile) {
				((AimaggEntityMissile)e).detonate();
				e.setDead();
			}
		}*/
	}
	
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Pre event) {
		if (AimaggEntityPlayerRainbowPadRender.isOnActivePad(event.getEntityPlayer())) {
			AimaggEntityPlayerRainbowPadRender.getInstance(event.getRenderer().getRenderManager(), ((AbstractClientPlayer)event.getEntityPlayer()).getSkinType().equals("slim")).doRender((AbstractClientPlayer)event.getEntityPlayer(), event.getX(), event.getY(), event.getZ(), event.getEntityPlayer().rotationYaw, event.getPartialRenderTick());
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityHit(LivingHurtEvent event) {
		//TODO add sounds when iron and crystal hearts are removed
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = ((EntityPlayer)event.getEntityLiving());
			float amount = event.getAmount();

			IHearts hearts = player.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
			
			if (hearts.getIronHealth() > 0.0F) {
				float amountAfterArmor = (!event.getSource().isUnblockable() ? CombatRules.getDamageAfterAbsorb(amount, (float)player.getTotalArmorValue(), (float)player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()) : amount) * ((player.isPotionActive(MobEffects.RESISTANCE) && !event.getSource().isDamageAbsolute() && event.getSource() != DamageSource.OUT_OF_WORLD) ? (float)(25 - (player.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5 * (1/25.0F)) : 1.0F);
				float healthAfter = hearts.getIronHealth() - amountAfterArmor;
				if (healthAfter >= 0.0F) {
					hearts.addIronHealth(-amountAfterArmor);
					event.setCanceled(true);
					AimaggCapabilityHearts.sendHeartsPacket(player);
					
					float armorDamage = Math.min(amount / 4.0F, 1.0F);
			        for (ItemStack i : player.getArmorInventoryList()) {
			        	if (i.getItem() instanceof ItemArmor) {
			                i.damageItem((int)armorDamage, player);
			            }
			        }
					
					return;
				} else {
					amount -= hearts.getIronHealth();
					hearts.setIronHealth(0);
					
					float armorDamage = Math.min(hearts.getIronHealth() / 4.0F, 1.0F);
			        for (ItemStack i : player.getArmorInventoryList()) {
			        	if (i.getItem() instanceof ItemArmor) {
			                i.damageItem((int)armorDamage, player);
			            }
			        }
				}
			}
			if (hearts.getCrystalHealth() > 0.0F) {
				float amountAfterReduction = amount - Math.min(amount / 2, 5.0F);
				float healthAfter = hearts.getCrystalHealth() - amountAfterReduction;
				if (healthAfter >= 0.0F) {
					hearts.addCrystalHealth(-amountAfterReduction);
					event.setCanceled(true);
					AimaggCapabilityHearts.sendHeartsPacket(player);
					
					float armorDamage = Math.min(amount / 4.0F, 1.0F);
			        for (ItemStack i : player.getArmorInventoryList()) {
			        	if (i.getItem() instanceof ItemArmor) {
			                i.damageItem((int)armorDamage, player);
			            }
			        }
			        
					return;
				} else {
					amount -= hearts.getCrystalHealth();
					hearts.setCrystalHealth(0);
					
					float armorDamage = Math.min(hearts.getCrystalHealth() / 4.0F, 1.0F);
			        for (ItemStack i : player.getArmorInventoryList()) {
			        	if (i.getItem() instanceof ItemArmor) {
			                i.damageItem((int)armorDamage, player);
			            }
			        }
				}
			}
			if (amount != event.getAmount()) {AimaggCapabilityHearts.sendHeartsPacket(player);}
			event.setAmount(amount);
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EntityLiving && event.getSource() instanceof AimaggDamage && ((AimaggDamage)event.getSource()).getSpikesType() == SpikesType.AMPLIFIED) {
			//
		}
	}
	
}