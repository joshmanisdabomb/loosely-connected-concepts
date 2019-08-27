package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import com.joshmanisdabomb.lcc.tileentity.TimeRiftTileEntity;
import com.joshmanisdabomb.lcc.tileentity.render.BouncePadRenderer;
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

    public static TileEntityType<?> spreader_interface;
    public static TileEntityType<?> bounce_pad;
    public static TileEntityType<?> time_rift;

    public static void init(RegistryEvent.Register<TileEntityType<?>> e) {
        all.add(spreader_interface = TileEntityType.Builder.create(SpreaderInterfaceTileEntity::new, LCCBlocks.spreader_interface).build(null).setRegistryName(LCC.MODID, "spreader_interface"));
        all.add(bounce_pad = TileEntityType.Builder.create(BouncePadTileEntity::new, LCCBlocks.bounce_pad).build(null).setRegistryName(LCC.MODID, "bounce_pad"));
        all.add(time_rift = TileEntityType.Builder.create(TimeRiftTileEntity::new, LCCBlocks.time_rift).build(null).setRegistryName(LCC.MODID, "time_rift"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(BouncePadTileEntity.class, new BouncePadRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TimeRiftTileEntity.class, new TimeRiftRenderer());
    }

}
