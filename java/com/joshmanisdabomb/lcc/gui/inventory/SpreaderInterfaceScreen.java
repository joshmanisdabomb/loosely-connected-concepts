package com.joshmanisdabomb.lcc.gui.inventory;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class SpreaderInterfaceScreen extends ContainerScreen<SpreaderInterfaceContainer> {

    public static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/spreader_interface.png");

    private DyeColor tab = DyeColor.WHITE;

    private final SpreaderCapability newSettings;
    private final SpreaderCapability oldSettings;

    private HashMap<DyeColor, Widget[]> tabWidgets = new HashMap<>();

    private SpriteButton buttonConfirm;
    private SpriteButton buttonCancel;

    public SpreaderInterfaceScreen(SpreaderInterfaceContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);

        this.oldSettings = container.oldSettings;
        this.newSettings = new SpreaderCapability().clone(this.oldSettings);

        this.xSize = 230;
        this.ySize = 231;
    }

    @Override
    protected void init() {
        super.init();
        this.buttonConfirm = this.addButton(new SpriteButton(this.guiLeft + 167, this.guiTop + 111, 88) {
            @Override
            public void onPress() {

            }

            @Override
            public void renderToolTip(int x, int y) {
                SpreaderInterfaceScreen.this.renderTooltip(I18n.format("gui.done"), x, y);
            }
        });
        this.buttonConfirm.active = false;
        this.buttonCancel = this.addButton(new SpriteButton(this.guiLeft + 193, this.guiTop + 111, 110) {
            @Override
            public void onPress() {
                SpreaderInterfaceScreen.this.minecraft.player.closeScreen();
            }

            @Override
            public void renderToolTip(int x, int y) {
                SpreaderInterfaceScreen.this.renderTooltip(I18n.format("gui.cancel"), x, y);
            }
        });
        this.buttonCancel.active = true;
        for (DyeColor color : DyeColor.values()) {
            Widget[] widgets = new Widget[8];

            widgets[0] = this.addButton(new SmallCheckboxButton(this.guiLeft + 10, this.guiTop + 37, this.newSettings.isEnabled(color)) {

                @Override
                public void onPress() {
                    super.onPress();
                    SpreaderInterfaceScreen.this.newSettings.enabled.put(color, this.func_212942_a());
                }

            });

            widgets[1] = this.addButton(new SmallCheckboxButton(this.guiLeft + 63, this.guiTop + 52, this.newSettings.isEater(color)) {

                @Override
                public void onPress() {
                    super.onPress();
                    SpreaderInterfaceScreen.this.newSettings.eating.put(color, this.func_212942_a());
                }

            });

            widgets[2] = this.addButton(new SmallCheckboxButton(this.guiLeft + 63, this.guiTop + 65, this.newSettings.throughGround(color)) {

                @Override
                public void onPress() {
                    super.onPress();
                    SpreaderInterfaceScreen.this.newSettings.throughGround.put(color, this.func_212942_a());
                }

            });

            widgets[3] = this.addButton(new SmallCheckboxButton(this.guiLeft + 63, this.guiTop + 78, this.newSettings.throughLiquid(color)) {

                @Override
                public void onPress() {
                    super.onPress();
                    SpreaderInterfaceScreen.this.newSettings.throughLiquid.put(color, this.func_212942_a());
                }

            });

            widgets[4] = this.addButton(new SmallCheckboxButton(this.guiLeft + 63, this.guiTop + 91, this.newSettings.throughAir(color)) {

                @Override
                public void onPress() {
                    super.onPress();
                    SpreaderInterfaceScreen.this.newSettings.throughAir.put(color, this.func_212942_a());
                }

            });

            widgets[5] = this.addButton(new IntSlider(this.guiLeft + 125, this.guiTop + 55, this.newSettings.getSpeedLevel(color), 0, 11) {

                @Override
                protected void updateMessage() {
                    this.setMessage(I18n.format("block.lcc.spreader_interface.speed.value", SpreaderInterfaceScreen.this.newSettings.getMaxTickSpeed(color), SpreaderInterfaceScreen.this.newSettings.getMinTickSpeed(color)));
                }

                @Override
                protected void applyValue() {
                    SpreaderInterfaceScreen.this.newSettings.speedLevel.put(color, this.getSpreaderValue());
                }

            });

            widgets[6] = this.addButton(new IntSlider(this.guiLeft + 125, this.guiTop + 70, this.newSettings.getDamageLevel(color), 0, 6) {

                @Override
                protected void updateMessage() {
                    this.setMessage(I18n.format("block.lcc.spreader_interface.damage.value", SpreaderInterfaceScreen.this.newSettings.getDamageLevel(color) * 0.5F));
                }

                @Override
                protected void applyValue() {
                    SpreaderInterfaceScreen.this.newSettings.damageLevel.put(color, this.getSpreaderValue());
                }

            });

            widgets[7] = this.addButton(new IntSlider(this.guiLeft + 125, this.guiTop + 85, this.newSettings.getDecayLevel(color), 0, 15) {

                @Override
                protected void updateMessage() {
                    this.setMessage(I18n.format("block.lcc.spreader_interface.decay.value", SpreaderInterfaceScreen.this.newSettings.getDecayPercentage(color)));
                }

                @Override
                protected void applyValue() {
                    SpreaderInterfaceScreen.this.newSettings.decayLevel.put(color, this.getSpreaderValue());
                }

            });

            for (int i = 0; i < 8; i++) {
                widgets[i].visible = color == tab;
            }
            this.tabWidgets.put(color, widgets);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if (mouseX >= this.guiLeft + 202 && mouseX <= this.guiLeft + 202 + 16 && mouseY >= this.guiTop + 10 && mouseY <= this.guiTop + 10 + 16) {
            this.renderTooltip(new ItemStack(LCCBlocks.spreaders.get(tab)), mouseX, mouseY);
        }
        for (Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(mouseX, mouseY);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 8.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.cost"), 8.0F, 118, 4210752);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.enabled"), 27, 39.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.speed"), 120 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.speed")), 57.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.damage"), 120 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.damage")), 72.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.decay"), 120 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.decay")), 87.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.eating"), 59 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.eating")), 54.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.throughGround"), 59 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.throughGround")), 67.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.throughLiquid"), 59 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.throughLiquid")), 80.5F, 0);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.throughAir"), 59 - this.font.getStringWidth(I18n.format("block.lcc.spreader_interface.throughAir")), 93.5F, 0);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.changeDrawingColor(tab);
        this.blit(this.guiLeft + 7, this.guiTop + 34, 7, 34, 216, 72);

        for (DyeColor color : DyeColor.values()) {
            this.changeDrawingColor(color);
            if (color == tab) {
                this.blit(this.guiLeft+8+(color.getId() * 10), this.guiTop + 21, 240, 0, 10, 14);
            } else {
                this.blit(this.guiLeft+8+(color.getId() * 10), this.guiTop + 21, 230, 0, 10, 13);
            }
        }

        //render items
        this.changeDrawingColor(null);
        this.itemRenderer.zLevel = 200.0F;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translatef(0.5F, 0.5F, 32.0F);

        this.itemRenderer.renderItemIntoGUI(new ItemStack(LCCBlocks.spreaders.get(tab)), this.guiLeft + 202, this.guiTop + 10);
        //this.itemRenderer.renderItemOverlays(Minecraft.getInstance().fontRenderer, new ItemStack(Items.EMERALD, Integer.MAX_VALUE), this.guiLeft + 42, this.guiTop + 109);

        GlStateManager.translatef(-0.5F, -0.5F, -32.0F);
        RenderHelper.enableStandardItemLighting();
        this.itemRenderer.zLevel = 0.0F;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.changeTabs(mouseX, mouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.changeTabs(mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDX, double mouseDY) {
        this.changeTabs(mouseX, mouseY);
        if (this.getFocused() != null && this.isDragging() && button == 0 && this.getFocused().mouseDragged(mouseX, mouseY, button, mouseDX, mouseDY)) return true;
        return super.mouseDragged(mouseX, mouseY, button, mouseDX, mouseDY);
    }

    private void changeTabs(double mouseX, double mouseY) {
        for (DyeColor color : DyeColor.values()) {
            if (mouseX >= this.guiLeft + 8 + (color.getId() * 10) && mouseX < this.guiLeft + 18 + (color.getId() * 10) && mouseY >= this.guiTop + 21 && mouseY < this.guiTop + 35) {
                if (color != tab) {
                    for (int i = 0; i < 8; i++) {
                        this.tabWidgets.get(tab)[i].visible = false;
                        this.tabWidgets.get(color)[i].visible = true;
                    }
                    tab = color;
                }
            }
        }
    }

    private void changeDrawingColor(DyeColor color) {
        if (color != null) {
            float[] c = color.getColorComponentValues();
            GlStateManager.color4f((c[0] + 0.2F) / 1.2F, (c[1] + 0.2F) / 1.2F, (c[2] + 0.2F) / 1.2F, 1.0F);
        } else {
            GlStateManager.color4f(0.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    private abstract class SpriteButton extends AbstractButton {

        private final int ix;

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

            this.blit(this.x, this.y, blit, 231, this.width, this.height);
            this.blit(this.x, this.y, this.ix, 231, 22, 22);
        }

    }

    private abstract class IntSlider extends AbstractSlider {

        private final int min;
        private final int max;

        protected IntSlider(int x, int y, double initialValue, int min, int max) {
            super(null, x, y, 90, 12, initialValue);
            this.min = min;
            this.max = max;
            this.updateMessage();
        }

        @Override
        public void renderButton(int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(GUI);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blit(this.x, this.y, 132, 231, this.width, this.height);
            this.blit(this.x + (int)(this.value * (double)(this.width - 8)), this.y, this.isHovered() ? 140 : 132, 243, 8, 12);

            this.drawCenteredString(SpreaderInterfaceScreen.this.font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getFGColor() | MathHelper.ceil(this.alpha * 255.0F) << 24);
        }

        @Override
        protected void renderBg(Minecraft mc, int p_renderBg_2_, int partialTicks) {

        }

        public int getSpreaderValue() {
            if (this.value <= 0.02F) return min;
            if (this.value >= 0.98F) return max;
            return MathHelper.floor(this.value * ((max-min)-1)) + min + 1;
        }

    }

    public abstract class SmallCheckboxButton extends CheckboxButton {

        public SmallCheckboxButton(int x, int y, boolean initialValue) {
            super(x, y, 12, 12, "", initialValue);
        }

        @Override
        public void renderButton(int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(GUI);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blit(this.x, this.y, 148, 243, this.width, this.height);
            if (this.func_212942_a()) this.blit(this.x, this.y, 160, 243, this.width, this.height);
        }
    }

}
