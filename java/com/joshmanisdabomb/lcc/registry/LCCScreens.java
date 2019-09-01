package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.container.ClassicChestContainer;
import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.gui.inventory.ClassicChestScreen;
import com.joshmanisdabomb.lcc.gui.inventory.SpreaderInterfaceScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LCCScreens {

    public static void init() {
        ScreenManager.registerFactory(((ContainerType<SpreaderInterfaceContainer>)LCCContainers.spreader_interface), SpreaderInterfaceScreen::new);
        ScreenManager.registerFactory(((ContainerType<ClassicChestContainer>)LCCContainers.classic_chest), ClassicChestScreen::new);
    }

}
