package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConsoleOperatingSystem extends OperatingSystem {

    public ConsoleOperatingSystem(TerminalTileEntity t, ComputingSession m) {
        super(t, m);
    }

    @Override
    public Type getType() {
        return Type.CONSOLE;
    }

    @Override
    public void render(float partialTicks) {

    }

}
