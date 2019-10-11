package com.joshmanisdabomb.lcc.gui;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.joshmanisdabomb.lcc.network.ComputerPowerPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class ComputerScreen extends ComputingScreen {

    public static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/computer.png");

    private SpriteButton buttonPower;

    public ComputerScreen(ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        this.xSize = 176;
        this.ySize = 178;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonPower = this.addButton(new SpriteButton(this.guiLeft + 77, this.guiTop + 58, this.container.module.powerState ? 110 : 88) {
            @Override
            public void onPress() {
                ComputerScreen.this.container.module.powerState = !ComputerScreen.this.container.module.powerState;
                this.ix = ComputerScreen.this.container.module.powerState ? 110 : 88;
                LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerPowerPacket(ComputerScreen.this.container.te.getWorld().getDimension().getType(), ComputerScreen.this.container.te.getPos(), ComputerScreen.this.playerInventory.player.getUniqueID(), ComputerScreen.this.container.location, ComputerScreen.this.container.module.powerState));
            }

            @Override
            public void renderToolTip(int x, int y) {
                ComputerScreen.this.renderTooltip(I18n.format("gui.lcc.computer." + (!ComputerScreen.this.container.module.powerState ? "on" : "off")), x, y);
            }
        });
        this.buttonPower.active = true;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        for (Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(mouseX, mouseY);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.container.module.getName().getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    private abstract class SpriteButton extends AbstractButton {

        public int ix;

        public SpriteButton(int x, int y, int ix) {
            super(x, y, 22, 22, "");
            this.ix = ix;
        }

        @Override
        public void renderButton(int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(GUI);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int blit = 0;
            if (!this.active) {
                blit += this.width * 2;
            } else if (this.isHovered()) {
                blit += this.width * 3;
            }

            this.blit(this.x, this.y, blit, 178, this.width, this.height);
            GlStateManager.enableBlend();
            this.blit(this.x, this.y, this.ix, 178, 22, 22);
            GlStateManager.disableBlend();
        }

    }

}
