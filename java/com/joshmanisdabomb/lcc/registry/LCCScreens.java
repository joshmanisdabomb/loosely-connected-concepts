package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.container.ClassicChestContainer;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.gui.ComputerScreen;
import com.joshmanisdabomb.lcc.gui.inventory.ClassicChestScreen;
import com.joshmanisdabomb.lcc.gui.inventory.SpreaderInterfaceScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LCCScreens {

    public static void init() {
        ScreenManager.registerFactory((ContainerType<SpreaderInterfaceContainer>)LCCContainers.spreader_interface, SpreaderInterfaceScreen::new);
        ScreenManager.registerFactory((ContainerType<ClassicChestContainer>)LCCContainers.classic_chest, ClassicChestScreen::new);
        ScreenManager.registerFactory((ContainerType<ComputingContainer>)LCCContainers.computing, (ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent) -> {
            switch (container.module.type) {
                default:
                    return new ComputerScreen(container, playerInv, textComponent);
            }
        });
    }

}
