package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;
import com.joshmanisdabomb.aimagg.entity.AimaggEntityNuclearExplosion;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityMissileRender;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggEntities {

	public static void init() {
		int entityid = 1;
		EntityRegistry.registerModEntity(AimaggEntityMissile.class, "missile", entityid++, AimlessAgglomeration.instance, 64, 1, true);
		EntityRegistry.registerModEntity(AimaggEntityNuclearExplosion.class, "missilenuclexp", entityid++, AimlessAgglomeration.instance, 16, 20, false);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(AimaggEntityMissile.class, AimaggEntityMissileRender.FACTORYEXPLOSIVE);
	}
	
}
