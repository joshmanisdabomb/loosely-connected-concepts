package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.*;
import com.joshmanisdabomb.lcc.tileentity.render.BouncePadRenderer;
import com.joshmanisdabomb.lcc.tileentity.render.ComputingRenderer;
import com.joshmanisdabomb.lcc.tileentity.render.TimeRiftRenderer;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;

public abstract class LCCTileEntities {

    public static final ArrayList<TileEntityType<?>> all = new ArrayList<>();

    public static TileEntityType<SpreaderInterfaceTileEntity> spreader_interface;

    public static TileEntityType<AtomicBombTileEntity> atomic_bomb;

    public static TileEntityType<BouncePadTileEntity> bounce_pad;

    public static TileEntityType<ComputingTileEntity> computing;
    public static TileEntityType<TerminalTileEntity> terminal;

    public static TileEntityType<TimeRiftTileEntity> time_rift;
    public static TileEntityType<NetherReactorTileEntity> nether_reactor;
    public static TileEntityType<ClassicChestTileEntity> classic_chest;

    public static void init(RegistryEvent.Register<TileEntityType<?>> e) {
        all.add(spreader_interface = (TileEntityType<SpreaderInterfaceTileEntity>) TileEntityType.Builder.create(SpreaderInterfaceTileEntity::new, LCCBlocks.spreader_interface).build(null).setRegistryName(LCC.MODID, "spreader_interface"));
        all.add(bounce_pad = (TileEntityType<BouncePadTileEntity>) TileEntityType.Builder.create(BouncePadTileEntity::new, LCCBlocks.bounce_pad).build(null).setRegistryName(LCC.MODID, "bounce_pad"));
        all.add(computing = (TileEntityType<ComputingTileEntity>) TileEntityType.Builder.create(ComputingTileEntity::new, LCCBlocks.computing).build(null).setRegistryName(LCC.MODID, "computing_tile"));
        all.add(terminal = (TileEntityType<TerminalTileEntity>) TileEntityType.Builder.create(TerminalTileEntity::new, LCCBlocks.terminals.values().toArray(new Block[0])).build(null).setRegistryName(LCC.MODID, "terminal"));
        all.add(time_rift = (TileEntityType<TimeRiftTileEntity>) TileEntityType.Builder.create(TimeRiftTileEntity::new, LCCBlocks.time_rift).build(null).setRegistryName(LCC.MODID, "time_rift"));
        all.add(classic_chest = (TileEntityType<ClassicChestTileEntity>) TileEntityType.Builder.create(ClassicChestTileEntity::new, LCCBlocks.classic_chest).build(null).setRegistryName(LCC.MODID, "classic_chest"));
        all.add(nether_reactor = (TileEntityType<NetherReactorTileEntity>) TileEntityType.Builder.create(NetherReactorTileEntity::new, LCCBlocks.nether_reactor).build(null).setRegistryName(LCC.MODID, "nether_reactor"));
        all.add(atomic_bomb = (TileEntityType<AtomicBombTileEntity>) TileEntityType.Builder.create(AtomicBombTileEntity::new, LCCBlocks.atomic_bomb).build(null).setRegistryName(LCC.MODID, "atomic_bomb"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderers() {
        ClientRegistry.bindTileEntityRenderer(bounce_pad, BouncePadRenderer::new);
        ClientRegistry.bindTileEntityRenderer(time_rift, TimeRiftRenderer::new);
        ClientRegistry.bindTileEntityRenderer(computing, ComputingRenderer::new);
    }

}
