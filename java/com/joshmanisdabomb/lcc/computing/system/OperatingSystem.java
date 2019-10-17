package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.function.Function;

public abstract class OperatingSystem {

    protected final ComputingSession cs;

    public OperatingSystem(ComputingSession cs) {
        this.cs = cs;
    }
    public abstract Type getType();

    /** When the computer is first switched on via power button. **/
    public void boot() {

    }

    /** When the computer is first loaded into memory. Other tile entities may not be loaded in yet. **/
    public void wake() {

    }

    @OnlyIn(Dist.CLIENT)
    public abstract void render(TerminalSession ts, float partialTicks, int x, int y);

    @OnlyIn(Dist.CLIENT)
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBackgroundColor(TerminalSession ts) {
        return 0xFF222222;
    }

    public enum Type {
        BIOS(-1, BIOSOperatingSystem::new),
        CONSOLE(400, ConsoleOperatingSystem::new),
        GRAPHICAL(4000, GraphicalOperatingSystem::new);

        public final int size;
        public final Function<ComputingSession, OperatingSystem> factory;

        Type(int size, Function<ComputingSession, OperatingSystem> factory) {
            this.size = size;
            this.factory = factory;
        }

        public static Type from(String type) {
            return Arrays.stream(Type.values()).filter(t -> t.name().equalsIgnoreCase(type)).findFirst().orElseThrow(RuntimeException::new);
        }
    }

}
