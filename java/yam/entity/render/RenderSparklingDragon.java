package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.model.ModelLollipopper;
import yam.entity.model.ModelSparklingDragon;

public class RenderSparklingDragon extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/sparkledragon.png");

	public RenderSparklingDragon(ModelSparklingDragon modelSparklingDragon, float shadowSize) {
		super(modelSparklingDragon, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
