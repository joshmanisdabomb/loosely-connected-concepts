package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public class BIOSOperatingSystem extends OperatingSystem {

    public BIOSOperatingSystem(ComputingSession cs) {
        super(cs);
    }

    @Override
    public Type getType() {
        return Type.BIOS;
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("computing.lcc.bios.no_os").getFormattedText(), x + 5, y + 4, 0xCCCCCC);
        Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("computing.lcc.bios.prompt").getFormattedText(), x + 5, y + 15, 0xCCCCCC);
    }

}
