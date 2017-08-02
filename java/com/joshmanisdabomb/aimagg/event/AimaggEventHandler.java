package com.joshmanisdabomb.aimagg.event;

import java.util.List;

import com.ibm.icu.util.BytesTrie.Result;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketCapabilityHearts;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggEventHandler {
	
	@SubscribeEvent
	public void onExplode(ExplosionEvent.Detonate event) {
		List<Entity> entityList = event.getAffectedEntities();
		for (Entity e : entityList) {
			/*if (e instanceof AimaggEntityMissile) {
				((AimaggEntityMissile)e).detonate();
				e.setDead();
			}*/
		}
	}

	@SubscribeEvent
	public void onEntityHit(LivingHurtEvent event) {
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
	
}