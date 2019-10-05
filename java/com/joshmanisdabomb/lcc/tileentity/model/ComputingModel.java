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
        module_top.addBox(-8.0F, 0, -8.0F, 16, 8, 16);

        module_bottom = new RendererModel(this);
        module_bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        module_bottom.addBox(-8.0F, -8.0F, -8.0F, 16, 8, 16);
    }

    public void renderModule(ComputingTileEntity te, float scale, SlabType module) {
        boolean isTop = module == SlabType.TOP;
        RendererModel m = isTop ? module_top : module_bottom;
        m.setTextureOffset(0, isTop ? 0 : 96);
        m.render(scale);
    }

}