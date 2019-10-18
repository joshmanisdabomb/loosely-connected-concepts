package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public abstract class LinedOperatingSystem extends OperatingSystem {

    protected String[] out = new String[10];

    public LinedOperatingSystem(ComputingSession cs) {
        super(cs);
    }

    @Override
    public void wake() {
        this.readOutput(cs.computer.state);
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        for (int i = 0; i < out.length; i++) {
            Minecraft.getInstance().fontRenderer.drawString(out[i], x + 5, y + 4 + (i * 11), 0xD5D5D5);
        }
    }

    protected void readOutput(CompoundNBT nbt) {
        if (nbt.contains("output", Constants.NBT.TAG_LIST)) this.out = nbt.getList("output", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
    }

    protected void writeOutput(CompoundNBT nbt) {
        ListNBT a = new ListNBT();
        for (int i = 0; i < out.length; i++) {
            a.add(new StringNBT(out[i] != null ? out[i] : ""));
        }
        nbt.put("output", a);
    }

    protected void print(String s) {
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
