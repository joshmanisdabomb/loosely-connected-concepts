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

import java.util.List;

public abstract class LinedOperatingSystem extends OperatingSystem {

    protected String[] out;

    public LinedOperatingSystem(ComputingSession cs, int outputLength) {
        super(cs);
        out = new String[outputLength];
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

    @OnlyIn(Dist.CLIENT)
    public void write(String s) {
        List<String> lines = LCCFonts.FIXED_WIDTH.get().listFormattedStringToWidth(s, 230);
        int k = out.length - 1;
        for (int i = 0; i < lines.size(); i++) {
            if (out[k] == null) out[k] = "";
            out[k] += lines.get(i);
            if (i < lines.size() - 1) scroll();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void writet(String key, Object... format) {
        write(new TranslationTextComponent(key, format).getFormattedText());
    }

    @OnlyIn(Dist.CLIENT)
    public void scroll(int lines) {
        if (lines < out.length) {
            System.arraycopy(out, lines, out, 0, out.length - lines);
            for (int i = 0; i < lines; i++) {
                out[out.length - i - 1] = null;
            }
        } else {
            clear();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void scroll() {
        this.scroll(1);
    }

    @OnlyIn(Dist.CLIENT)
    public void print(String s) {
        write(s);
        scroll();
    }

    @OnlyIn(Dist.CLIENT)
    public void printt(String key, Object... format) {
        print(new TranslationTextComponent(key, format).getFormattedText());
    }

    @OnlyIn(Dist.CLIENT)
    public void clear() {
        out = new String[out.length];
    }

}
