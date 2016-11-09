package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.entity.missile.AimaggEntityMissileExplosive;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityMissileSmallRender;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggEntities {

	public static void init() {
		int entityid = 1;
		EntityRegistry.registerModEntity(AimaggEntityMissileExplosive.class, "missileExplosive", entityid++, AimlessAgglomeration.instance, 64, 1, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(AimaggEntityMissileExplosive.class, AimaggEntityMissileSmallRender.FACTORYEXPLOSIVE);
	}
	
}
