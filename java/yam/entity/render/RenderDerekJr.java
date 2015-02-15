package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.model.ModelDerekJr;

public class RenderDerekJr extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/derekJr.png");

	public RenderDerekJr(ModelDerekJr modelDerekJr, float shadowSize) {
		super(modelDerekJr, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
	