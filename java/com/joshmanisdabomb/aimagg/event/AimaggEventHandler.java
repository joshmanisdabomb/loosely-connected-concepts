package com.joshmanisdabomb.aimagg.event;

import java.util.List;

import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggEventHandler {
	
	@SubscribeEvent
	public void onExplode(ExplosionEvent.Detonate event) {
		List<Entity> entityList = event.getAffectedEntities();
		for (Entity e : entityList) {
			if (e instanceof AimaggEntityMissile) {
				((AimaggEntityMissile)e).detonate();
				e.setDead();
			}
		}
	}
	
}