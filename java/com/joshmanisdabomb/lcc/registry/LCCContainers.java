package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.container.*;
import com.joshmanisdabomb.lcc.tileentity.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCContainers {

    public static final ArrayList<ContainerType<?>> all = new ArrayList<>();

    public static ContainerType<?> spreader_interface;
    public static ContainerType<?> atomic_bomb;
    public static ContainerType<?> classic_chest;
    public static ContainerType<?> computing;
    public static ContainerType<?> terminal;

    public static void init(RegistryEvent.Register<ContainerType<?>> e) {
        all.add(spreader_interface = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new SpreaderInterfaceContainer(windowId, ((SpreaderInterfaceTileEntity)LCC.proxy.getClientWorld().getTileEntity(pos)), new SpreaderCapability().readFromPacket(data), LCC.proxy.getClientPlayer(), inv);
        }).setRegistryName(LCC.MODID, "spreader_interface"));
        all.add(classic_chest = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new ClassicChestContainer(windowId, ((ClassicChestTileEntity)LCC.proxy.getClientWorld().getTileEntity(pos)), LCC.proxy.getClientPlayer(), inv);
        }).setRegistryName(LCC.MODID, "classic_chest"));
        all.add(computing = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new ComputingContainer(windowId, ((ComputingTileEntity)LCC.proxy.getClientWorld().getTileEntity(pos)), data.readBoolean() ? SlabType.TOP : SlabType.BOTTOM, LCC.proxy.getClientPlayer(), inv);
        }).setRegistryName(LCC.MODID, "computing_container"));
        all.add(terminal = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new TerminalContainer(windowId, ((TerminalTileEntity)LCC.proxy.getClientWorld().getTileEntity(pos)), LCC.proxy.getClientPlayer(), inv);
        }).setRegistryName(LCC.MODID, "terminal"));
        all.add(atomic_bomb = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new AtomicBombContainer(windowId, ((AtomicBombTileEntity)LCC.proxy.getClientWorld().getTileEntity(pos)), LCC.proxy.getClientPlayer(), inv);
        }).setRegistryName(LCC.MODID, "atomic_bomb"));
    }

}
