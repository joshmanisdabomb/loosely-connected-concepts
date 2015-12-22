package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.EntityHiddenReptile;
import yam.entity.model.ModelHiddenReptile;

public class RenderHiddenReptile extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/hiddenreptile.png");

	public RenderHiddenReptile(ModelHiddenReptile modelRotting, float shadowSize) {
		super(modelRotting, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityHiddenReptile)par1Entity, par2, par4, par6, par8, par9);
    }
	
	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityHiddenReptile)par1EntityLiving, par2, par4, par6, par8, par9);
    }
	
	public void doRender(EntityHiddenReptile par1Tick, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRender((EntityLiving)par1Tick, par2, par4, par6, par8, par9);
    }

}
	