package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.ComputingModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class ComputingRenderer extends TileEntityRenderer<ComputingTileEntity> {

    protected final ComputingModel model = new ComputingModel();
    public static final ResourceLocation CASING_TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_casing.png");

    public void render(ComputingTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translated(x+0.5, y+0.5, z+0.5);
        GlStateManager.enableRescaleNormal();

        this.bindTexture(CASING_TEXTURE);
        model.render(te, 0.0625f);

        GlStateManager.popMatrix();
    }

}
