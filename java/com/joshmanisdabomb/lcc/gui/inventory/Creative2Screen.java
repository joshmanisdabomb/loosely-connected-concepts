package com.joshmanisdabomb.lcc.gui.inventory;

import com.google.common.collect.ImmutableList;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.group.Creative2Group;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Map;

public class Creative2Screen extends CreativeScreen {

    protected final Creative2Group group;

    protected static boolean refreshed = false;

    public Creative2Screen(Creative2Group group, PlayerEntity player) {
        super(player);
        this.group = group;
    }

    @Override
    protected void init() {
        super.init();
        this.setCurrentCreativeTab(group);
        refreshed = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.searchField.x = this.guiLeft + 169 - (this.searchField.getText().isEmpty() ? 8 : this.searchField.getWidth());
        if (!refreshed) {
            this.container.itemList.set(10, new ItemStack(Blocks.COBBLESTONE));
            this.container.scrollTo(0.0F);
            refreshed = true;
        }
    }

    @Override
    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        super.resize(p_resize_1_, p_resize_2_, p_resize_3_);
        refreshed = container.itemList.isEmpty();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        this.font.drawString(I18n.format(group.getTranslationKey() + (this.searchField.getText().isEmpty() ? "" : ".short")), 8.0F, 6.0F, group.getLabelColor());

        if (this.searchField.getText().isEmpty()) {
            this.minecraft.getTextureManager().bindTexture(group.getBackgroundImage());
            for (Slot slot : this.container.inventorySlots) {
                if (slot.inventory != this.minecraft.player.inventory) {
                    Map<? extends Enum<?>, Item> group = this.group.getGroup(slot.getStack().getItem().asItem());
                    if (group != null) {
                        this.blit(slot.xPos, slot.yPos + 10, 0, this.ySize + 34, 16, 6);
                    }
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        this.minecraft.getTextureManager().bindTexture(group.getBackgroundImage());
        if (this.searchField.getText().isEmpty()) {
            this.blit(this.guiLeft + 158, this.guiTop + 4, 0, this.ySize + 12, 12, 12);
        } else {
            this.blit(this.guiLeft + 167 - this.searchField.getWidth(), this.guiTop + 4, 0, this.ySize, this.searchField.getWidth() + 3, 12);
        }
        this.searchField.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void setCurrentCreativeTab(ItemGroup tab) {
        if (tab != group) {
            CreativeScreen creative = new CreativeScreen(LCC.proxy.getClientPlayer());
            creative.init(this.minecraft, this.minecraft.getMainWindow().getScaledWidth(), this.minecraft.getMainWindow().getScaledHeight());
            creative.setCurrentCreativeTab(tab);
            this.minecraft.displayGuiScreen(creative);
        } else {
            super.setCurrentCreativeTab(tab);
            this.container.itemList.clear();
            group.fillNoSearch(this.container.itemList);
        }
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (this.searchField.getText().isEmpty() && this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack() && this.hoveredSlot.inventory != this.minecraft.player.inventory) {
            Map<? extends Enum<?>, IItemProvider> g = group.getGroup(this.hoveredSlot.getStack().getItem().asItem());
            if (g != null) {
                ITextComponent count = new TranslationTextComponent("itemGroup.lcc.group.amount", g.size()).applyTextStyle(TextFormatting.DARK_GRAY);
                if (g.size() != 1) count.appendSibling(new TranslationTextComponent("itemGroup.lcc.group.amount.s"));
                this.renderTooltip(ImmutableList.of(new TranslationTextComponent(group.getGroupTranslationKey(g)).getFormattedText(), count.getFormattedText()), mouseX, mouseY, this.font);
                return;
            }
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        if (p_mouseReleased_5_ == 0) {
            double d0 = p_mouseReleased_1_ - (double)this.guiLeft;
            double d1 = p_mouseReleased_3_ - (double)this.guiTop;
            if (this.isMouseOverGroup(group, d0, d1)) {
                this.isScrolling = false;
                container.scrollTo(this.currentScroll = 0.0F);
                return false;
            }
        }
        return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean empty = this.searchField.getText().isEmpty();
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            if (!empty && (keyCode == 259 || keyCode == 261) && this.searchField.getText().isEmpty()) {
                this.container.itemList.clear();
                group.fillNoSearch(this.container.itemList);
                container.scrollTo(this.currentScroll = 0.0F);
            }
            return true;
        }
        return false;
    }

}
