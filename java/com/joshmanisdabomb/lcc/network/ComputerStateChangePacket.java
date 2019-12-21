package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputerStateChangePacket implements LCCPacket {

    private final DimensionType dim;
    private final BlockPos pos;
    private final SlabType location;
    private final CompoundNBT newState;
    private final boolean readLight;

    public ComputerStateChangePacket(DimensionType dim, BlockPos pos, SlabType location, CompoundNBT newState, boolean readLight) {
        this.dim = dim;
        this.pos = pos;
        this.location = location;
        this.newState = newState;
        this.readLight = readLight;
    }

    public static void encode(ComputerStateChangePacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.dim.getRegistryName());
        buf.writeBlockPos(msg.pos);
        buf.writeBoolean(msg.location == SlabType.TOP);
        buf.writeCompoundTag(msg.newState);
        buf.writeBoolean(msg.readLight);
    }

    public static ComputerStateChangePacket decode(PacketBuffer buf) {
        return new ComputerStateChangePacket(DimensionType.byName(buf.readResourceLocation()), buf.readBlockPos(), buf.readBoolean() ? SlabType.TOP : SlabType.BOTTOM, buf.readCompoundTag(), buf.readBoolean());
    }

    private void handle(World world) {
        if (this.pos == null) return;
        if (this.newState == null) return;

        if (!world.isBlockLoaded(this.pos)) return;

        TileEntity te = world.getTileEntity(this.pos);
        if (!(te instanceof ComputingTileEntity)) return;

        BlockState state = world.getBlockState(this.pos);
        if (state.getBlock() != LCCBlocks.computing) return;
        if (state.get(ComputingBlock.MODULE) == flip(this.location)) return;

        ComputingModule cm = ((ComputingTileEntity)te).getModule(this.location);
        if (cm == null) return;
        if (cm.type != ComputingModule.Type.COMPUTER) return;

        cm.state = this.newState;
        if (cm.session != null) cm.session.receiveState();
        if (this.readLight) cm.read();
    }

    @Override
    public void handleClient() {
        this.handle(LCC.proxy.getClientWorld());
    }

    @Override
    public void handleLogicalServer() {
        MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        if (this.dim == null) return;

        World world = s.getWorld(this.dim);

        this.handle(world);
    }

}
