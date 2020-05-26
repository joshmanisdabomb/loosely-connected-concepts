package com.joshmanisdabomb.lcc.gui.inventory;

import com.google.common.collect.ImmutableList;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.creative2.Creative2Category;
import com.joshmanisdabomb.lcc.creative2.Creative2Group;
import com.joshmanisdabomb.lcc.creative2.Creative2GroupKey;
import com.joshmanisdabomb.lcc.misc.ExtendedDyeColor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Creative2Screen extends CreativeScreen {

    protected final Creative2Group group;

    protected static boolean refreshed = false;

    private final ArrayList<Map<? extends Enum<?>, ? extends IItemProvider>> groupMark = new ArrayList<>();

    private int hoveredSelector = -1;

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
            this.container.scrollTo(this.currentScroll = 0.0F);
            refreshed = true;
        }
    }

    @Override
    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        super.resize(p_resize_1_, p_resize_2_, p_resize_3_);
        refreshed = container.itemList.isEmpty();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        this.minecraft.getTextureManager().bindTexture(group.getBackgroundImage());
        if (this.searchField.getText().isEmpty()) {
            this.blit(this.guiLeft + 158, this.guiTop + 4, 0, this.ySize + 12, 12, 12);

            groupMark.clear();
            for (Slot slot : this.container.inventorySlots) {
                if (slot.inventory != this.minecraft.player.inventory) {
                    Map<? extends Enum<?>, Item> group = this.group.getGroup(slot.getStack().getItem().asItem());
                    if (group != null) {
                        if (!groupMark.contains(group)) {
                            this.drawSelectors(group, slot, this.guiLeft + slot.xPos, this.guiTop + slot.yPos, mouseX, mouseY, 1.0F);

                            groupMark.add(group);
                        } else {
                            Creative2Category category = this.group.getCategory(slot.getStack());
                            this.fillGradient(this.guiLeft + slot.xPos, this.guiTop + slot.yPos, this.guiLeft + slot.xPos + 16, this.guiTop + slot.yPos + 16, category.getGroupColor(), category.getGroupColor());
                        }
                    }
                }
            }
        } else {
            this.blit(this.guiLeft + 167 - this.searchField.getWidth(), this.guiTop + 4, 0, this.ySize, this.searchField.getWidth() + 3, 12);
        }
        this.searchField.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        this.font.drawString(I18n.format(group.getTranslationKey() + (this.searchField.getText().isEmpty() ? "" : ".short")), 8.0F, 6.0F, group.getLabelColor());

        hoveredSelector = -1;
        if (this.searchField.getText().isEmpty()) {
            this.minecraft.getTextureManager().bindTexture(group.getBackgroundImage());

            groupMark.clear();
            for (Slot slot : this.container.inventorySlots) {
                if (slot.inventory != this.minecraft.player.inventory) {
                    Map<? extends Enum<?>, Item> group = this.group.getGroup(slot.getStack().getItem().asItem());
                    if (group != null && !groupMark.contains(group)) {
                        int xOffset = this.group.expandedGroups.contains(group) ? 6 : 0;
                        this.blit(slot.xPos + 10, slot.yPos + 10, xOffset, this.ySize + 34, 6, 6);

                        RenderSystem.enableBlend();
                        int h = this.drawSelectors(group, slot, slot.xPos, slot.yPos, mouseX, mouseY, 0.5F);

                        if (slot == hoveredSlot && hoveredSelector == -1) {
                            int slotColor = super.getSlotColor(slot.slotNumber);
                            this.fillGradient(slot.xPos, slot.yPos + h, slot.xPos + 16, slot.yPos + 16, slotColor, slotColor);
                            this.blit(slot.xPos + 10, slot.yPos + 10, xOffset, this.ySize + 28, 6, 6);
                        }
                        RenderSystem.disableBlend();

                        groupMark.add(group);
                    }
                }
            }
        }
    }

    private int drawSelectors(Map<? extends Enum<?>, Item> group, Slot slot, int xPos, int yPos, int mouseX, int mouseY, float alpha) {
        Enum<?>[] keys = group.keySet().toArray(new Enum[0]);

        //Convert DyeColor to ExtendedDyeColor.
        if (Arrays.stream(keys).allMatch(k -> k instanceof DyeColor)) {
            keys = Arrays.stream(keys).map(d -> ExtendedDyeColor.CompoundDyeColor.get((DyeColor)d)).toArray(Enum<?>[]::new);
        }

        //Display group items with recognised implementations.
        if (Arrays.stream(keys).allMatch(k -> k instanceof Creative2GroupKey)) {
            int y = 0;
            Creative2GroupKey k = null;
            for (int i = 0; i < keys.length; i++) {
                k = (Creative2GroupKey)keys[i];
                int x = (i * k.getSelectionWidth()) % 16;
                y = (i / (16/k.getSelectionWidth())) * k.getSelectionHeight();

                float[] color = k.getSelectionColor();
                RenderSystem.color4f(color[0], color[1], color[2], alpha);

                this.blit(xPos + x, yPos + y, 0, this.ySize + 24, 2, 2);

                if (this.minecraft.player.inventory.getItemStack().isEmpty() && mouseX >= this.guiLeft + slot.xPos + x && mouseX <= this.guiLeft + slot.xPos + x + 1 && mouseY >= this.guiTop + slot.yPos + y && mouseY <= this.guiTop + slot.yPos + y + 1) {
                    hoveredSelector = i;

                    float[] hcolor = k.getSelectionHoverColor();
                    RenderSystem.color4f(hcolor[0], hcolor[1], hcolor[2], hcolor[3]);

                    this.group.group_items.put(group, i);

                    ItemStack newStack = new ItemStack(group.get(k.getGroupKey(group.keySet())), 1);
                    this.container.getSlot(slot.slotNumber).putStack(newStack);
                    this.container.itemList.set(this.getListIndex(slot.getSlotIndex()), newStack);
                }
                this.blit(xPos + x, yPos + y, 0, this.ySize + 24, 2, 2);
            }

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            return y + (k == null ? 0 : k.getSelectionHeight());
        } else {
            throw new RuntimeException("Creative2 cannot handle group key array " + Arrays.toString(keys));
        }
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
        if (this.hoveredSelector == -1 && this.searchField.getText().isEmpty() && this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack() && this.hoveredSlot.inventory != this.minecraft.player.inventory) {
            Map<? extends Enum<?>, IItemProvider> g = group.getGroup(this.hoveredSlot.getStack().getItem().asItem());
            if (g != null && this.isFirstOfGroup(this.hoveredSlot.slotNumber, g)) {
                ITextComponent count = new TranslationTextComponent("itemGroup.lcc.group.amount", g.size()).applyTextStyle(TextFormatting.DARK_GRAY);
                if (g.size() != 1) count.appendSibling(new TranslationTextComponent("itemGroup.lcc.group.amount.s"));
                this.renderTooltip(ImmutableList.of(new TranslationTextComponent(group.getGroupTranslationKey(g)).getFormattedText(), count.getFormattedText()), mouseX, mouseY, this.font);
                return;
            }
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public int getSlotColor(int index) {
        Slot slot = this.container.inventorySlots.get(index);
        if (this.searchField.getText().isEmpty() && slot != null && slot.getHasStack() && slot.inventory != this.minecraft.player.inventory) {
            Map<? extends Enum<?>, IItemProvider> g = group.getGroup(this.hoveredSlot.getStack().getItem().asItem());
            if (g != null && this.isFirstOfGroup(this.hoveredSlot.slotNumber, g)) {
                return 0x00000000;
            }
        }
        return super.getSlotColor(index);
    }

    protected int getListIndex(int slotNumber) {
        int i = (this.container.itemList.size() + 9 - 1) / 9 - 5;
        int j = (int)((double)(this.currentScroll * (float)i) + 0.5D);
        if (j < 0) j = 0;
        return (j*9) + slotNumber;
    }

    protected boolean isFirstOfGroup(int slotNumber, Map<? extends Enum<?>, ? extends IItemProvider> g) {
        int listIndex = this.getListIndex(slotNumber);
        if (listIndex <= 0) return true;
        return group.getGroup(this.container.itemList.get(listIndex - 1).getItem().asItem()) != g;
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

    @Override
    protected void handleMouseClick(@Nullable Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (this.hoveredSelector == -1 && this.searchField.getText().isEmpty() && this.minecraft.player.inventory.getItemStack().isEmpty() && slotIn != null && slotIn.getHasStack() && slotIn.inventory != this.minecraft.player.inventory) {
            Map<? extends Enum<?>, IItemProvider> g = group.getGroup(this.hoveredSlot.getStack().getItem().asItem());
            if (g != null && this.isFirstOfGroup(this.hoveredSlot.slotNumber, g)) {
                if (type == ClickType.PICKUP) {
                    if (group.expandedGroups.contains(g)) group.expandedGroups.remove(g);
                    else group.expandedGroups.add(g);
                    this.container.itemList.clear();
                    group.fillNoSearch(this.container.itemList);
                    container.scrollTo(this.currentScroll);
                }
                return;
            }
        }
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

}
