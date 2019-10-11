package com.joshmanisdabomb.lcc.misc;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ComputerSession {

    public ComputerSession(ComputingModule module) {

    }

    @OnlyIn(Dist.CLIENT)
    public int getBackgroundColor() {
        return 0xFFFFFFFF;
    }

}
