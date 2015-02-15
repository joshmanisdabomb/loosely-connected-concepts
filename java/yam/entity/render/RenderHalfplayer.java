package yam.entity.render;

import yam.YetAnotherMod;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHalfplayer extends RenderBiped {
	
	private static final ResourceLocation textureLocation = new ResourceLocation("minecraft:textures/entity/steve.png");

	public RenderHalfplayer(ModelBiped model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
