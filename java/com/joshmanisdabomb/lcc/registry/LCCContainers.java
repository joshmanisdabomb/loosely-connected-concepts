package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCContainers {

    public static final ArrayList<ContainerType<?>> all = new ArrayList<>();

    public static ContainerType<?> spreader_interface;

    public static void init(RegistryEvent.Register<ContainerType<?>> e) {
        all.add(spreader_interface = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new SpreaderInterfaceContainer(windowId, ((SpreaderInterfaceTileEntity)Minecraft.getInstance().world.getTileEntity(pos)), Minecraft.getInstance().player, inv);
        }).setRegistryName(LCC.MODID, "spreader_interface"));
    }

}
