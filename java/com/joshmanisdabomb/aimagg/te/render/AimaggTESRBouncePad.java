package com.joshmanisdabomb.aimagg.te.render;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;
import com.joshmanisdabomb.aimagg.te.model.AimaggTEBouncePadModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class AimaggTESRBouncePad extends TileEntitySpecialRenderer {

	public static AimaggTEBouncePadModel model = new AimaggTEBouncePadModel();
	public static final ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/tesr/bounce_pad.png");
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)x+0.5, (float)y+0.5, (float)z+0.5);
        GlStateManager.enableRescaleNormal();
        if (te instanceof AimaggTEBouncePad && ((AimaggTEBouncePad)te).getDirection() != null) {
	        switch (((AimaggTEBouncePad)te).getDirection()) {
				case DOWN:
			        GlStateManager.rotate(180, 0, 0, 1);
					break;
				case EAST:
			        GlStateManager.rotate(90, 0, 0, 1);
			        GlStateManager.rotate(180, 1, 0, 0);
					break;
				case NORTH:
			        GlStateManager.rotate(90, 0, 0, 1);
			        GlStateManager.rotate(270, 1, 0, 0);
					break;
				case SOUTH:
			        GlStateManager.rotate(90, 0, 0, 1);
			        GlStateManager.rotate(90, 1, 0, 0);
					break;
				case WEST:
			        GlStateManager.rotate(90, 0, 0, 1);
					break;
				default:
			        break;
	        }
	        
	        float f = 0.0625F;
	        GlStateManager.scale(f, f, f);
	        GlStateManager.enableAlpha();
	        this.bindTexture(texture);

			model.render(te, 1);
        }

        GlStateManager.popMatrix();
	}
	
}
