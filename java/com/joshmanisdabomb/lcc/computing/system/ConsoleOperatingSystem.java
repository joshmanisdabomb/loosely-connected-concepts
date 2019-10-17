package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConsoleOperatingSystem extends OperatingSystem {

    public ConsoleOperatingSystem(ComputingSession cs) {
        super(cs);
    }

    @Override
    public Type getType() {
        return Type.CONSOLE;
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {

    }

}
