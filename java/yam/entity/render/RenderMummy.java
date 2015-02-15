package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.model.ModelMummy;

public class RenderMummy extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/mummy.png");

	public RenderMummy(ModelMummy model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
