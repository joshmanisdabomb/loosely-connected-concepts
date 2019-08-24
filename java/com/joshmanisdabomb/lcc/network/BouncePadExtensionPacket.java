package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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

    public static void handle(final BouncePadExtensionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ((BouncePadTileEntity)Minecraft.getInstance().world.getTileEntity(msg.pos)).extension = msg.extension;
            for (int i = 0; i < 50; i++) {
                Minecraft.getInstance().world.addParticle(ParticleTypes.BUBBLE_POP, msg.pos.getX() + 0.5, msg.pos.getY() + 0.4375, msg.pos.getZ() + 0.5, (PARTICLE_RAND.nextDouble() - 0.5) * 0.6, -0.01, (PARTICLE_RAND.nextDouble() - 0.6) * 0.4);
            }
            Minecraft.getInstance().world.playSound(msg.pos.getX() + 0.5, msg.pos.getY() + 0.5, msg.pos.getZ() + 0.5, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, SoundCategory.BLOCKS, 0.4F, 1.5F, false);
    });
        ctx.get().setPacketHandled(true);
    }

}