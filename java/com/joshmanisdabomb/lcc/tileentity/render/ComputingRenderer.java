package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.ComputingModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.state.properties.SlabType;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingRenderer extends TileEntityRenderer<ComputingTileEntity> {

    private static final SlabType[] MODULE_SLABS = new SlabType[]{SlabType.TOP, SlabType.BOTTOM};

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
                if (te.getModuleType(module) != null) {
                    float[] c = te.getModuleColor(module).getColorComponentValues();
                    GlStateManager.color4f(c[0], c[1], c[2], 1.0F);
                    this.bindTexture(te.getModuleType(module).getTexture());
                    model.renderModule(te, 0.0625F, module);
                }
            }
        }
        GlStateManager.color4f(1, 1, 1, 1);

        GlStateManager.popMatrix();
    }

}
