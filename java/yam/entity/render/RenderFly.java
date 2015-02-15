package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.model.ModelFly;
import yam.entity.model.ModelLollipopper;

public class RenderFly extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/fly.png");

	public RenderFly(ModelFly modelFly, float shadowSize) {
		super(modelFly, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
