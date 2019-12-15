package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.registry.LCCFonts;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class LinedOperatingSystem extends OperatingSystem {

    public static final int CONSOLE_WIDTH = 230;

    protected String[] out;

    protected ArrayList<String> buffer;

    public LinedOperatingSystem(ComputingSession cs, int outputLength) {
        super(cs);
        out = new String[outputLength];
    }

    @Override
    public void wake() {
        this.readOutput(cs.getState());
        this.readBuffer(cs.getState());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        for (int i = 0; i < out.length; i++) {
            LCCFonts.FIXED_WIDTH.get().drawString(out[i], x + 5, y + 4 + (i * 11), 0xD5D5D5);
        }
    }

    protected void readOutput(CompoundNBT nbt) {
        if (nbt.contains(this.getType() + ".output", Constants.NBT.TAG_LIST)) this.out = nbt.getList(this.getType() + ".output", Constants.NBT.TAG_STRING).stream().map(INBT::getString).toArray(String[]::new);
    }

    protected void writeOutput(CompoundNBT nbt) {
        ListNBT a = new ListNBT();
        for (int i = 0; i < out.length; i++) {
            a.add(new StringNBT(out[i] != null ? out[i] : ""));
        }
        nbt.put(this.getType() + ".output", a);
    }

    protected void readBuffer(CompoundNBT nbt) {
        if (nbt.contains(this.getType() + ".buffer", Constants.NBT.TAG_LIST)) this.buffer = nbt.getList(this.getType() + ".buffer", Constants.NBT.TAG_STRING).stream().map(INBT::getString).collect(Collectors.toCollection(ArrayList::new));
    }

    protected void writeBuffer(CompoundNBT nbt) {
        ListNBT a = new ListNBT();
        nbt.remove(this.getType() + ".buffer");
        if (buffer != null) {
            for (int i = 0; i < buffer.size(); i++) {
                a.add(new StringNBT(buffer.get(i) != null ? buffer.get(i) : ""));
            }
            nbt.put(this.getType() + ".buffer", a);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void write(String s) {
        List<String> lines = LCCFonts.FIXED_WIDTH.get().listFormattedStringToWidth(s, CONSOLE_WIDTH);
        if (buffer == null) {
            int k = out.length - 1;
            for (int i = 0; i < lines.size(); i++) {
                if (out[k] == null) out[k] = "";
                out[k] += lines.get(i);
                if (i < lines.size() - 1) scroll();
            }
        } else {
            if (buffer.isEmpty()) buffer.add("");
            int k = buffer.size() - 1;
            for (int i = 0; i < lines.size(); i++) {
                String b = buffer.get(k);
                buffer.set(k, b + lines.get(i));
                if (i < lines.size() - 1) scroll();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void writet(String key, Object... format) {
        write(new TranslationTextComponent(key, format).getFormattedText());
    }

    @OnlyIn(Dist.CLIENT)
    protected void scroll(int lines) {
        if (buffer == null) {
            if (lines < out.length) {
                System.arraycopy(out, lines, out, 0, out.length - lines);
                for (int i = 0; i < lines; i++) {
                    out[out.length - i - 1] = null;
                }
            } else {
                clear();
            }
        } else {
            for (int i = 0; i < lines; i++) {
                buffer.add("");
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void scroll() {
        this.scroll(1);
    }

    @OnlyIn(Dist.CLIENT)
    protected void print(String s) {
        write(s);
        scroll();
    }

    @OnlyIn(Dist.CLIENT)
    protected void printt(String key, Object... format) {
        print(new TranslationTextComponent(key, format).getFormattedText());
    }

    @OnlyIn(Dist.CLIENT)
    protected void clear() {
        out = new String[out.length];
    }

    @OnlyIn(Dist.CLIENT)
    protected void startBuffer() {
        buffer = new ArrayList<>();
    }

    @OnlyIn(Dist.CLIENT)
    protected ArrayList<String> endBuffer(boolean output) {
        ArrayList<String> b = buffer;
        buffer = null;
        if (output) {
            write(String.join("\n", b));
        }
        return b;
    }

}
