package com.joshmanisdabomb.lcc.gui;

import com.joshmanisdabomb.lcc.container.ComputingContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ComputingScreen extends ContainerScreen<ComputingContainer> {

    public ComputingScreen(ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
    }

}
