package com.joshmanisdabomb.lcc.creative2;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface Creative2Category {

    @OnlyIn(Dist.CLIENT)
    default int getGroupColor() {
        return 0xFF5B5B5B;
    }

    @OnlyIn(Dist.CLIENT)
    int getSortValue();

}
