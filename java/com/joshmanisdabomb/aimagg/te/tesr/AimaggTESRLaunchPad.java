package com.joshmanisdabomb.aimagg.te.tesr;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.MissileType;
import com.joshmanisdabomb.aimagg.entity.model.AimaggEntityMissileLargeModel;
import com.joshmanisdabomb.aimagg.entity.model.AimaggEntityMissileSmallModel;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;
import com.joshmanisdabomb.aimagg.te.model.AimaggTELaunchPadModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggTESRLaunchPad extends TileEntitySpecialRenderer {

	public static AimaggTELaunchPadModel model = new AimaggTELaunchPadModel();
	public static AimaggEntityMissileSmallModel missileModel = new AimaggEntityMissileSmallModel();
	public static AimaggEntityMissileLargeModel missileLargeModel = new AimaggEntityMissileLargeModel();
	public static final ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/tesr/launchpad.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick, int destroyStage) {
		GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)x+0.5, (float)y+2, (float)z+0.5);
        GlStateManager.enableRescaleNormal();
        float f = 0.0625F;
        GlStateManager.scale(f, f, f);
        GlStateManager.enableAlpha();
        this.bindTexture(texture);

		model.render(te, 1);

        GlStateManager.popMatrix();
		
		MissileType mt = ((AimaggTELaunchPad)te).getMissileType();
		if (mt != null) {
			this.doMissileRender(te, mt, x+0.5, y+(4/16D), z+0.5, partialTick);
		}
	}

	public void doMissileRender(TileEntity te, MissileType mt, double x, double y, double z, float partialTick) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        float f = 0.0625F;
        GlStateManager.scale(f, f, f);
        GlStateManager.enableAlpha();
        this.bindTexture(mt.getEntityTexture());
        
        if (mt.useLargeModel()) {
            missileLargeModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
        } else {
            missileModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
        }
        
        GlStateManager.popMatrix();
	}
}
