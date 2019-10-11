package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.UUID;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputerPowerPacket implements LCCPacket {

    private final DimensionType dim;
    private final BlockPos pos;
    private final UUID player;
    private final SlabType module;
    private final boolean powerState;

    public ComputerPowerPacket(DimensionType dim, BlockPos pos, UUID player, SlabType module, boolean powerState) {
        this.dim = dim;
        this.pos = pos;
        this.player = player;
        this.module = module;
        this.powerState = powerState;
    }

    public static void encode(ComputerPowerPacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.dim.getRegistryName());
        buf.writeBlockPos(msg.pos);
        buf.writeUniqueId(msg.player);
        buf.writeBoolean(msg.module == SlabType.TOP);
        buf.writeBoolean(msg.powerState);
    }

    public static ComputerPowerPacket decode(PacketBuffer buf) {
        return new ComputerPowerPacket(DimensionType.byName(buf.readResourceLocation()), buf.readBlockPos(), buf.readUniqueId(), buf.readBoolean() ? SlabType.TOP : SlabType.BOTTOM, buf.readBoolean());
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
        if (!(te instanceof ComputingTileEntity)) return;

        PlayerEntity player = s.getPlayerList().getPlayerByUUID(this.player);
        if (player == null) return;
        if (player.getDistanceSq(this.pos.getX(), this.pos.getY(), this.pos.getZ()) > 200) return;

        BlockState state = world.getBlockState(this.pos);
        if (state.getBlock() != LCCBlocks.computing) return;
        if (state.get(ComputingBlock.MODULE) == flip(this.module)) return;

        ComputingTileEntity.ComputingModule cm = ((ComputingTileEntity)te).getModule(this.module);
        if (cm == null) return;
        if (cm.type != ComputingTileEntity.ComputingModuleType.COMPUTER) return;

        cm.powerState = this.powerState;
    }

}
