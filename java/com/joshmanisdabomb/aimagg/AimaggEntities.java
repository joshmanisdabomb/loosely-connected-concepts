package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityMissileRender;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggEntities {

	public static ArrayList<EntityEntry> registry = new ArrayList<EntityEntry>();

	public static void init() {
		AimaggEntities.registry.add(new EntityEntry(AimaggEntityMissile.class, "missile").setRegistryName(Constants.MOD_ID, "missile"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(AimaggEntityMissile.class, AimaggEntityMissileRender.FACTORYEXPLOSIVE);
	}
	
}
