package com.joshmanisdabomb.lcc.tileentity.model;

import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.SlabType;

public class ComputingModel extends Model {

    private final RendererModel module_top;
    private final RendererModel module_bottom;

    public ComputingModel() {
        textureWidth = 64;
        textureHeight = 256;

        module_top = new RendererModel(this);
        module_top.setRotationPoint(0.0F, 0.0F, 0.0F);
        module_top.addBox(-8.0F, -8.0F, -8.0F, 16, 8, 16);

        module_bottom = new RendererModel(this);
        module_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        module_bottom.addBox(-8.0F, 0, -8.0F, 16, 8, 16);
    }

    public void render(ComputingTileEntity te, float scale) {
        BlockState computing_block = te.getBlockState();
        if (computing_block.getBlock() != LCCBlocks.computing) return;
        SlabType module = computing_block.get(ComputingBlock.MODULES);
        if (module != SlabType.TOP) {
            float[] c = DyeColor.BLACK.getColorComponentValues();
            GlStateManager.color4f(c[0], c[1], c[2], 1.0F);
            module_top.setTextureOffset(0, 0);
            module_top.render(scale);
        }
        if (module != SlabType.BOTTOM) {
            float[] c = DyeColor.PINK.getColorComponentValues();
            GlStateManager.color4f(c[0], c[1], c[2], 1.0F);
            module_bottom.setTextureOffset(0, 96);
            module_bottom.render(scale);
        }
        GlStateManager.color4f(1, 1, 1, 1);
    }

}