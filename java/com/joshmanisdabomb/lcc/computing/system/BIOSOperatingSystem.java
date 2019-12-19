package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BIOSOperatingSystem extends LinedOperatingSystem {

    public BIOSOperatingSystem(ComputingSession cs) {
        super(cs, 10);
    }

    @Override
    public Type getType() {
        return Type.BIOS;
    }

    @Override
    public void boot() {
        super.boot();
        this.printError();
    }

    @Override
    public void wake() {
        super.wake();
    }

    private void printError() {
        CompoundNBT seeks = cs.getState().getCompound("os_seeks");
        this.scroll();
        if (seeks.isEmpty()) {
            this.writet("computing.lcc.bios.no_os");
        } else if (seeks.size() == 1) {
            String os = seeks.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
            this.writet("computing.lcc.bios.partial_os", new TranslationTextComponent("computing.lcc." + os).getFormattedText(), seeks.getInt(os));
        } else {
            for (String os : seeks.keySet()) {
                this.printt("computing.lcc.bios.partial_os.option", new TranslationTextComponent("computing.lcc." + os).getFormattedText(), seeks.getInt(os));
            }
            this.writet("computing.lcc.bios.partial_os.multiple");
        }
        this.writeOutput(cs.getState());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        super.render(ts, partialTicks, x, y);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        ts.blockInput = true;
        cs.osLoad();
        this.printError();
        cs.sendState();
        return true;
    }

    @Override
    public void processWork(ListNBT workQueue) {

    }

}
