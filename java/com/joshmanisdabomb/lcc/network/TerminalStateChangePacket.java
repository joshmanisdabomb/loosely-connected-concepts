package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class TerminalStateChangePacket implements LCCPacket {

    private final DimensionType dim;
    private final BlockPos pos;
    private final CompoundNBT newState;

    public TerminalStateChangePacket(DimensionType dim, BlockPos pos, CompoundNBT newState) {
        this.dim = dim;
        this.pos = pos;
        this.newState = newState;
    }

    public static void encode(TerminalStateChangePacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.dim.getRegistryName());
        buf.writeBlockPos(msg.pos);
        buf.writeCompoundTag(msg.newState);
    }

    public static TerminalStateChangePacket decode(PacketBuffer buf) {
        return new TerminalStateChangePacket(DimensionType.byName(buf.readResourceLocation()), buf.readBlockPos(), buf.readCompoundTag());
    }

    @Override
    public void handleLogicalServer() {
        MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        if (this.dim == null) return;
        if (this.pos == null) return;
        if (this.newState == null) return;

        World world = s.getWorld(this.dim);
        if (!world.isBlockLoaded(this.pos)) return;

        TileEntity te = world.getTileEntity(this.pos);
        if (!(te instanceof TerminalTileEntity)) return;

        BlockState state = world.getBlockState(this.pos);
        if (!LCCBlocks.terminals.containsValue(state.getBlock())) return;

        ((TerminalTileEntity)te).state = this.newState;
        ((TerminalTileEntity)te).receiveState();
    }

}
