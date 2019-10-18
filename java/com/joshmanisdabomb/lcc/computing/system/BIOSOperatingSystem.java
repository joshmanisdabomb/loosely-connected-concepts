package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.client.util.InputMappings;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

public class BIOSOperatingSystem extends LinedOperatingSystem {

    public BIOSOperatingSystem(ComputingSession cs) {
        super(cs);
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

    private void printError() {
        CompoundNBT seeks = cs.computer.state.getCompound("os_seeks");
        if (seeks.isEmpty()) {
            this.print(new TranslationTextComponent("computing.lcc.bios.no_os").getFormattedText());
        } else if (seeks.size() == 1) {
            String os = seeks.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
            this.print(new TranslationTextComponent("computing.lcc.bios.partial_os", new TranslationTextComponent("computing.lcc." + os).getFormattedText(), seeks.getInt(os)).getFormattedText());
        } else {
            for (String os : seeks.keySet()) {
                this.print(new TranslationTextComponent("computing.lcc.bios.partial_os.option", new TranslationTextComponent("computing.lcc." + os).getFormattedText(), seeks.getInt(os)).getFormattedText());
            }
            this.print(new TranslationTextComponent("computing.lcc.bios.partial_os.multiple").getFormattedText());
        }
        this.writeOutput(cs.computer.state);
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        super.render(ts, partialTicks, x, y);
    }

    @Override
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        cs.osLoad();
        this.printError();
        cs.sendState();
        return true;
    }
}
