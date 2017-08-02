package com.joshmanisdabomb.aimagg.data.capabilities;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class AimaggCapabilityHandler {

	public static final ResourceLocation HEARTS_CAPABILITY = new ResourceLocation(Constants.MOD_ID, "hearts");
	
	@SubscribeEvent
	public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(HEARTS_CAPABILITY, new HeartsProvider());
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		IHearts oldHearts = event.getOriginal().getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
		IHearts newHearts = event.getEntityPlayer().getCapability(HeartsProvider.HEARTS_CAPABILITY, null);

		if (!event.isWasDeath()) {
			newHearts.setIronHealth(oldHearts.getIronHealth());
			newHearts.setIronMaxHealth(oldHearts.getIronMaxHealth());
			newHearts.setCrystalHealth(oldHearts.getCrystalHealth());
			newHearts.setCrystalMaxHealth(oldHearts.getCrystalMaxHealth());
		}
	}

	@SubscribeEvent
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		if (event.player instanceof EntityPlayerMP) {AimaggCapabilityHearts.sendHeartsPacket(event.player);}
	}

	@SubscribeEvent
	public void onPlayerSwitchDimension(PlayerChangedDimensionEvent event) {
		if (event.player instanceof EntityPlayerMP) {AimaggCapabilityHearts.sendHeartsPacket(event.player);}
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {AimaggCapabilityHearts.sendHeartsPacket(event.player);}
	}
	
}
