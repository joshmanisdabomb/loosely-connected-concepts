package com.joshmanisdabomb.lcc.creative2;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

public interface Creative2GroupKey {

    @OnlyIn(Dist.CLIENT)
    default int getSelectionWidth() {
        return 2;
    }

    @OnlyIn(Dist.CLIENT)
    default int getSelectionHeight() {
        return 2;
    }

    @OnlyIn(Dist.CLIENT)
    float[] getSelectionColor();

    @OnlyIn(Dist.CLIENT)
    default float[] getSelectionHoverColor() {
        return new float[]{1.0F, 1.0F, 1.0F, 0.5019608F};
    }

    @OnlyIn(Dist.CLIENT)
    default Object getGroupKey(Set<?> groupKeys) {
        return this;
    }

}