package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.ComputingModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingRenderer extends TileEntityRenderer<ComputingTileEntity> {

    private static final SlabType[] MODULE_SLABS = new SlabType[]{SlabType.TOP, SlabType.BOTTOM};

    public static final ResourceLocation COMPUTER_LIGHTS = new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_light.png");

    protected final ComputingModel model = new ComputingModel();

    public void render(ComputingTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translated(x+0.5, y+0.5, z+0.5);
        GlStateManager.enableRescaleNormal();

        BlockState computing_block = te.getBlockState();
        if (computing_block.getBlock() != LCCBlocks.computing) return;
        SlabType setup = computing_block.get(ComputingBlock.MODULE);
        for (SlabType module : MODULE_SLABS) {
            if (setup != flip(module)) {
                ComputingTileEntity.ComputingModule m = te.getModule(module);
                if (m != null) {
                    float[] c = m.color.getColorComponentValues();
                    GlStateManager.color4f(c[0], c[1], c[2], 1.0F);
                    this.bindTexture(m.type.getTexture());
                    model.renderModule(te, 0.0625F, module);

                    //lights
                    if (m.isPowered()) {
                        GlStateManager.color4f(1, 1, 1, 1);
                        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                        GlStateManager.depthMask(false);
                        this.setLightmapDisabled(true);

                        this.bindTexture(COMPUTER_LIGHTS);
                        if (m.isPowered()) model.renderPowerLight(te, 0.0625F, module, m.isReading());

                        this.setLightmapDisabled(false);
                        GlStateManager.depthMask(true);
                        GlStateManager.disableBlend();
                    }
                }
            }
        }

        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.popMatrix();
    }

}
