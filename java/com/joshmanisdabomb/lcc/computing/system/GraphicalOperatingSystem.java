package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraphicalOperatingSystem extends OperatingSystem {

    public GraphicalOperatingSystem(TerminalTileEntity t, ComputingSession s) {
        super(t, s);
    }

    @Override
    public Type getType() {
        return Type.GRAPHICAL;
    }

    @Override
    public void render(float partialTicks) {

    }

    @Override
    public int getBackgroundColor() {
        return 0xFFFFFFFF;
    }

}
