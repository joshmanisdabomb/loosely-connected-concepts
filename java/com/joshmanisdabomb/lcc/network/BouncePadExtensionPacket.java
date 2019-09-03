package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.block.BouncePadBlock;
import com.joshmanisdabomb.lcc.registry.LCCParticles;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class BouncePadExtensionPacket implements LCCPacket {

    private final BlockPos pos;
    private final float extension;

    private static final Random PARTICLE_RAND = new Random();

    public BouncePadExtensionPacket(BlockPos pos, float extension) {
        this.pos = pos;
        this.extension = extension;
    }

    public static void encode(BouncePadExtensionPacket msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeFloat(msg.extension);
    }

    public static BouncePadExtensionPacket decode(PacketBuffer buf) {
        return new BouncePadExtensionPacket(buf.readBlockPos(), buf.readFloat());
    }

    public static void handleClient(final BouncePadExtensionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ((BouncePadTileEntity)Minecraft.getInstance().world.getTileEntity(msg.pos)).extension = msg.extension;
        int setting = Minecraft.getInstance().world.getBlockState(msg.pos).get(BouncePadBlock.SETTING);
        Minecraft.getInstance().world.addParticle(LCCParticles.hydrated_soul_sand_jump, false, msg.pos.getX() + 0.5, msg.pos.getY() + 0.4375, msg.pos.getZ() + 0.5, 0.875, ((setting + 1)*4) - 2, 0.5 + ((setting + 1) * 0.2));
        Minecraft.getInstance().world.playSound(msg.pos.getX() + 0.5, msg.pos.getY() + 0.4375, msg.pos.getZ() + 0.5, LCCSounds.block_bounce_pad_jump, SoundCategory.BLOCKS, 0.4F, 0.95F + ((4-setting)*0.05F), false);
    }

}