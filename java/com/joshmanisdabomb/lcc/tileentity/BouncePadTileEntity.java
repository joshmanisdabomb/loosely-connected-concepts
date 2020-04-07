package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BouncePadTileEntity extends TileEntity implements ITickableTileEntity {

    public float extension = 0.0F;
    public float prevExtension = extension;

    public BouncePadTileEntity() {
        super(LCCTileEntities.bounce_pad);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            this.prevExtension = this.extension;
            this.extension = Math.max(this.extension - Math.max(this.extension * 0.07F, 0.07F), 0);
        }
    }

}