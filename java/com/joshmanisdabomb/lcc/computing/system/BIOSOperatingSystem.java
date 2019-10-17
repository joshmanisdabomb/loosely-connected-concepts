package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class BIOSOperatingSystem extends OperatingSystem {

    public BIOSOperatingSystem(ComputingSession cs) {
        super(cs);
    }

    private final String[] out = new String[10];

    @Override
    public Type getType() {
        return Type.BIOS;
    }

    @Override
    public void boot() {
        CompoundNBT partitions = cs.computer.state.getCompound("partitions");
        if (partitions.isEmpty()) {
            this.print(new TranslationTextComponent("computing.lcc.bios.no_os").getFormattedText());
        } else {
            this.print(new TranslationTextComponent("computing.lcc.bios.no_os").getFormattedText());
        }
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        for (int i = 0; i < out.length; i++) {
            Minecraft.getInstance().fontRenderer.drawString(out[i], x + 5, y + 4 + (i * 11), 0xD5D5D5);
        }
    }

    private void print(String s) {
        List<String> lines = Minecraft.getInstance().fontRenderer.listFormattedStringToWidth(s, 230);
        if (lines.size() < out.length) {
            System.arraycopy(out, lines.size(), out, 0, out.length - lines.size());
        }
        for (int i = 0; i < out.length; i++) {
            int key = lines.size() - (out.length - i);
            if (key < 0) continue;
            out[i] = lines.get(key);
        }
    }

}
