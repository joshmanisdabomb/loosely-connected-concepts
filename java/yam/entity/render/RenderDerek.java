package yam.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.EntityDerek;
import yam.entity.model.ModelDerek;

public class RenderDerek extends RenderLiving {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/derek.png");

	public RenderDerek(ModelDerek modelDerek, float shadowSize) {
		super(modelDerek, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityDerek)par1Entity, par2, par4, par6, par8, par9);
    }
	
	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityDerek)par1EntityLiving, par2, par4, par6, par8, par9);
    }
	
	public void doRender(EntityDerek par1Derek, double par2, double par4, double par6, float par8, float par9)
    {
        BossStatus.setBossStatus(par1Derek, true);
        super.doRender((EntityLiving)par1Derek, par2, par4, par6, par8, par9);
    }

}
	