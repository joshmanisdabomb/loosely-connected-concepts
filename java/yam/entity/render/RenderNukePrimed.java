package yam.entity.render;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.entity.EntityNukePrimed;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class RenderNukePrimed extends RenderTNTPrimed {

    private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderNukePrimed(float shadowSize) {
		super();
		
        this.shadowSize = shadowSize;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return TextureMap.locationBlocksTexture;
	}
	
	public void doRender(EntityNukePrimed par1EntityNukePrimed, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        float f2;

        if ((float)par1EntityNukePrimed.fuse - par9 + 1.0F < 10.0F)
        {
            f2 = 1.0F - ((float)par1EntityNukePrimed.fuse - par9 + 1.0F) / 10.0F;

            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }

            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }

            f2 *= f2;
            f2 *= f2;
            float f3 = 1.0F + f2 * 0.3F;
            GL11.glScalef(f3, f3, f3);
        }

        f2 = (1.0F - ((float)par1EntityNukePrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(par1EntityNukePrimed);
        this.blockRenderer.renderBlockAsItem(YetAnotherMod.nuke, 0, par1EntityNukePrimed.getBrightness(par9));

        if (par1EntityNukePrimed.fuse / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
            this.blockRenderer.renderBlockAsItem(YetAnotherMod.nuke, 0, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }
	
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityNukePrimed)par1Entity, par2, par4, par6, par8, par9);
    }

}
