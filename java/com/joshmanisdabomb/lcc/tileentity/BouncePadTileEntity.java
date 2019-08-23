package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BouncePadTileEntity extends TileEntity implements ITickableTileEntity {

    public float extension = 0.0F;

    public BouncePadTileEntity() {
        super(LCCTileEntities.bounce_pad);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            this.extension = Math.max(this.extension - Math.max(this.extension * 0.05F, 0.05F), 0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getExtensionModel() {
        return MathHelper.clamp(9-(int)Math.ceil(extension), 0, 8);
    }

    @OnlyIn(Dist.CLIENT)
    public float getExtensionOffset() {
        return (extension % 1F) * 0.0625F;
    }

}