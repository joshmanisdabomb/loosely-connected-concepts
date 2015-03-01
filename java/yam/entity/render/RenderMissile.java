package yam.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.entity.EntityMissile;
import yam.entity.model.ModelMissile;

public class RenderMissile extends Render {
	
	private static final ResourceLocation textureLocation = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/missilered.png");
	private ModelMissile model;

	public RenderMissile(ModelMissile model) {
		super();
		this.model = model;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
        doRender((EntityMissile)entity, par2, par4, par6, par8, par9);
    }
	
	public void doRender(EntityMissile entity, double par2, double par4, double par6, float par8, float par9)
    {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef((float)entity.rotationX, 0.0F, (float)entity.rotationZ, 0.0F);

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

}
