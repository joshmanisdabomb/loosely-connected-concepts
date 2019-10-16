package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public abstract class OperatingSystem {

    protected final TerminalTileEntity terminal;
    protected final ComputingSession session;

    public OperatingSystem(TerminalTileEntity t, ComputingSession s) {
        this.terminal = t;
        this.session = s;
    }

    public abstract Type getType();

    public abstract void render(float partialTicks);

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return true;
    }

    public int getBackgroundColor() {
        return 0xFF222222;
    }

    public enum Type {
        BIOS(-1, BIOSOperatingSystem::new),
        CONSOLE(400, ConsoleOperatingSystem::new),
        GRAPHICAL(4000, GraphicalOperatingSystem::new);

        public final int size;
        public final BiFunction<TerminalTileEntity, ComputingSession, OperatingSystem> factory;

        Type(int size, BiFunction<TerminalTileEntity, ComputingSession, OperatingSystem> factory) {
            this.size = size;
            this.factory = factory;
        }

        public static Type from(String type) {
            return Arrays.stream(Type.values()).filter(t -> t.name().equalsIgnoreCase(type)).findFirst().orElseThrow(RuntimeException::new);
        }
    }

}
