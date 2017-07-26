package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;
import com.joshmanisdabomb.aimagg.entity.AimaggEntityNuclearExplosion;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityMissileRender;

import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggEntities {

	public static ArrayList<EntityEntry> registry = new ArrayList<EntityEntry>();

	public static void init() {
		AimaggEntities.registry.add(new EntityEntry(AimaggEntityMissile.class, "missile").setRegistryName(Constants.MOD_ID, "missile"));
		AimaggEntities.registry.add(new EntityEntry(AimaggEntityNuclearExplosion.class, "missilenuclexp").setRegistryName(Constants.MOD_ID, "missilenuclexp"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(AimaggEntityMissile.class, AimaggEntityMissileRender.FACTORYEXPLOSIVE);
	}
	
}
