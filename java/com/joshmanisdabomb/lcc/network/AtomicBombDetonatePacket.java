package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.block.AtomicBombBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.AtomicBombTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.UUID;

public class AtomicBombDetonatePacket implements LCCPacket {

    private final DimensionType dim;
    private final BlockPos pos;
    private final UUID player;

    public AtomicBombDetonatePacket(DimensionType dim, BlockPos pos, UUID player) {
        this.dim = dim;
        this.pos = pos;
        this.player = player;
    }

    public static void encode(AtomicBombDetonatePacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.dim.getRegistryName());
        buf.writeBlockPos(msg.pos);
        buf.writeUniqueId(msg.player);
    }

    public static AtomicBombDetonatePacket decode(PacketBuffer buf) {
        return new AtomicBombDetonatePacket(DimensionType.byName(buf.readResourceLocation()), buf.readBlockPos(), buf.readUniqueId());
    }

    @Override
    public void handleLogicalServer() {
        MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        if (this.dim == null) return;
        if (this.pos == null) return;
        if (this.player == null) return;

        World world = s.getWorld(this.dim);
        if (!world.isBlockLoaded(this.pos)) return;

        TileEntity te = world.getTileEntity(this.pos);
        if (!(te instanceof AtomicBombTileEntity)) return;

        PlayerEntity player = s.getPlayerList().getPlayerByUUID(this.player);
        if (player == null) return;
        if (player.getDistanceSq(this.pos.getX(), this.pos.getY(), this.pos.getZ()) > 200) return;

        BlockState state = world.getBlockState(this.pos);
        if (state.getBlock() != LCCBlocks.atomic_bomb) return;
        if (state.get(AtomicBombBlock.SEGMENT) != AtomicBombBlock.Segment.MIDDLE) return;

        ((AtomicBombTileEntity)te).detonate(player);
    }

}
