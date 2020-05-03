package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class ComputingRenderer extends TileEntityRenderer<ComputingTileEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_light.png");

    //protected final ComputingModel model = new ComputingModel();

    public ComputingRenderer(TileEntityRendererDispatcher terd) {
        super(terd);
    }

    @Override
    public void render(ComputingTileEntity te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        /*GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translated(0.5, 0.5, 0.5);
        GlStateManager.enableRescaleNormal();

        BlockState computing_block = te.getBlockState();
        if (computing_block.getBlock() != LCCBlocks.computing) return;
        SlabType setup = computing_block.get(ComputingBlock.MODULE);
        for (SlabType location : MODULE_SLABS) {
            if (setup != flip(location)) {
                ComputingModule m = te.getModule(location);
                if (m != null) {
                    float[] c = m.color.getColorComponentValues();
                    GlStateManager.color4f(c[0], c[1], c[2], 1.0F);
                    //this.bindTexture(m.type.getTexture());
                    model.renderModule(te, 0.0625F, location);

                    //lights
                    if (m.isPowered()) {
                        GlStateManager.color4f(1, 1, 1, 1);
                        //GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                        GlStateManager.depthMask(false);
                        //this.setLightmapDisabled(true);

                        //this.bindTexture(COMPUTER_LIGHTS);
                        if (m.isPowered()) model.renderPowerLight(te, 0.0625F, location, m.isReading());

                        //this.setLightmapDisabled(false);
                        GlStateManager.depthMask(true);
                        GlStateManager.disableBlend();
                    }
                }
            }
        }

        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.popMatrix();*/
    }

}
