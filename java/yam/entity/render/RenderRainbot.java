package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.model.ModelRainbot;

public class RenderRainbot extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/rainbot.png");

	public RenderRainbot(ModelRainbot model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
