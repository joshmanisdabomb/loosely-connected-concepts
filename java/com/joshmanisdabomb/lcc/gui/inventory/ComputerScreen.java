package com.joshmanisdabomb.lcc.gui.inventory;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.joshmanisdabomb.lcc.network.ComputerPowerPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class ComputerScreen extends ContainerScreen<ComputingContainer> {

    public static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/computer.png");

    private FunctionalSpriteButton buttonPower;

    public ComputerScreen(ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        this.xSize = 176;
        this.ySize = 178;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonPower = this.addButton(new FunctionalSpriteButton(() -> {
            ComputingModule m = ComputerScreen.this.container.module;
            m.powerState = !m.powerState;
            m.session = m.getSession(ComputingSession::boot);
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerPowerPacket(container.te.getWorld().getDimension().getType(), container.te.getPos(), playerInventory.player.getUniqueID(), container.location, m.powerState));
            return m.powerState ? 110 : 88;
        }, (x,y) -> this.renderTooltip(I18n.format("gui.lcc.computer." + (!container.module.powerState ? "on" : "off")), x, y), GUI,this.guiLeft + 77, this.guiTop + 58, this.container.module.powerState ? 110 : 88, 0, 178));
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

}
