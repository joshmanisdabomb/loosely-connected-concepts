package com.joshmanisdabomb.aimagg.data.capabilities;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.PillsProvider;
import com.joshmanisdabomb.aimagg.util.PillModifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class AimaggCapabilityHandler {

	public static final ResourceLocation HEARTS_CAPABILITY = new ResourceLocation(Constants.MOD_ID, "hearts");
	public static final ResourceLocation PILLS_CAPABILITY = new ResourceLocation(Constants.MOD_ID, "pills");
	
	@SubscribeEvent
	public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(HEARTS_CAPABILITY, new HeartsProvider());
			event.addCapability(PILLS_CAPABILITY, new PillsProvider());
		}
	}
	
	@SubscribeEvent
	//fired when the player dies (was death), or when the player goes through the ender dragon portal back to the overworld (!was death)
	public void onPlayerClone(PlayerEvent.Clone event) {
		IHearts oldHearts = event.getOriginal().getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
		IHearts newHearts = event.getEntityPlayer().getCapability(HeartsProvider.HEARTS_CAPABILITY, null);

		if (!event.isWasDeath()) {
			newHearts.setIronHealth(oldHearts.getIronHealth());
			newHearts.setIronMaxHealth(oldHearts.getIronMaxHealth());
			newHearts.setCrystalHealth(oldHearts.getCrystalHealth());
			newHearts.setCrystalMaxHealth(oldHearts.getCrystalMaxHealth());
		}

		IPills oldPills = event.getOriginal().getCapability(PillsProvider.PILLS_CAPABILITY, null);
		IPills newPills = event.getEntityPlayer().getCapability(PillsProvider.PILLS_CAPABILITY, null);

		if (event.isWasDeath()) {
			newPills.setExtraHealth(Math.max(oldPills.getExtraHealth(), PillModifier.EXTRA_HEALTH.getInitialAmount()));
		} else {
			newPills.setExtraHealth(oldPills.getExtraHealth());
		}
		newPills.setExtraSpeed(oldPills.getExtraSpeed());
		newPills.setExtraLuck(oldPills.getExtraLuck());
		newPills.setExtraDamage(oldPills.getExtraDamage());
		newPills.setExtraAttackSpeed(oldPills.getExtraAttackSpeed());
		
		newPills.setLastPrimaryColor(oldPills.getLastPrimaryColor());
		newPills.setLastSecondaryColor(oldPills.getLastSecondaryColor());
		
		for (String s : oldPills.getDiscoveries()) {
			newPills.addDiscovery(s);
		}
		
		PillModifier.applyAllModifiers(event.getEntityPlayer());
	}

	@SubscribeEvent
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			AimaggCapabilityHearts.sendHeartsPacket(event.player);
			AimaggCapabilityPills.sendPillsPacket(event.player);
			PillModifier.applyAllModifiers(event.player);
		}
	}

	@SubscribeEvent
	public void onPlayerSwitchDimension(PlayerChangedDimensionEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			AimaggCapabilityHearts.sendHeartsPacket(event.player);
			AimaggCapabilityPills.sendPillsPacket(event.player);
			PillModifier.applyAllModifiers(event.player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			AimaggCapabilityHearts.sendHeartsPacket(event.player);
			AimaggCapabilityPills.sendPillsPacket(event.player);
			PillModifier.applyAllModifiers(event.player);
		}
	}
	
}
