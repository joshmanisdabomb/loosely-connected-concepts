package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.joshmanisdabomb.lcc.gui.TerminalScreen.GUI;

@OnlyIn(Dist.CLIENT)
public class GraphicalOperatingSystem extends OperatingSystem {

    public GraphicalOperatingSystem(ComputingSession cs) {
        super(cs);
    }

    @Override
    public Type getType() {
        return Type.GRAPHICAL;
    }

    @Override
    public void render(TerminalSession ts, float partialTicks, int x, int y) {
        GlStateManager.color4f(0, 0.5F, 1, 1);
        Minecraft.getInstance().getTextureManager().bindTexture(GUI);
        this.blit(x, y + 102, 0, 231, 240, 13);
        GlStateManager.color4f(1, 1, 1, 1);

        Minecraft.getInstance().fontRenderer.drawString(new TranslationTextComponent("computing.lcc.graphical.time", Minecraft.getInstance().world.getDayTime() / 24000L, this.formatTime(Minecraft.getInstance().world.getDayTime())).getFormattedText(), x + 4, y + 105, 0x0);
    }

    @Override
    public int getBackgroundColor(TerminalSession ts) {
        return 0xFFFFFFFF;
    }

    private String formatTime(long time) {
        time = time % 24000;
        long hours = (time / 1000) + 6;
        long minutes = ((time % 1000) * 60) / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }

}
