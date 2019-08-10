package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCTileEntities {

    public static final ArrayList<TileEntityType<?>> all = new ArrayList<>();

    public static TileEntityType<?> spreader_interface;

    public static void init(RegistryEvent.Register<TileEntityType<?>> e) {
        all.add(spreader_interface = TileEntityType.Builder.create(SpreaderInterfaceTileEntity::new, LCCBlocks.spreader_interface).build(null).setRegistryName(LCC.MODID, "spreader_interface"));
    }

}
