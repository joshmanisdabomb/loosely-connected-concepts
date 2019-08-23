package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.BouncePadModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class BouncePadRenderer extends TileEntityRenderer<BouncePadTileEntity> {

    protected final BouncePadModel model = new BouncePadModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/bounce_pad.png");

    @Override
    public void render(BouncePadTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translated(x+0.5, y+0.5, z+0.5);
        GlStateManager.enableRescaleNormal();

        this.bindTexture(TEXTURE);
        model.render(te, 0.0625f);

        GlStateManager.popMatrix();
    }
}